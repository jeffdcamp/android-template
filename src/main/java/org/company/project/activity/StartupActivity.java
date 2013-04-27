package org.company.project.activity;

import android.os.Bundle;
import org.company.project.R;
import org.company.project.task.StartupTask;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;

import javax.inject.Inject;

@ContentView(R.layout.startup)
public class StartupActivity extends RoboActivity {

    @Inject
    private StartupTask startupTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startup();
    }

    private void startup() {
        startupTask.init(this).execute("");
    }

}
