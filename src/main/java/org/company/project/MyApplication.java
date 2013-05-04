package org.company.project;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.IOException;
import java.util.Properties;

/**
 * @author jcampbell
 */
public class MyApplication extends Application {
    public static final String TAG = MyApplication.createTag(MyApplication.class);

    public static final String DEFAULT_TAG_PREFIX = "company.";  // TODO change this for your app (pick a name similar to package name... get both raw log AND tag logs)
    public static final int MAX_TAG_LENGTH = 23; // if over: IllegalArgumentException: Log tag "xxx" exceeds limit of 23 characters

    @Override
    public void onCreate() {
        super.onCreate();
        enableStrictMode();
    }

    public static String createTag(String name) {
        String fullName = DEFAULT_TAG_PREFIX + name;
        return fullName.length() > MAX_TAG_LENGTH ? fullName.substring(0, MAX_TAG_LENGTH) : fullName;
    }

    public static String createTag(Class clazz) {
        return createTag(clazz.getSimpleName());
    }

    public String readBuildNumber() {
        String versionText = null;
        Properties properties = new Properties() ;
        try {
            properties.load(MyApplication.class.getResourceAsStream("/build.properties"));
            versionText = properties.getProperty("build.number");
        } catch (IOException e) {
            Log.e(TAG, "Failed to read build.properties", e);
        }

        if (versionText != null) {
            return versionText.equals("${build.number}") ? "Developer Build" : versionText;
        } else {
            return "Not available";
        }
    }

    public String getVersionText(Context context) throws PackageManager.NameNotFoundException {
        PackageInfo pInfo = context.getPackageManager().getPackageInfo("org.company.project", PackageManager.GET_META_DATA);
        return pInfo.versionName + " (" + readBuildNumber() + ")";
    }

    /**
     * Enable strict mode
     * This only works if compiling against android 2.3 or greater
     * also, this will crash on devices running on less than 2.3
     */
    private void enableStrictMode() {
        android.os.StrictMode.setThreadPolicy(new android.os.StrictMode.ThreadPolicy.Builder()
                //.detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());

        android.os.StrictMode.setVmPolicy(new android.os.StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

}
