package org.jdc.template.ux.individualedit

import com.google.android.gms.analytics.HitBuilders
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import org.jdc.template.Analytics
import org.jdc.template.model.database.main.individual.Individual
import org.jdc.template.model.database.main.individual.IndividualManager
import org.jdc.template.ui.mvp.BasePresenter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import javax.inject.Inject

class IndividualEditPresenter @Inject
constructor(private val analytics: Analytics,
            private val individualManager: IndividualManager): BasePresenter() {

    private lateinit var view: IndividualEditContract.View
    private var individualId = 0L
    private var individual: Individual? = null

    fun init(view: IndividualEditContract.View, individualId: Long) {
        this.view = view
        this.individualId = individualId
    }

    override fun load(): Job? {
        return loadIndividual()
    }

    private fun loadIndividual(): Job {
        val job = launch(UI) {
            if (individualId <= 0L) {
                individual = Individual()
                return@launch
            }

            individual = run(context + CommonPool) {
                individualManager.findByRowId(individualId)
            }

            individual?.let {
                analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_INDIVIDUAL).setAction(Analytics.ACTION_EDIT).build())
                view.showIndividual(it)
            }
        }
        compositeJob.add(job)
        return job
    }

    fun saveIndividual() {
        val job = launch(UI) {
            if (view.validateIndividualData()) {
                individual?.let {
                    view.getIndividualDataFromUi(it)

                    run(context + CommonPool) {
                        individualManager.save(individual)
                        analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_INDIVIDUAL).setAction(Analytics.ACTION_EDIT_SAVE).build())
                    }

                    view.close()
                }
            }
        }
        compositeJob.add(job)
    }

    fun alarmTimeClicked() {
        view.showAlarmTimeSelector(individual?.alarmTime ?: LocalTime.now())
    }

    fun alarmTimeSelected(time: LocalTime) {
        individual?.let {
            it.alarmTime = time
            view.showAlarmTime(time)
        }
    }

    fun birthdayClicked() {
        view.showBirthDateSelector(individual?.birthDate ?: LocalDate.now())
    }

    fun birthDateSelected(date: LocalDate) {
        individual?.let {
            it.birthDate = date
            view.showBirthDate(date)
        }
    }
}
