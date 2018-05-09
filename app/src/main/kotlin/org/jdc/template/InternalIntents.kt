package org.jdc.template

import android.app.Activity
import android.content.Context
import android.content.Intent
import org.jdc.template.ui.activity.SettingsActivity
import org.jdc.template.ux.about.AboutActivity
import org.jdc.template.ux.individual.IndividualActivity
import org.jdc.template.ux.individualedit.IndividualEditActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternalIntents
@Inject
constructor() {

    fun showIndividual(activity: Activity, individualId: Long) {
        IndividualActivity.start(activity) {
            it.individualId = individualId
        }
    }

    fun newIndividual(activity: Activity) {
        IndividualEditActivity.start(activity)
    }

    fun editIndividual(activity: Activity, individualId: Long) {
        IndividualEditActivity.start(activity) {
            it.individualId = individualId
        }
    }

    fun showSettings(context: Context) {
        val settingIntent = Intent(context, SettingsActivity::class.java)
        context.startActivity(settingIntent)
    }

    fun showHelp(context: Context) {
        AboutActivity.start(context)
    }
}
