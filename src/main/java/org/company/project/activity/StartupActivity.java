package org.company.project.activity;

import android.os.Bundle;
import org.company.project.R;
import org.company.project.task.StartupTask;
import roboguice.activity.RoboActivity;

public class StartupActivity extends RoboActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);
        startup();
    }

    private void startup() {
        new StartupTask(this, MainActivity.class).execute();

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//
//            public void run() {
//                Void[] args = null;
//                new StartupTask(this, MainActivity.class).execute(args);
//            }}, 300);
    }

}
