package org.company.project.activity;

import dagger.Module;

@Module(
        injects = {
                // Activities & Fragments
                StartupActivity.class,
                AboutActivity.class,
                MainActivity.class,
                MainFragment.class,
                SettingsActivity.class,
        },
        complete = false
)
public class ActivityModule {
}
