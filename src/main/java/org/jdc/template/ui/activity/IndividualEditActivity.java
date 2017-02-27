package org.jdc.template.ui.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.analytics.HitBuilders;

import org.apache.commons.lang3.StringUtils;
import org.dbtools.android.domain.date.DBToolsThreeTenFormatter;
import org.jdc.template.Analytics;
import org.jdc.template.R;
import org.jdc.template.inject.Injector;
import org.jdc.template.model.database.main.individual.Individual;
import org.jdc.template.model.database.main.individual.IndividualManager;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pocketknife.BindExtra;
import pocketknife.PocketKnife;

public class IndividualEditActivity extends BaseActivity {

    public static final String EXTRA_ID = "INDIVIDUAL_ID";

    @BindExtra(EXTRA_ID)
    long individualId;

    @BindView(R.id.ab_toolbar)
    Toolbar toolbar;
    @BindView(R.id.first_name_layout)
    TextInputLayout firstNameLayout;
    @BindView(R.id.alarm_time_layout)
    TextInputLayout alarmTimeLayout;
    @BindView(R.id.first_name)
    TextInputEditText firstNameEditText;
    @BindView(R.id.last_name)
    TextInputEditText lastNameEditText;
    @BindView(R.id.phone)
    TextInputEditText phoneEditText;
    @BindView(R.id.email)
    TextInputEditText emailEditText;
    @BindView(R.id.birth_date)
    TextInputEditText birthDateEditText;
    @BindView(R.id.alarm_time)
    TextInputEditText alarmTimeEditText;

    @Inject
    IndividualManager individualManager;
    @Inject
    Analytics analytics;

    @Nonnull
    private Individual individual = new Individual();

    private DatePickerDialog birthDatePickerDialog;
    private TimePickerDialog alarmTimePickerDialog;

    public IndividualEditActivity() {
        Injector.get().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_edit);
        ButterKnife.bind(this);
        PocketKnife.bindExtras(this);

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
        getMenuInflater().inflate(R.menu.individual_edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item == null) {
            return false;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_item_save:
                saveIndividual();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @OnClick(R.id.birth_date)
    public void onBirthdayClick() {
        if (birthDatePickerDialog == null) {

            LocalDate date = individual.getBirthDate() != null ? individual.getBirthDate() : LocalDate.now();
            birthDatePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
                individual.setBirthDate(LocalDate.of(year, monthOfYear + 1, dayOfMonth)); // + 1 because cord Java Date is 0 based
                showBirthDate();
            }, date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth()); // - 1 because cord Java Date is 0 based
        }

        birthDatePickerDialog.show();
    }

    @OnClick(R.id.alarm_time)
    public void onAlarmClick() {
        if (alarmTimePickerDialog == null) {

            LocalTime time = individual.getAlarmTime() != null ? individual.getAlarmTime() : LocalTime.now();
            alarmTimePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                individual.setAlarmTime(LocalTime.of(hourOfDay, minute));
                showAlarmTime();
            }, time.getHour(), time.getMinute(), false);
        }

        alarmTimePickerDialog.show();
    }

    private void showIndividual() {
        if (individualId <= 0) {
            return;
        }

        Individual foundIndividual = individualManager.findByRowId(individualId);
        if (foundIndividual != null) {
            individual = foundIndividual;
            analytics.send(new HitBuilders.EventBuilder()
                    .setCategory(Analytics.CATEGORY_INDIVIDUAL)
                    .setAction(Analytics.ACTION_EDIT)
                    .build());

            firstNameEditText.setText(individual.getFirstName());
            lastNameEditText.setText(individual.getLastName());
            emailEditText.setText(individual.getEmail());
            phoneEditText.setText(individual.getPhone());

            showBirthDate();
            showAlarmTime();
        }
    }

    private void showBirthDate() {
        if (individual.getBirthDate() == null) {
            return;
        }

        LocalDate date = individual.getBirthDate();
        long millis = DBToolsThreeTenFormatter.localDateTimeToLong(date.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime());
        birthDateEditText.setText(DateUtils.formatDateTime(this, millis, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    private void showAlarmTime() {
        if (individual.getAlarmTime() == null) {
            return;
        }

        LocalTime time = individual.getAlarmTime();
        long millis = DBToolsThreeTenFormatter.localDateTimeToLong(time.atDate(LocalDate.now()));
        alarmTimeEditText.setText(DateUtils.formatDateTime(this, millis, DateUtils.FORMAT_SHOW_TIME));
    }

    private void saveIndividual() {
        if (StringUtils.isBlank(firstNameEditText.getText())) {
            firstNameLayout.setError(getString(R.string.required));
            return;
        }

        if (individual.getAlarmTime() == null) {
            alarmTimeLayout.setError(getString(R.string.required));
            return;
        }

        individual.setFirstName(firstNameEditText.getText().toString());
        individual.setLastName(lastNameEditText.getText().toString());
        individual.setPhone(phoneEditText.getText().toString());
        individual.setEmail(emailEditText.getText().toString());

        individualManager.save(individual);

        finish();
    }
}