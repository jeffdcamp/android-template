package org.company.project.ui.tv;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import org.company.project.R;

public class TVVideoSelectionActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_video_selection_activity);
    }

    @Override
    public boolean onSearchRequested() {
        return true;
    }
}
