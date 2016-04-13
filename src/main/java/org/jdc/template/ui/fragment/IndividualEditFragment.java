package org.jdc.template.ui.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.gms.analytics.HitBuilders;

import org.apache.commons.lang3.StringUtils;
import org.dbtools.android.domain.DBToolsDateFormatter;
import org.jdc.template.Analytics;
import org.jdc.template.App;
import org.jdc.template.R;
import org.jdc.template.inject.Injector;
import org.jdc.template.event.IndividualSavedEvent;
import org.jdc.template.model.database.main.individual.Individual;
import org.jdc.template.model.database.main.individual.IndividualManager;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pocketbus.Bus;
import pocketknife.BindArgument;
import pocketknife.PocketKnife;

public class IndividualEditFragment extends Fragment {
    public static final String TAG = App.createTag(IndividualEditFragment.class);

    private static final String ARG_ID = "ID";

    @Bind(R.id.first_name_layout)
    TextInputLayout firstNameLayout;
    @Bind(R.id.alarm_time_layout)
    TextInputLayout alarmTimeLayout;
    @Bind(R.id.first_name)
    EditText firstNameEditText;
    @Bind(R.id.last_name)
    EditText lastNameEditText;
    @Bind(R.id.phone)
    EditText phoneEditText;
    @Bind(R.id.email)
    EditText emailEditText;
    @Bind(R.id.birth_date)
    EditText birthDateEditText;
    @Bind(R.id.alarm_time)
    EditText alarmTimeEditText;

    @Inject
    IndividualManager individualManager;
    @Inject
    Bus bus;
    @Inject
    Analytics analytics;

    @BindArgument(ARG_ID)
    long individualId;

    @Nonnull
    private Individual individual = new Individual();

    private DatePickerDialog birthDatePickerDialog;
    private TimePickerDialog alarmTimePickerDialog;

    public static IndividualEditFragment newInstance(long individualId) {
        IndividualEditFragment fragment = new IndividualEditFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ID, individualId);
        fragment.setArguments(args);
        return fragment;
    }

    public IndividualEditFragment() {
        Injector.get().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PocketKnife.bindArguments(this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@Nonnull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_individual_edit, container, false);
        ButterKnife.bind(this, view);

        showIndividual();
//        birthDateEditText.setInputType(InputType.TYPE_NULL);
//        alarmTimeEditText.setInputType(InputType.TYPE_NULL);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.individual_edit_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
            birthDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    individual.setBirthDate(LocalDate.of(year, monthOfYear + 1, dayOfMonth)); // + 1 because cord Java Date is 0 based
                    showBirthDate();
                }
            }, date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth()); // - 1 because cord Java Date is 0 based
        }

        birthDatePickerDialog.show();
    }

    @OnClick(R.id.alarm_time)
    public void onAlarmClick() {
        if (alarmTimePickerDialog == null) {

            LocalTime time = individual.getAlarmTime() != null ? individual.getAlarmTime() : LocalTime.now();
            alarmTimePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    individual.setAlarmTime(LocalTime.of(hourOfDay, minute));
                    showAlarmTime();
                }

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
        long millis = DBToolsDateFormatter.localDateTimeToLong(date.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime());
        birthDateEditText.setText(DateUtils.formatDateTime(getActivity(), millis, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    private void showAlarmTime() {
        if (individual.getAlarmTime() == null) {
            return;
        }

        LocalTime time = individual.getAlarmTime();
        long millis = DBToolsDateFormatter.localDateTimeToLong(time.atDate(LocalDate.now()));
        alarmTimeEditText.setText(DateUtils.formatDateTime(getActivity(), millis, DateUtils.FORMAT_SHOW_TIME));
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

        bus.post(new IndividualSavedEvent(individual.getId()));
    }
}
