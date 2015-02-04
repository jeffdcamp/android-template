package org.company.project.ui;

import android.app.Activity;
import android.os.Bundle;

import org.company.project.App;
import org.company.project.R;
import org.company.project.task.StartupTask;

import javax.inject.Inject;

public class StartupActivity extends Activity {

    @Inject
    StartupTask startupTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);
        App.inject(this);

        startup();
    }

    private void startup() {
        startupTask.init(this).execute("");
    }

}
