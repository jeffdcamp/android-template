package org.company.project;

import android.app.Application;
import android.util.Log;

import java.io.IOException;
import java.util.Properties;

/**
 * @author jcampbell
 */
public class MyApplication extends Application {
    public static final String TAG = MyApplication.createTag(MyApplication.class);

    public static final String DEFAULT_TAG_PREFIX = "myApp.";  // TODO change this for your app
    public static final int MAX_TAG_LENGTH = 23; // if over: IllegalArgumentException: Log tag "xxx" exceeds limit of 23 characters

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
}
