package org.company.project.task;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import org.company.project.MyApplication;
import org.company.project.R;
import roboguice.RoboGuice;

import java.lang.reflect.Method;

public class StartupTask extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = MyApplication.createTag(StartupTask.class);

    private long perfTime = 0;

    private Activity contextActivity;
    private Class startupActivityClass;

    public StartupTask(Activity contextActivity, Class startupActivityClass) {
        RoboGuice.getInjector(contextActivity).injectMembers(this);
        this.contextActivity = contextActivity;
        this.startupActivityClass = startupActivityClass;
    }

    @Override
    public void onPreExecute() {
        perfTime = System.currentTimeMillis();
    }

    @Override
    protected Boolean doInBackground(Void... params) {



        return true;
    }

    @Override
    public void onPostExecute(Boolean result) {
        Intent i = new Intent(contextActivity, startupActivityClass);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        contextActivity.startActivity(i);
        Log.d(TAG, "Startup Elapsed Time:" + (System.currentTimeMillis() - perfTime) + "ms");

        contextActivity.finish();
        try {
            Method override = Activity.class.getMethod("overridePendingTransition", new Class[] {Integer.TYPE, Integer.TYPE});
            override.invoke(contextActivity, R.anim.fade_in, R.anim.nothing);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}