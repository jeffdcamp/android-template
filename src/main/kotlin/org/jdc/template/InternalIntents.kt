package org.jdc.template

import android.app.Activity
import android.content.Context
import android.content.Intent
import org.jdc.template.ui.AboutActivity
import org.jdc.template.ui.IndividualActivity
import org.jdc.template.ui.IndividualEditActivity
import org.jdc.template.ui.SettingsActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternalIntents
@Inject
constructor() {

    fun showIndividual(activity: Activity, individualId: Long) {
        val intent = Intent(activity, IndividualActivity::class.java)
        intent.putExtra(IndividualActivity.EXTRA_ID, individualId)
        activity.startActivity(intent)
    }

    fun newIndividual(activity: Activity) {
        val intent = Intent(activity, IndividualEditActivity::class.java)
        intent.putExtra(IndividualEditActivity.EXTRA_ID, -1)
        activity.startActivity(intent)
    }

    fun editIndividual(activity: Activity, individualId: Long) {
        val intent = Intent(activity, IndividualEditActivity::class.java)
        intent.putExtra(IndividualEditActivity.EXTRA_ID, individualId)
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
