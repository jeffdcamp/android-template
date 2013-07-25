package org.company.project.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.company.project.ForApplication;
import org.company.project.MyApplication;
import org.company.project.R;
import org.company.project.activity.MainActivity;
import org.company.project.domain.DatabaseManager;

import javax.inject.Inject;

public class StartupTask extends AsyncTask<String, Void, Boolean> {

    private static final String TAG = MyApplication.createTag(StartupTask.class);

    private long perfTime = 0;

    @Inject
    @ForApplication
    public Context context;

    @Inject
    public DatabaseManager databaseManager;

    private Activity contextActivity;
    private Class startupActivityClass = MainActivity.class;

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
        if (databaseManager.getDatabase(DatabaseManager.MAIN_DATABASE_NAME) == null) {
            databaseManager.addDatabase(context, DatabaseManager.MAIN_DATABASE_NAME, DatabaseManager.DATABASE_VERSION);
        }


        databaseManager.getWritableDatabase(DatabaseManager.MAIN_DATABASE_NAME);

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