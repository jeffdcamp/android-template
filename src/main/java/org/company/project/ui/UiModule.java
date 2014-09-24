package org.company.project.ui;

import org.company.project.ui.adapter.AdapterModule;
import org.company.project.ui.loader.LoaderModule;

import dagger.Module;

@Module(
        includes = {
                AdapterModule.class,
                LoaderModule.class,
        },

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
public class UiModule {
}
