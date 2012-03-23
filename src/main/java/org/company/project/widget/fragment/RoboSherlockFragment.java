package org.company.project.widget.fragment;

import android.os.Bundle;
import android.view.View;
import com.actionbarsherlock.app.SherlockFragment;
import roboguice.RoboGuice;

public abstract class RoboSherlockFragment extends SherlockFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RoboGuice.getInjector(getActivity()).injectMembersWithoutViews(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RoboGuice.getInjector(getActivity()).injectViewMembers(this);
    }
}
