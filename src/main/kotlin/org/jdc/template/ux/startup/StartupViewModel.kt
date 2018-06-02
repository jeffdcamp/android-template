package org.jdc.template.ux.startup

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.analytics.HitBuilders
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import org.jdc.template.Analytics
import org.jdc.template.BuildConfig
import org.jdc.template.datasource.database.main.MainDatabase
import org.jdc.template.datasource.database.main.household.Household
import org.jdc.template.datasource.database.main.individual.Individual
import org.jdc.template.datasource.database.main.type.IndividualType
import org.jdc.template.datasource.webservice.individuals.IndividualService
import org.jdc.template.datasource.webservice.individuals.dto.DtoIndividuals
import org.jdc.template.livedata.SingleLiveEvent
import org.jdc.template.util.CoroutineContextProvider
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class StartupViewModel
@Inject constructor(
        private val cc: CoroutineContextProvider,
        private val analytics: Analytics,
        private val mainDatabase: MainDatabase,
        private val individualService: IndividualService
        ) : ViewModel() {

    val startupProgress = MutableLiveData<StartupProgress>()
    val onStartupFinishedEvent = SingleLiveEvent<Void>()

    fun startup() {
        launch(cc.commonPool) {
            analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_APP).setAction(Analytics.ACTION_APP_LAUNCH).setLabel(BuildConfig.BUILD_TYPE).build())

            // do startup work here...
            showProgress("Doing stuff")

            onStartupFinishedEvent.postCall()
        }

        createSampleIndividualDataWithInjection()
    }

    private fun showProgress(message: String) = async(cc.ui) {
        Timber.i("Startup progress: [%s]", message)
        val currentProgress = startupProgress.value
        if (currentProgress == null) {
            startupProgress.postValue(StartupProgress(0, message))
        } else {
            startupProgress.postValue(StartupProgress(currentProgress.progress + 1, message))
        }
    }

    /**
     * Creates Individual sample data WITH using injection
     */
    fun createSampleIndividualDataWithInjection() = launch(cc.commonPool) {
        val call = individualService.individuals()

        call.enqueue(object : Callback<DtoIndividuals> {
            override fun onResponse(call: Call<DtoIndividuals>, response: Response<DtoIndividuals>) {
                processWebIndividualServiceResponse(response)
            }

            override fun onFailure(call: Call<DtoIndividuals>, t: Throwable?) {
                Timber.e(t, "Individual Search FAILED")
            }
        })
    }

    private fun processWebIndividualServiceResponse(response: Response<DtoIndividuals>) {
        if (response.isSuccessful) {
            Timber.i("Individual Search SUCCESS")
            response.body()?.let {
                processIndividualSearchResponse(it)
            }
        } else {
            Timber.e("Search FAILURE: code (%d)", response.code())
        }
    }

    private fun processIndividualSearchResponse(dtoIndividuals: DtoIndividuals) = launch(cc.commonPool) {
        val list : MutableList<Individual> = mutableListOf()
        val result : List<Individual> = list

        val household = Household()
        household.name = "Shan"

        for (ind in dtoIndividuals.individuals) {

            val thisIndividual = Individual()
            thisIndividual.id = ind.id
            thisIndividual.firstName = ind.firstName
            thisIndividual.lastName = ind.lastName
            thisIndividual.phone = "801-555-0000"
            thisIndividual.individualType = IndividualType.HEAD
            thisIndividual.householdId = household.id
            val parts = ind.birthdate.split("-")
            thisIndividual.birthDate = LocalDate.of(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
            thisIndividual.alarmTime = LocalTime.of(7, 0)
            thisIndividual.profileUrl = ind.profilePicture
            list.add(thisIndividual)
            Timber.i("Result: %s - %s", thisIndividual.firstName, thisIndividual.profileUrl)
        }
        persistIndividuals(result, household)
    }

    private fun persistIndividuals(individuals : List<Individual>, household: Household) {
        val individualDao = mainDatabase.individualDao()
        val householdDao = mainDatabase.householdDao()

        try {
            // MAIN Database
            mainDatabase.beginTransaction()

            // clear any existing items
            individualDao.deleteAll()
            householdDao.deleteAll()

            householdDao.insert(household)

            for (ind in individuals) {
                individualDao.insert(ind)
                Timber.i(ind.getFullData())
            }

            mainDatabase.setTransactionSuccessful()
        } finally {
            mainDatabase.endTransaction()
        }
    }

    companion object {
        const val TOTAL_STARTUP_PROGRESS = 3
    }

    data class StartupProgress(val progress: Int, val message: String = "", val indeterminate: Boolean = false)

}