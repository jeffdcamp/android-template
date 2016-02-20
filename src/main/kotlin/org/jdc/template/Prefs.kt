package org.jdc.template

import android.content.SharedPreferences

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Prefs
@Inject
constructor(private val preferences: SharedPreferences) {

    fun reset() {
        // clear ALL preferences
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

    val prefSomething: String
        get() = preferences.getString(PREF_SOMETHING, "")

    fun savePromptDatabaseUpdated(value: String) {
        val editor = preferences.edit()
        editor.putString(PREF_SOMETHING, value)
        editor.apply()
    }

    companion object {
        val PREF_SOMETHING = "pref_enable_something_id"
    }
}
