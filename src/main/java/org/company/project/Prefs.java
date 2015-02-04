package org.company.project;

import android.content.SharedPreferences;

import com.squareup.otto.Bus;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class Prefs {

    public static final String PREF_SOMETHING = "pref_enable_something_id";

    private SharedPreferences preferences;
    private Bus bus; // use bus to implement Otto @Produce as needed

    @Inject
    public Prefs(SharedPreferences preferences, Bus bus) {
        this.bus = bus;
        this.preferences = preferences;

        this.bus.register(this);
    }

    public void reset() {
        // clear ALL preferences
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public String getPrefSomething() {
        return preferences.getString(PREF_SOMETHING, "");
    }

    public void savePromptDatabaseUpdated(String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_SOMETHING, value);
        editor.apply();
    }
}
