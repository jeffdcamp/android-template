package org.jdc.template.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import javax.annotation.Nonnull;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BaseActivity extends AppCompatActivity {

    @Nonnull
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    protected void onStop() {
        compositeSubscription.unsubscribe();
        super.onStop();
    }

    public void addSubscription(@Nonnull Subscription subscription) {
        if (compositeSubscription.isUnsubscribed()) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(subscription);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (allowFinishOnHome() && item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected boolean allowFinishOnHome() {
        return true;
    }

    public void enableActionBarBackArrow(boolean enable) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(enable);
            actionBar.setDisplayHomeAsUpEnabled(enable);
        }
    }
}
