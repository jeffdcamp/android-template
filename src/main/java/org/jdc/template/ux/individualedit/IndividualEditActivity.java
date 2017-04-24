package org.jdc.template.ux.individualedit;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;
import org.dbtools.android.domain.date.DBToolsThreeTenFormatter;
import org.jdc.template.R;
import org.jdc.template.inject.Injector;
import org.jdc.template.model.database.main.individual.Individual;
import org.jdc.template.ui.activity.BaseActivity;
import org.jdc.template.ux.individual.IndividualContract;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pocketknife.PocketKnife;

import static org.jdc.template.R.string.individual;

public class IndividualEditActivity extends BaseActivity implements IndividualEditContract.View {

    @BindView(R.id.mainToolbar)
    Toolbar toolbar;
    @BindView(R.id.firstNameLayout)
    TextInputLayout firstNameLayout;
    @BindView(R.id.alarmTimeLayout)
    TextInputLayout alarmTimeLayout;
    @BindView(R.id.firstNameEditText)
    EditText firstNameEditText;
    @BindView(R.id.lastNameEditText)
    EditText lastNameEditText;
    @BindView(R.id.phoneEditText)
    EditText phoneEditText;
    @BindView(R.id.emailEditText)
    EditText emailEditText;
    @BindView(R.id.birthDateEditText)
    EditText birthDateEditText;
    @BindView(R.id.alarmTimeEditText)
    EditText alarmTimeEditText;

    private DatePickerDialog birthDatePickerDialog;
    private TimePickerDialog alarmTimePickerDialog;

    @Inject
    IndividualEditPresenter presenter;

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

        long individualId = getIntent().getLongExtra(IndividualContract.Extras.EXTRA_ID, 0L);

        presenter.init(this, individualId);
        presenter.load();
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        enableActionBarBackArrow();
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(individual);
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
                presenter.saveIndividual();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @OnClick(R.id.birthDateEditText)
    public void onBirthdayClick() {
        presenter.birthdayClicked();
    }

    @Override
    public void showBirthDateSelector(LocalDate date) {
        if (birthDatePickerDialog == null) {
            birthDatePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
                LocalDate selectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
                presenter.birthDateSelected(selectedDate);
            }, date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth()); // - 1 because cord Java Date is 0 based
        }

        birthDatePickerDialog.show();
    }

    @OnClick(R.id.alarmTimeEditText)
    public void onAlarmClick() {
        presenter.alarmTimeClicked();
    }

    @Override
    public void showAlarmTimeSelector(LocalTime time) {

        if (alarmTimePickerDialog == null) {
            alarmTimePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                LocalTime selectedTime = LocalTime.of(hourOfDay, minute);
                presenter.alarmTimeSelected(selectedTime);
            }, time.getHour(), time.getMinute(), false);
        }

        alarmTimePickerDialog.show();
    }

    public void showIndividual(Individual individual) {
        firstNameEditText.setText(individual.getFirstName());
        lastNameEditText.setText(individual.getLastName());
        emailEditText.setText(individual.getEmail());
        phoneEditText.setText(individual.getPhone());

        showBirthDate(individual.getBirthDate());
        showAlarmTime(individual.getAlarmTime());
    }

    public void showBirthDate(LocalDate date) {
        long millis = DBToolsThreeTenFormatter.localDateTimeToLong(date.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime());
        birthDateEditText.setText(DateUtils.formatDateTime(this, millis, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    public void showAlarmTime(LocalTime time) {
        long millis = DBToolsThreeTenFormatter.localDateTimeToLong(time.atDate(LocalDate.now()));
        alarmTimeEditText.setText(DateUtils.formatDateTime(this, millis, DateUtils.FORMAT_SHOW_TIME));
    }

    public boolean validateIndividualData() {
        if (StringUtils.isBlank(firstNameEditText.getText())) {
            firstNameLayout.setError(getString(R.string.required));
            return false;
        }

        return true;
    }

    public void getIndividualDataFromUi(Individual individual) {
        individual.setFirstName(firstNameEditText.getText().toString());
        individual.setLastName(lastNameEditText.getText().toString());
        individual.setPhone(phoneEditText.getText().toString());
        individual.setEmail(emailEditText.getText().toString());
    }

    @Override
    public void close() {
        finish();
    }
}