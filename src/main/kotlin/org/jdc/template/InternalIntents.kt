package org.jdc.template

import android.app.Activity
import android.content.Context
import android.content.Intent
import org.jdc.template.ui.activity.SettingsActivity
import org.jdc.template.ux.about.AboutContract
import org.jdc.template.ux.individual.IndividualContract
import org.jdc.template.ux.individualedit.IndividualEditContract
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternalIntents
@Inject
constructor() {

    fun showIndividual(activity: Activity, individualId: Long) {
        IndividualContract.start(activity) {
            it.individualId = individualId
        }
    }

    fun newIndividual(activity: Activity) {
        IndividualEditContract.start(activity)
    }

    fun editIndividual(activity: Activity, individualId: Long) {
        IndividualEditContract.start(activity) {
            it.individualId = individualId
        }
    }

    fun showSettings(context: Context) {
        val settingIntent = Intent(context, SettingsActivity::class.java)
        context.startActivity(settingIntent)
    }

    fun showHelp(context: Context) {
        AboutContract.start(context)
    }
}
