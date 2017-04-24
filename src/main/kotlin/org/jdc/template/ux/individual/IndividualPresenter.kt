package org.jdc.template.ux.individual

import com.google.android.gms.analytics.HitBuilders
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import org.jdc.template.Analytics
import org.jdc.template.model.database.main.individual.IndividualManager
import org.jdc.template.ui.mvp.BasePresenter
import org.jdc.template.util.CoroutineContextProvider
import javax.inject.Inject

class IndividualPresenter @Inject
constructor(private val cc: CoroutineContextProvider,
            private val analytics: Analytics,
            private val individualManager: IndividualManager): BasePresenter() {

    private lateinit var view: IndividualContract.View
    private var individualId = 0L
    private var modelTs = 0L

    fun init(view: IndividualContract.View, individualId: Long) {
        this.view = view;
        this.individualId = individualId
    }

    override fun load(): Job? {
        analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_INDIVIDUAL).setAction(Analytics.ACTION_VIEW).build())
        return loadIndividual()
    }

    override fun reload(forceRefresh: Boolean): Job? {
        if (forceRefresh || modelTs != individualManager.getLastTableModifiedTs()) {
            return loadIndividual()
        }

        return null
    }

    private fun loadIndividual(): Job {
        val job = launch(cc.ui) {
            modelTs = individualManager.getLastTableModifiedTs()

            val individual = run(context + cc.commonPool) {
                individualManager.findByRowId(individualId)
            }

            if (individual != null) {
                view.showIndividual(individual)
            }
        }

        compositeJob.add(job)
        return job
    }

    fun deleteIndividualClicked() {
        view.promptDeleteIndividual()
    }

    fun deleteIndividual() {
        individualManager.delete(individualId)
        analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_INDIVIDUAL).setAction(Analytics.ACTION_DELETE).build())
        view.close()
    }

    fun editIndividualClicked() {
        view.showEditIndividual(individualId)
    }

}
