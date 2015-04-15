package org.company.project;

import android.app.Application;

import org.company.project.ui.AboutActivity;
import org.company.project.ui.DirectoryActivity;
import org.company.project.ui.DirectoryFragment;
import org.company.project.ui.IndividualActivity;
import org.company.project.ui.IndividualEditActivity;
import org.company.project.ui.IndividualEditFragment;
import org.company.project.ui.IndividualFragment;
import org.company.project.ui.SettingsActivity;
import org.company.project.ui.StartupActivity;
import org.company.project.ui.loader.DirectoryListLoader;
import org.company.project.ui.tv.TVVideoSelectionFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    // UI
    void inject(App application);
    void inject(StartupActivity target);
    void inject(DirectoryActivity target);
    void inject(DirectoryFragment target);
    void inject(IndividualActivity target);
    void inject(IndividualFragment target);
    void inject(IndividualEditActivity target);
    void inject(IndividualEditFragment target);
    void inject(SettingsActivity target);
    void inject(AboutActivity target);

    void inject(TVVideoSelectionFragment target);

    // Loaders
    void inject(DirectoryListLoader target);

    // Exported for child-components.
    Application application();
}
