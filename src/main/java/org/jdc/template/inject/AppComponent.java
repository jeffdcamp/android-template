package org.jdc.template.inject;

import android.app.Application;

import org.jdc.template.App;
import org.jdc.template.ux.about.AboutActivity;
import org.jdc.template.ux.directory.DirectoryActivity;
import org.jdc.template.ux.individual.IndividualActivity;
import org.jdc.template.ux.individualedit.IndividualEditActivity;
import org.jdc.template.ui.activity.SettingsActivity;
import org.jdc.template.ux.startup.StartupActivity;
import org.jdc.template.ux.directory.DirectoryAdapter;
import org.jdc.template.ui.fragment.SettingsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    // UI
    void inject(App application);
    void inject(StartupActivity target);
    void inject(DirectoryActivity target);
    void inject(IndividualActivity target);
    void inject(IndividualEditActivity target);
    void inject(SettingsActivity target);
    void inject(AboutActivity target);

    // Adapters
    void inject(DirectoryAdapter target);

    // Exported for child-components.
    Application application();

    void inject(SettingsFragment target);
}
