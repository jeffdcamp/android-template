package org.jdc.template.ux.individual

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.google.android.gms.analytics.HitBuilders
import org.jdc.template.Analytics
import org.jdc.template.datasource.database.main.individual.Individual
import org.jdc.template.datasource.database.main.individual.IndividualDao
import org.jdc.template.inject.Injector
import javax.inject.Inject

class IndividualViewModel constructor(application: Application): AndroidViewModel(application) {

    @Inject
    lateinit var analytics: Analytics
    @Inject
    lateinit var individualDao: IndividualDao

    init {
        Injector.get().inject(this)
    }

    fun getIndividual(individualId: Long): LiveData<Individual> {
        analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_INDIVIDUAL).setAction(Analytics.ACTION_VIEW).build())
        return individualDao.findByIdLive(individualId)
    }

    fun  deleteIndividual(individualId: Long){
        individualDao.deleteById(individualId)
        analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_INDIVIDUAL).setAction(Analytics.ACTION_DELETE).build())
    }
}