package org.jdc.template.ux.directory

import com.google.android.gms.analytics.HitBuilders
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import org.jdc.template.Analytics
import org.jdc.template.model.database.main.individual.IndividualConst
import org.jdc.template.model.database.main.individual.IndividualManager
import org.jdc.template.ui.mvp.BasePresenter
import javax.inject.Inject

class DirectoryPresenter @Inject
constructor(private val analytics: Analytics,
            private val individualManager: IndividualManager) : BasePresenter() {

    private lateinit var view: DirectoryContract.View
    private var modelTs = 0L
    var scrollPosition: Int = 0

    fun init(view: DirectoryContract.View) {
        this.view = view
    }

    override fun load(): Job? {
        analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_DIRECTORY).setAction(Analytics.ACTION_VIEW).build())
        return loadDirectoryList()
    }

    override fun reload(forceRefresh: Boolean): Job? {
        if (forceRefresh || modelTs != individualManager.getLastTableModifiedTs()) {
            return loadDirectoryList()
        }

        return null
    }

    private fun loadDirectoryList(): Job {
        val job = launch(UI) {
            modelTs = individualManager.getLastTableModifiedTs()

            val list = run(context + CommonPool) {
                individualManager.findAllBySelection(orderBy = "${IndividualConst.C_FIRST_NAME}, ${IndividualConst.C_LAST_NAME}")
            }

            view.showIndividualList(list)
            view.scrollToPosition(scrollPosition)
        }
        compositeJob.add(job)
        return job
    }

    fun newItemClicked() {
        analytics.send(HitBuilders.EventBuilder()
                .setCategory(Analytics.CATEGORY_INDIVIDUAL)
                .setAction(Analytics.ACTION_NEW)
                .build())

        view.showNewIndividual()
    }

    fun individualClicked(individualId: Long) {
        view.showIndividual(individualId)
    }

}
