package org.jdc.template.ux.about

import android.app.Application
import android.arch.lifecycle.ViewModel
import com.google.android.gms.analytics.HitBuilders
import kotlinx.coroutines.experimental.launch
import okhttp3.ResponseBody
import org.jdc.template.Analytics
import org.jdc.template.BuildConfig
import org.jdc.template.datasource.database.main.MainDatabase
import org.jdc.template.datasource.database.main.household.Household
import org.jdc.template.datasource.database.main.individual.Individual
import org.jdc.template.datasource.database.main.type.IndividualType
import org.jdc.template.datasource.webservice.colors.ColorService
import org.jdc.template.datasource.webservice.colors.dto.DtoColors
import org.jdc.template.ext.saveBodyToFile
import org.jdc.template.job.AppJobScheduler
import org.jdc.template.util.CoroutineContextProvider
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class AboutViewModel
@Inject constructor(
    private val analytics: Analytics,
    private val application: Application,
    private val cc: CoroutineContextProvider,
    private val mainDatabase: MainDatabase,
    private val colorService: ColorService,
    private val appJobScheduler: AppJobScheduler
) : ViewModel() {

    var appVersion = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
    var appBuildDateTime = BuildConfig.BUILD_TIME

    fun logAnalytics() {
        analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_ABOUT).build())
    }

    /**
     * Creates sample data WITH using injection
     */
    fun createSampleDataWithInjection() = launch(cc.commonPool) {
        val individualDao = mainDatabase.individualDao()
        val householdDao = mainDatabase.householdDao()

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
        individual1.profileUrl = ""
        individualDao.insert(individual1)

        val individual2 = Individual()
        individual2.firstName = "John"
        individual2.lastName = "Miller"
        individual2.phone = "303-555-1111"
        individual2.individualType = IndividualType.CHILD
        individual2.householdId = household.id
        individual2.birthDate = LocalDate.of(1970, 1, 2)
        individual2.alarmTime = LocalTime.of(6, 0)
        individual2.profileUrl = ""
        individualDao.insert(individual2)

        mainDatabase.setTransactionSuccessful()
        mainDatabase.endTransaction()
    }

    /**
     * Simple web service call
     */
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
                response.saveBodyToFile(outputFile)

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
        appJobScheduler.scheduleSampleJob()
        appJobScheduler.scheduleSampleJob()
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        appJobScheduler.scheduleSampleJob()
    }

    /**
     * Table change listener tests
     */
    fun testTableChange() = launch(cc.commonPool) {
        val individualDao = mainDatabase.individualDao()

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