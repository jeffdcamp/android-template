package org.jdc.template.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseFragment extends Fragment {

    @Inject
    EventBus bus;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

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

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        compositeSubscription.unsubscribe();
        super.onDestroyView();
    }

    public boolean registerEventBus() {
        return false;
    }

    public void addSubscription(@Nonnull Subscription subscription) {
        compositeSubscription.add(subscription);
    }
}
