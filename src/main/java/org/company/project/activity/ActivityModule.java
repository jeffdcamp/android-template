package org.company.project.activity;

import dagger.Module;

@Module(
        injects = {
                // Activities & Fragments
                StartupActivity.class,
                AboutActivity.class,
                DirectoryActivity.class,
                DirectoryFragment.class,
                IndividualActivity.class,
                IndividualFragment.class,
                SettingsActivity.class,
        },
        complete = false
)
public class ActivityModule {
}
