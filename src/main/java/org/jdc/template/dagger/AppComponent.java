package org.jdc.template.dagger;

import android.app.Application;

import org.jdc.template.App;
import org.jdc.template.ui.AboutActivity;
import org.jdc.template.ui.DirectoryActivity;
import org.jdc.template.ui.DirectoryFragment;
import org.jdc.template.ui.IndividualActivity;
import org.jdc.template.ui.IndividualEditActivity;
import org.jdc.template.ui.IndividualEditFragment;
import org.jdc.template.ui.IndividualFragment;
import org.jdc.template.ui.SettingsActivity;
import org.jdc.template.ui.SettingsFragment;
import org.jdc.template.ui.StartupActivity;
import org.jdc.template.ui.adapter.DirectoryAdapter;
import org.jdc.template.ui.tv.TVVideoSelectionFragment;

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

    // Adapters
    void inject(DirectoryAdapter target);

    // Exported for child-components.
    Application application();

    void inject(SettingsFragment target);
}
