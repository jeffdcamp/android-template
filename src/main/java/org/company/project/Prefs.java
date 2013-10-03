package org.company.project;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class Prefs {

    public static final String PREF_SOMETHING = "pref_enable_something_id";

    @Inject
    public SharedPreferences preferences;

    public void reset() {
        // clear ALL preferences
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public String getPrefSomething() {
        return preferences.getString(PREF_SOMETHING, "");
    }

    public void savePromptDatabaseUpdated(String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_SOMETHING, value);
        editor.commit();
    }
}
