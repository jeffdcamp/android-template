package org.jdc.template;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.greenrobot.event.EventBus;

@Singleton
public final class Prefs {

    public static final String PREF_SOMETHING = "pref_enable_something_id";

    private SharedPreferences preferences;
    private EventBus bus;

    @Inject
    public Prefs(SharedPreferences preferences, EventBus bus) {
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
