package org.jdc.template.ui.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;

import org.dbtools.android.domain.date.DBToolsThreeTenFormatter;
import org.jdc.template.Analytics;
import org.jdc.template.InternalIntents;
import org.jdc.template.R;
import org.jdc.template.inject.Injector;
import org.jdc.template.model.database.main.individual.Individual;
import org.jdc.template.model.database.main.individual.IndividualManager;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pocketknife.BindExtra;
import pocketknife.PocketKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IndividualActivity extends BaseActivity {

    public static final String EXTRA_ID = "INDIVIDUAL_ID";

    @BindView(R.id.mainToolbar)
    Toolbar toolbar;
    @BindView(R.id.nameTextView)
    TextView nameTextView;
    @BindView(R.id.phoneTextView)
    TextView phoneTextView;
    @BindView(R.id.emailTextView)
    TextView emailTextView;
    @BindView(R.id.birthDateTextView)
    TextView birthDateTextView;
    @BindView(R.id.alarmTimeTextView)
    TextView alarmTimeTextView;
    @BindView(R.id.sampleDateTimeTextView)
    TextView sampleDateTimeEditText;

    @BindExtra(EXTRA_ID)
    long individualId;

    @Inject
    InternalIntents internalIntents;
    @Inject
    IndividualManager individualManager;
    @Inject
    Analytics analytics;

    public IndividualActivity() {
        Injector.get().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);
        ButterKnife.bind(this);
        PocketKnife.bindExtras(this);

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

        setupActionBar();

        showIndividual();
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        enableActionBarBackArrow();
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(R.string.individual);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.individual_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_edit:
                internalIntents.editIndividual(this, individualId);
                return true;
            case R.id.menu_item_delete:
                deleteIndividual();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showIndividual();
    }

    private void deleteIndividual() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.delete_individual_confirm)
                .setPositiveButton(R.string.delete, (dialog, which) -> {
                    individualManager.delete(individualId);

                    analytics.send(new HitBuilders.EventBuilder()
                            .setCategory(Analytics.CATEGORY_INDIVIDUAL)
                            .setAction(Analytics.ACTION_DELETE)
                            .build());

                    finish();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void showIndividual() {
        if (individualId <= 0) { // || !this.isVisible() || getActivity() == null) {
            return;
        }

        individualManager.findByRowIdRx(individualId)
                .subscribeOn(Schedulers.io())
                .filter(individual -> individual != null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(individual -> setUi(individual));
    }

    private void setUi(@Nonnull Individual individual) {
        analytics.send(new HitBuilders.EventBuilder()
                .setCategory(Analytics.CATEGORY_INDIVIDUAL)
                .setAction(Analytics.ACTION_VIEW)
                .build());

        nameTextView.setText(individual.getFullName());
        phoneTextView.setText(individual.getPhone());
        emailTextView.setText(individual.getEmail());
        showBirthDate(individual);
        showAlarmTime(individual);
        showSampleDateTime(individual);
    }

    private void showBirthDate(Individual individual) {
        if (individual.getBirthDate() == null) {
            return;
        }

        LocalDate date = individual.getBirthDate();
        long millis = DBToolsThreeTenFormatter.localDateTimeToLong(date.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime());
        birthDateTextView.setText(DateUtils.formatDateTime(this, millis, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    private void showAlarmTime(Individual individual) {
        if (individual.getAlarmTime() == null) {
            return;
        }

        LocalTime time = individual.getAlarmTime();
        long millis = DBToolsThreeTenFormatter.localDateTimeToLong(time.atDate(LocalDate.now()));
        alarmTimeTextView.setText(DateUtils.formatDateTime(this, millis, DateUtils.FORMAT_SHOW_TIME));
    }

    private void showSampleDateTime(Individual individual) {
        if (individual.getSampleDateTime() == null) {
            return;
        }

        LocalDateTime dateTime = individual.getSampleDateTime();
        long millis = DBToolsThreeTenFormatter.localDateTimeToLong(dateTime);
        sampleDateTimeEditText.setText(DateUtils.formatDateTime(this, millis, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME));
    }
}