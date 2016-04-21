package org.jdc.template

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.util.Log
import com.bluelinelabs.logansquare.LoganSquare
import com.evernote.android.job.JobManager
import com.jakewharton.threetenabp.AndroidThreeTen
import org.jdc.template.inject.Injector
import org.jdc.template.job.AppJobCreator
import org.jdc.template.model.webservice.DateTimeTypeConverter
import org.threeten.bp.LocalDateTime
import pocketbus.Registry

@Registry // PocketBus Registry
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        Injector.init(this)
        JobManager.create(this).addJobCreator(AppJobCreator())

        // register json global converters
        registerJsonConverters();
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        if (filesDir != null) {
            MultiDex.install(this)
        } else {
            // During app install it might have experienced "INSTALL_FAILED_DEXOPT" (reinstall is the only known work-around)
            // https://code.google.com/p/android/issues/detail?id=8886
            val message = getString(R.string.app_name) + " is in a bad state, please uninstall/reinstall"
            Log.e(TAG, message)
        }
    }

    fun registerJsonConverters() {
        LoganSquare.registerTypeConverter(LocalDateTime::class.java, DateTimeTypeConverter());
    }

    companion object {
        val TAG = createTag(App::class.java)

        // TODO change this for your app (pick a name similar to package name... get both raw log AND tag logs)
        val DEFAULT_TAG_PREFIX = "company."
        val MAX_TAG_LENGTH = 23 // if over: IllegalArgumentException: Log tag "xxx" exceeds limit of 23 characters

        fun createTag(clazz: Class<*>): String {
            return clazz.simpleName
        }

        fun createTag(name: String): String {
            val fullName = DEFAULT_TAG_PREFIX + name
            return if (fullName.length > MAX_TAG_LENGTH) fullName.substring(0, MAX_TAG_LENGTH) else fullName
        }
    }

}
