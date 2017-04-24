package org.jdc.template

import android.app.Activity
import android.content.Context
import android.content.Intent
import org.jdc.template.ui.activity.SettingsActivity
import org.jdc.template.ux.about.AboutActivity
import org.jdc.template.ux.individual.IndividualActivity
import org.jdc.template.ux.individual.IndividualContract
import org.jdc.template.ux.individualedit.IndividualEditActivity
import org.jdc.template.ux.individualedit.IndividualEditContract
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternalIntents
@Inject
constructor() {

    fun showIndividual(activity: Activity, individualId: Long) {
        val intent = Intent(activity, IndividualActivity::class.java)
        intent.putExtra(IndividualContract.Extras.EXTRA_ID, individualId)
        activity.startActivity(intent)
    }

    fun newIndividual(activity: Activity) {
        editIndividual(activity, -1)
    }

    fun editIndividual(activity: Activity, individualId: Long) {
        val intent = Intent(activity, IndividualEditActivity::class.java)
        intent.putExtra(IndividualEditContract.Extras.EXTRA_ID, individualId)
        activity.startActivity(intent)
    }

    fun showSettings(context: Context) {
        val settingIntent = Intent(context, SettingsActivity::class.java)
        context.startActivity(settingIntent)
    }

    fun showHelp(context: Context) {
        val aboutIntent = Intent(context, AboutActivity::class.java)
        context.startActivity(aboutIntent)
    }
}
