package org.company.project;

import android.content.SharedPreferences;
import com.google.inject.Singleton;

import javax.inject.Inject;

@Singleton
public final class Prefs {

    public static final String PREF_SOMETHING = "pref_enable_something_id";

    @Inject
    private SharedPreferences preferences;

    private Prefs() {
    }
}
