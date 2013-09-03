package org.company.project;

import org.company.project.activity.AboutActivity;
import org.company.project.activity.MainActivity;
import org.company.project.activity.SettingsActivity;
import org.company.project.activity.StartupActivity;

import dagger.Module;

@Module(
        injects = {
                // Activities & Fragments
                StartupActivity.class,
                AboutActivity.class,
                MainActivity.class,
                SettingsActivity.class,

                // Adapters

                // Loaders

                // Misc
        },
        complete = false // complete=true will fail the build if any dependency is missing. Use this to make sure you haven't forgotten any @Provides methods.
)
public class ApplicationModules {
    private final MyApplication application;

    public ApplicationModules(MyApplication application) {
        this.application = application;
    }

}
