package org.company.project.task;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.company.project.MyApplication;
import org.company.project.R;
import org.company.project.ui.DirectoryActivity;
import org.company.project.domain.DatabaseManager;

import javax.inject.Inject;

public class StartupTask extends AsyncTask<String, Void, Boolean> {

    private static final String TAG = MyApplication.createTag(StartupTask.class);

    private long perfTime = 0;

    private Activity contextActivity;
    private Class startupActivityClass = DirectoryActivity.class;

    @Inject
    DatabaseManager databaseManager;

    public StartupTask init(Activity contextActivity) {
        this.contextActivity = contextActivity;
        return this;
    }

    @Override
    public void onPreExecute() {
        perfTime = System.currentTimeMillis();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        databaseManager.initDatabaseConnection();
        return true;
    }

    @Override
    public void onPostExecute(Boolean result) {
        Log.d(TAG, "Startup Elapsed Time:" + (System.currentTimeMillis() - perfTime) + "ms");

        Intent i = new Intent(contextActivity, startupActivityClass);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        contextActivity.startActivity(i);
        contextActivity.finish();
        contextActivity.overridePendingTransition(R.anim.fade_in, R.anim.nothing); // no animation
    }
}