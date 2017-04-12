package org.jdc.template.ui.activity

import android.os.Bundle
import android.text.format.DateUtils
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.analytics.HitBuilders
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import okhttp3.ResponseBody
import org.dbtools.android.domain.DBToolsTableChangeListener
import org.dbtools.android.domain.DatabaseTableChange
import org.jdc.template.Analytics
import org.jdc.template.BuildConfig
import org.jdc.template.R.layout.activity_about
import org.jdc.template.event.NewDataEvent
import org.jdc.template.inject.Injector
import org.jdc.template.job.SampleJob
import org.jdc.template.model.database.AppDatabaseConfig
import org.jdc.template.model.database.DatabaseManager
import org.jdc.template.model.database.DatabaseManagerConst
import org.jdc.template.model.database.attached.crossdatabasequery.CrossDatabaseQueryManager
import org.jdc.template.model.database.main.household.Household
import org.jdc.template.model.database.main.household.HouseholdManager
import org.jdc.template.model.database.main.individual.Individual
import org.jdc.template.model.database.main.individual.IndividualManager
import org.jdc.template.model.database.main.individualdata.IndividualDataManager
import org.jdc.template.model.database.main.individualquery.IndividualQueryManager
import org.jdc.template.model.database.other.individuallist.IndividualList
import org.jdc.template.model.database.other.individuallist.IndividualListManager
import org.jdc.template.model.database.other.individuallistitem.IndividualListItem
import org.jdc.template.model.database.other.individuallistitem.IndividualListItemManager
import org.jdc.template.model.type.IndividualType
import org.jdc.template.model.webservice.colors.ColorService
import org.jdc.template.model.webservice.colors.dto.DtoColors
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

class AboutActivity : BaseActivity() {

    @Inject
    lateinit var analytics: Analytics
    @Inject
    lateinit var bus: Bus
    @Inject
    lateinit var databaseManager: DatabaseManager
    @Inject
    lateinit var individualManager: IndividualManager
    @Inject
    lateinit var householdManager: HouseholdManager
    @Inject
    lateinit var individualListManager: IndividualListManager
    @Inject
    lateinit var individualListItemManager: IndividualListItemManager
    @Inject
    lateinit var individualQueryManager: IndividualQueryManager
    @Inject
    lateinit var crossDatabaseQueryManager: CrossDatabaseQueryManager
    @Inject
    lateinit var individualDataManager: IndividualDataManager

    @Inject
    lateinit var colorService: ColorService
    @Inject
    lateinit var webServiceUtil: WebServiceUtil

    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_about)

        analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_ABOUT).build())

        setSupportActionBar(mainToolbar)
        enableActionBarBackArrow(true)

        versionTextView.text = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
        versionDateTextView.text = DateUtils.formatDateTime(this, BuildConfig.BUILD_TIME, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_YEAR)

        createDatabaseButton.setOnClickListener {
            createSampleDataWithInjection()
//            createSampleDataNoInjection()
        }
        restTestButton.setOnClickListener {
//            testQueryWebServiceCall() // simple rest call
            testQueryWebServiceCallRx() // use Rx to make the call
//            testSaveQueryWebServiceCall() // write the response to file, the read the file to show results
//            testFullUrlQueryWebServiceCall() //  simple call using the full URL instead of an endpoint
        }
        jobTestButton.setOnClickListener {
            jobTest()
        }
        rxTestButton.setOnClickListener {
            testRx()
        }
        testButton.setOnClickListener {
//            testQuery()
        }
    }

    override fun onStart() {
        super.onStart()
        bus.register(this)
    }

    override fun onStop() {
        bus.unregister(this)
        super.onStop()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     * Creates sample data WITH using injection
     */
    private fun createSampleDataWithInjection() {
        // clear any existing items
        individualManager.deleteAll()
        householdManager.deleteAll()
        individualListManager.deleteAll()

        // MAIN Database
        householdManager.beginTransaction()

        val household = Household()
        household.name = "Campbell"
        householdManager.save(household)

        val individual1 = Individual()
        individual1.firstName = "Jeff"
        individual1.lastName = "Campbell"
        individual1.phone = "801-555-0000"
        individual1.individualType = IndividualType.HEAD
        individual1.individualTypeText = IndividualType.HEAD
        individual1.householdId = household.id
        individual1.birthDate = LocalDate.of(1970, 1, 1)
        individual1.alarmTime = LocalTime.of(7, 0)
        individual1.amount1 = 19.95F
        individual1.amount2 = 1000000000.25
        individual1.enabled = true
        individualManager.save(individual1)

        val individual2 = Individual()
        individual2.firstName = "John"
        individual2.lastName = "Miller"
        individual2.phone = "303-555-1111"
        individual2.individualType = IndividualType.CHILD
        individual2.individualTypeText = IndividualType.CHILD
        individual2.householdId = household.id
        individual1.birthDate = LocalDate.of(1970, 1, 2)
        individual2.alarmTime = LocalTime.of(6, 0)
        individual2.amount1 = 21.95F
        individual2.amount2 = 2000000000.25
        individual1.enabled = false
        individualManager.save(individual2)

        householdManager.endTransaction(true)

        // OTHER Database
        individualListManager.beginTransaction()
        val newList = IndividualList()
        newList.name = "My List"
        individualListManager.save(newList)

        val newListItem = IndividualListItem()
        newListItem.listId = newList.id
        newListItem.individualId = individual1.id
        individualListItemManager.save(newListItem)

        individualListManager.endTransaction(true)
    }

    /**
     * Creates sample data WITHOUT using injection
     */
    private fun createSampleDataNoInjection() {
        val noInjectionDatabaseManager = DatabaseManager(AppDatabaseConfig(application))

        val householdManager = HouseholdManager(noInjectionDatabaseManager)
        val individualManager = IndividualManager(noInjectionDatabaseManager)
        val individualListManager = IndividualListManager(noInjectionDatabaseManager)
        val individualListItemManager = IndividualListItemManager(noInjectionDatabaseManager)

        // Main Database
        val dbName = DatabaseManagerConst.MAIN_DATABASE_NAME
        noInjectionDatabaseManager.beginTransaction(DatabaseManagerConst.MAIN_DATABASE_NAME)

        val household = Household()
        household.name = "Campbell"
        householdManager.save(household, dbName)

        val individual1 = Individual()
        individual1.firstName = "Jeff"
        individual1.lastName = "Campbell"
        individual1.phone = "000-555-1234"
        individual1.individualType = IndividualType.HEAD
        individual1.individualTypeText = IndividualType.HEAD
        individual1.householdId = household.id
        individualManager.save(individual1, dbName)

        val individual2 = Individual()
        individual2.firstName = "Tanner"
        individual2.lastName = "Campbell"
        individual2.individualType = IndividualType.CHILD
        individual2.individualTypeText = IndividualType.CHILD
        individual2.householdId = household.id
        individualManager.save(individual2, dbName)
        noInjectionDatabaseManager.endTransaction(DatabaseManagerConst.MAIN_DATABASE_NAME, true)


        // Other Database
        noInjectionDatabaseManager.beginTransaction(DatabaseManagerConst.OTHER_DATABASE_NAME)

        val otherDb = DatabaseManagerConst.MAIN_DATABASE_NAME
        val newList = IndividualList()
        newList.name = "My List"
        individualListManager.save(newList, otherDb)

        val newListItem = IndividualListItem()
        newListItem.listId = newList.id
        newListItem.individualId = individual1.id
        individualListItemManager.save(newListItem, otherDb)

        noInjectionDatabaseManager.endTransaction(DatabaseManagerConst.OTHER_DATABASE_NAME, true)

        Toast.makeText(this, "Database created", Toast.LENGTH_SHORT).show()
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
                    RxUtil.verifyRetrofitResponse(response)

                })
                .filter({ dtoSearchResponse -> dtoSearchResponse != null }) // don't continue if null
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ dtoSearchResponse -> processSearchResponse(dtoSearchResponse!!) }, { _ -> bus.post(NewDataEvent(false, null)) }, { bus.post(NewDataEvent(true, null)) })
    }

    /**
     * Bus event example (when rest call finishes)
     */
    @Subscribe
    fun handle(event: NewDataEvent) {
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
                val outputFile = File(externalCacheDir, "ws-out.json")
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
            processSearchResponse(response.body())
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
     * Rx and Table change listener tests
     */
    fun testRx() {
        // Sample tests for Rx
        if (individualManager.findCount() == 0L) {
            Timber.e("No data.. cannot perform test")
            return
        }

        // Rx Subscribe
        val tableChangeSubscription = individualManager.tableChanges().subscribe { change -> handleRxIndividualTableChange(change) }

        // Standard Listener
        individualManager.addTableChangeListener(DBToolsTableChangeListener { change -> handleIndividualTableChange(change) })

        // Make some changes
        val originalName: String

        val individualList = individualManager.findAll()
        if (individualList.isNotEmpty()) {
            val individual = individualList[0]
            originalName = individual.firstName
            Timber.i("ORIGINAL NAME = %s", originalName)

            // change name
            individual.firstName = "Bobby"
            individualManager.save(individual)

            // restore name
            individual.firstName = originalName
            individualManager.save(individual)
        } else {
            Timber.e("Cannot find individual")
        }

        // Unsubscribe
        tableChangeSubscription.dispose()
    }

    fun handleRxIndividualTableChange(change: DatabaseTableChange) {
        when {
            change.isInsert -> Timber.i("Rx Individual Table had insert for table: [${change.table}] rowId: [${change.rowId}]")
            change.isUpdate -> Timber.i("Rx Individual Table had update for table: [${change.table}] rowId: [${change.rowId}]")
            change.isDelete -> Timber.i("Rx Individual Table had delete for table: [${change.table}] rowId: [${change.rowId}]")
        }
    }

    fun handleIndividualTableChange(change: DatabaseTableChange) {
        when {
            change.isInsert -> Timber.i("Individual Table had insert for table: [${change.table}] rowId: [${change.rowId}]")
            change.isUpdate -> Timber.i("Individual Table had update for table: [${change.table}] rowId: [${change.rowId}]")
            change.isDelete -> Timber.i("Individual Table had delete for table: [${change.table}] rowId: [${change.rowId}]")
        }
    }
}
