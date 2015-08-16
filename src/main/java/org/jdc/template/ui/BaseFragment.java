package org.jdc.template.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public abstract class BaseFragment extends Fragment {

    @Inject
    EventBus bus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(getLayoutResourceId(), container, false);
        ButterKnife.bind(this, view);
        onPostCreateView();
        return view;
    }

    @LayoutRes
    protected abstract int getLayoutResourceId();

    protected void onPostCreateView() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (registerEventBus()) {
            bus.register(this);
        }
    }

    @Override
    public void onStop() {
        if (registerEventBus()) {
            bus.unregister(this);
        }
        super.onStop();
    }

    public boolean registerEventBus() {
        return false;
    }
}
