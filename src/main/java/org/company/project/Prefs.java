package org.company.project;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class Prefs {

    public static final String PREF_SOMETHING = "pref_enable_something_id";

    @Inject
    public SharedPreferences preferences;

    private Prefs() {
    }
}
