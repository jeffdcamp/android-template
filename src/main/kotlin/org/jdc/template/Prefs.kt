package org.jdc.template

import android.content.SharedPreferences

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Prefs @Inject constructor(private val preferences: SharedPreferences) {
    var prefSomething: String
        get() = preferences.getString(PREF_SOMETHING, "")
        set(value) = preferences.edit { putString(PREF_SOMETHING, value) }

    /**
     * Clear All preferences
     */
    fun reset() {
        preferences.edit { clear() }
    }

    companion object {
        private const val PREF_SOMETHING = "PREF_SOMETHING"
    }

    inline fun SharedPreferences.edit(func: SharedPreferences.Editor.() -> Unit) {
        val editor = edit()
        editor.func()
        editor.apply()
    }
}
