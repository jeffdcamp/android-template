package org.jdc.template.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;

import org.dbtools.android.domain.DBToolsDateFormatter;
import org.jdc.template.Analytics;
import org.jdc.template.App;
import org.jdc.template.R;
import org.jdc.template.dagger.Injector;
import org.jdc.template.model.database.main.individual.Individual;
import org.jdc.template.model.database.main.individual.IndividualManager;
import org.jdc.template.event.EditIndividualEvent;
import org.jdc.template.event.IndividualDeletedEvent;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pocketbus.Bus;
import pocketknife.BindArgument;
import pocketknife.PocketKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class IndividualFragment extends Fragment {
    public static final String TAG = App.createTag(IndividualFragment.class);

    private static final String ARG_ID = "ID";

    @Bind(R.id.individual_name)
    TextView nameTextView;
    @Bind(R.id.individual_phone)
    TextView phoneTextView;
    @Bind(R.id.individual_email)
    TextView emailTextView;
    @Bind(R.id.birth_date)
    TextView birthDateEditText;
    @Bind(R.id.alarm_time)
    TextView alarmTimeEditText;
    @Bind(R.id.sample_datetime)
    TextView sampleDateTimeEditText;

    @Inject
    IndividualManager individualManager;
    @Inject
    Bus bus;
    @Inject
    Analytics analytics;

    @BindArgument(ARG_ID)
    long individualId;

    public static IndividualFragment newInstance(long individualId) {
        IndividualFragment fragment = new IndividualFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ID, individualId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.get().inject(this);
        PocketKnife.bindArguments(this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@Nonnull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_individual, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showIndividual();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.individual_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_edit:
                bus.post(new EditIndividualEvent(individualId));
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
        long millis = DBToolsDateFormatter.localDateTimeToLong(date.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime());
        birthDateEditText.setText(DateUtils.formatDateTime(getActivity(), millis, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    private void showAlarmTime(Individual individual) {
        if (individual.getAlarmTime() == null) {
            return;
        }

        LocalTime time = individual.getAlarmTime();
        long millis = DBToolsDateFormatter.localDateTimeToLong(time.atDate(LocalDate.now()));
        alarmTimeEditText.setText(DateUtils.formatDateTime(getActivity(), millis, DateUtils.FORMAT_SHOW_TIME));
    }

    private void showSampleDateTime(Individual individual) {
        if (individual.getSampleDateTime() == null) {
            return;
        }

        LocalDateTime dateTime = individual.getSampleDateTime();
        long millis = DBToolsDateFormatter.localDateTimeToLong(dateTime);
        sampleDateTimeEditText.setText(DateUtils.formatDateTime(getActivity(), millis, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME));
    }

    private void deleteIndividual() {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.delete_individual_confirm)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        individualManager.delete(individualId);

                        analytics.send(new HitBuilders.EventBuilder()
                                .setCategory(Analytics.CATEGORY_INDIVIDUAL)
                                .setAction(Analytics.ACTION_DELETE)
                                .build());

                        bus.post(new IndividualDeletedEvent(individualId));
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
