package org.jdc.template.ux.about

import android.app.Application
import com.google.android.gms.analytics.HitBuilders
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import okhttp3.ResponseBody
import org.jdc.template.Analytics
import org.jdc.template.datasource.database.main.MainDatabase
import org.jdc.template.datasource.database.main.household.Household
import org.jdc.template.datasource.database.main.household.HouseholdDao
import org.jdc.template.datasource.database.main.individual.Individual
import org.jdc.template.datasource.database.main.individual.IndividualDao
import org.jdc.template.datasource.database.main.type.IndividualType
import org.jdc.template.datasource.webservice.colors.ColorService
import org.jdc.template.datasource.webservice.colors.dto.DtoColors
import org.jdc.template.event.SampleEvent
import org.jdc.template.job.SampleJob
import org.jdc.template.ui.BaseController
import org.jdc.template.util.CoroutineContextProvider
import org.jdc.template.util.RxUtil
import org.jdc.template.util.WebServiceUtil
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import pocketbus.Bus
import pocketbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class AboutController @Inject
constructor(private val analytics: Analytics,
            private val bus: Bus,
            private val application: Application,
            private val cc: CoroutineContextProvider,
            private val mainDatabase: MainDatabase,
            private val householdDao: HouseholdDao,
            private val individualDao: IndividualDao,
            private val colorService: ColorService,
            private val webServiceUtil: WebServiceUtil): BaseController() {

    override fun load(): Job? {
        analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_ABOUT).build())
        return null
    }

    override fun register() {
        bus.register(this)
    }

    override fun unregister() {
        super.unregister()
        bus.unregister(this)
    }

    /**
     * Creates sample data WITH using injection
     */
    fun createSampleDataWithInjection() = launch(cc.commonPool) {
        // clear any existing items
        individualDao.deleteAll()
        householdDao.deleteAll()

        // MAIN Database
        mainDatabase.beginTransaction()

        val household = Household()
        household.name = "Campbell"
        householdDao.insert(household)

        val individual1 = Individual()
        individual1.firstName = "Jeff"
        individual1.lastName = "Campbell"
        individual1.phone = "801-555-0000"
        individual1.individualType = IndividualType.HEAD
        individual1.householdId = household.id
        individual1.birthDate = LocalDate.of(1970, 1, 1)
        individual1.alarmTime = LocalTime.of(7, 0)
        individualDao.insert(individual1)

        val individual2 = Individual()
        individual2.firstName = "John"
        individual2.lastName = "Miller"
        individual2.phone = "303-555-1111"
        individual2.individualType = IndividualType.CHILD
        individual2.householdId = household.id
        individual1.birthDate = LocalDate.of(1970, 1, 2)
        individual2.alarmTime = LocalTime.of(6, 0)
        individualDao.insert(individual2)

        mainDatabase.setTransactionSuccessful()
        mainDatabase.endTransaction()
    }

    /**
     * Simple web service call
     */
    //    @OnClick(R.id.rest_test_button)
    fun testQueryWebServiceCall() {
        val call = colorService.colors()

        call.enqueue(object : Callback<DtoColors> {
            override fun onResponse(call: Call<DtoColors>, response: Response<DtoColors>) {
                processWebServiceResponse(response)
            }

            override fun onFailure(call: Call<DtoColors>, t: Throwable) {
                Timber.e(t, "Search FAILED")
            }
        })
    }

    /**
     * Simple web service call using Rx
     */
    fun testQueryWebServiceCallRx() {
        RxUtil.toRetrofitObservable(colorService.colors())
                .subscribeOn(Schedulers.io())
                .map({ response ->
                    RxUtil.verifyRetrofitResponse(response)!!

                })
                .filter({ dtoSearchResponse -> dtoSearchResponse != null }) // don't continue if null
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ dtoSearchResponse -> processSearchResponse(dtoSearchResponse) },
                        { _ -> bus.post(SampleEvent(false, null)) },
                        { bus.post(SampleEvent(true, null)) })
    }

    /**
     * Bus event example (when rest call finishes)
     */
    @Subscribe
    fun handle(event: SampleEvent) {
        Timber.i(event.throwable, "Rest Service finished [%b]", event.isSuccess)
    }

    /**
     * Simple web service call using the full url (instead of just an endpoint)
     */
    fun testFullUrlQueryWebServiceCall() {
        val call = colorService.colorsByFullUrl(ColorService.FULL_URL)

        call.enqueue(object : Callback<DtoColors> {
            override fun onResponse(call: Call<DtoColors>, response: Response<DtoColors>) {
                processWebServiceResponse(response)
            }

            override fun onFailure(call: Call<DtoColors>, t: Throwable) {
                Timber.e(t, "Search FAILED")
            }
        })
    }

    /**
     * Web service call that saves response to file, then processes the file (best for large JSON payloads)
     */
    fun testSaveQueryWebServiceCall() {
        val call = colorService.colorsToFile()

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                // delete any existing file
                val outputFile = File(application.externalCacheDir, "ws-out.json")
                if (outputFile.exists()) {
                    outputFile.delete()
                }

                // save the response body to file
                webServiceUtil.saveResponseToFile(response, outputFile)

                // show the output of the file
                val fileContents = outputFile.readText()
                Timber.i("Output file: [$fileContents]")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Timber.e("Search FAILED")
            }
        })
    }

    private fun processWebServiceResponse(response: Response<DtoColors>) {
        if (response.isSuccessful) {
            Timber.i("Search SUCCESS")
            response.body()?.let {
                processSearchResponse(it)
            }
        } else {
            Timber.e("Search FAILURE: code (%d)", response.code())
        }
    }

    private fun processSearchResponse(dtoColors: DtoColors) {
        for (dtoResult in dtoColors.colors) {
            Timber.i("Result: %s", dtoResult.colorName)
        }
    }

    /**
     * Sample for creating a scheduled job
     */
    fun jobTest() {
        SampleJob.schedule()
        SampleJob.schedule()
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        SampleJob.schedule()
    }

    /**
     * Table change listener tests
     */
    fun testTableChange() = launch(cc.commonPool) {
        // Sample tests for Rx
        if (individualDao.findCount() == 0L) {
            Timber.e("No data.. cannot perform test")
            return@launch
        }

        // Make some changes
        val originalName: String

        val individualList = individualDao.findAll()
        if (individualList.isNotEmpty()) {
            val individual = individualList[0]
            originalName = individual.firstName
            Timber.i("ORIGINAL NAME = %s", originalName)

            // change name
            individual.firstName = "Bobby"
            individualDao.update(individual)

            // restore name
            individual.firstName = originalName
            individualDao.update(individual)
        } else {
            Timber.e("Cannot find individual")
        }
    }
}