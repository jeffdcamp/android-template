package org.company.project.activity;

import android.os.Bundle;
import org.company.project.R;
import org.company.project.task.StartupTask;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;

@ContentView(R.layout.startup)
public class StartupActivity extends RoboActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
