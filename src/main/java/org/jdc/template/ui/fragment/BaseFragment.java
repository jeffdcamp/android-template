package org.jdc.template.ui.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.annotation.Nonnull;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseFragment extends Fragment {

    @Nonnull
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
    public void onStop() {
        compositeSubscription.unsubscribe();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    public void addSubscription(@Nonnull Subscription subscription) {
        if (compositeSubscription.isUnsubscribed()) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(subscription);
    }
}
