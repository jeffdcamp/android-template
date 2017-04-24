package org.jdc.template.ux.about;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.jdc.template.BuildConfig;
import org.jdc.template.R;
import org.jdc.template.inject.Injector;
import org.jdc.template.ui.activity.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity implements AboutContract.View {
    @BindView(R.id.versionTextView)
    TextView versionTextView;
    @BindView(R.id.versionDateTextView)
    TextView versionDateTextView;
    @BindView(R.id.mainToolbar)
    Toolbar toolbar;

    @Inject
    AboutPresenter presenter;

    public AboutActivity() {
        Injector.get().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        enableActionBarBackArrow();

        versionTextView.setText(BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")");
        versionDateTextView.setText(DateUtils.formatDateTime(this, BuildConfig.BUILD_TIME, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_YEAR));

        presenter.init(this);
        presenter.load();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.register();
    }

    @Override
    protected void onStop() {
        presenter.unregister();
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.createDatabaseButton)
    public void onCreateDatabaseButtonClick() {
        presenter.createSampleData();
    }

    @OnClick(R.id.jobTestButton)
    public void onJobTestClick() {
        presenter.jobTest();
    }

    @OnClick(R.id.restTestButton)
    public void onRestTestButtonClick() {
        presenter.testQueryWebServiceCallRx();
    }

    @OnClick(R.id.rxTestButton)
    public void onTestRxClick() {
        presenter.testRx();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
