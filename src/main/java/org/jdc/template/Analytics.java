package org.jdc.template;

import com.google.android.gms.analytics.Tracker;

import java.util.Map;

public interface Analytics {
    public static final String TAG = App.createTag(Analytics.class);

    // Categorys
    public String CATEGORY_APP = "App";
    public String CATEGORY_INDIVIDUAL = "Individual";
    public String CATEGORY_ABOUT = "About";
    public String CATEGORY_SETTINGS = "Settings";

    // Actions
    public String ACTION_APP_LAUNCH = "Launch";
    public String ACTION_NEW = "New";
    public String ACTION_VIEW = "View";
    public String ACTION_EDIT = "Edit";
    public String ACTION_DELETE = "Delete";

    // Variables
    String VARIABLE_BUILD_TYPE = "Build Type";

    void send(Map<String, String> params);

    class GoogleAnalytics implements Analytics {
        private final Tracker tracker;

        public GoogleAnalytics(Tracker tracker) {
            this.tracker = tracker;
        }

        @Override
        public void send(Map<String, String> params) {
            tracker.send(params);
        }
    }
}