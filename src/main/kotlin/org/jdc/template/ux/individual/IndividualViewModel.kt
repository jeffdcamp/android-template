package org.jdc.template.ux.individual

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.analytics.HitBuilders
import org.jdc.template.Analytics
import org.jdc.template.datasource.database.main.individual.Individual
import org.jdc.template.datasource.database.main.individual.IndividualDao
import org.jdc.template.livedata.AbsentLiveData
import javax.inject.Inject

class IndividualViewModel
@Inject constructor(application: Application,
                    private val analytics: Analytics,
                    private val individualDao: IndividualDao) : AndroidViewModel(application) {

    private val individualId = MutableLiveData<Long>()
    val individual: LiveData<Individual>

    init {
        individual = AbsentLiveData.switchMap(individualId) {
            loadIndividual(it)
        }
    }

    fun setIndividualId(individualId: Long) {
        if (individualId != this.individualId.value) {
            this.individualId.value = individualId
        }
    }

    private fun loadIndividual(individualId: Long): LiveData<Individual> {
        analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_INDIVIDUAL).setAction(Analytics.ACTION_VIEW).build())
        return individualDao.findByIdLive(individualId)
    }

    fun deleteIndividual(individualId: Long) {
        individualDao.deleteById(individualId)
        analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_INDIVIDUAL).setAction(Analytics.ACTION_DELETE).build())
    }
}