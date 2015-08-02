package org.company.project.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.analytics.HitBuilders;
import com.squareup.otto.Bus;

import org.apache.commons.lang3.StringUtils;
import org.company.project.Analytics;
import org.company.project.App;
import org.company.project.R;
import org.company.project.domain.main.individual.Individual;
import org.company.project.domain.main.individual.IndividualManager;
import org.company.project.event.IndividualSavedEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pocketknife.BindArgument;
import pocketknife.PocketKnife;

public class IndividualEditFragment extends Fragment {
    public static final String TAG = App.createTag(IndividualEditFragment.class);

    private static final String ARG_ID = "ID";

    @Bind(R.id.first_name_layout)
    TextInputLayout firstNameLayout;

    @Bind(R.id.first_name)
    EditText firstNameEditText;

    @Bind(R.id.last_name)
    EditText lastNameEditText;

    @Bind(R.id.phone)
    EditText phoneEditText;

    @Bind(R.id.email)
    EditText emailEditText;

    @Inject
    IndividualManager individualManager;

    @Inject
    Bus bus;

    @Inject
    Analytics analytics;

    @BindArgument(ARG_ID)
    long individualId;

    public static IndividualEditFragment newInstance(long individualId) {
        IndividualEditFragment fragment = new IndividualEditFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ID, individualId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PocketKnife.bindArguments(this);
    }

    @Override
    public View onCreateView(@Nonnull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.individual_edit_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInjectComponent(this).inject(this);
        setHasOptionsMenu(true);
        showIndividual();
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

    private void showIndividual() {
        if (individualId <= 0) {
            return;
        }

        Individual individual = individualManager.findByRowId(individualId);
        if (individual != null) {
            analytics.send(new HitBuilders.EventBuilder()
                    .setCategory(Analytics.CATEGORY_INDIVIDUAL)
                    .setAction(Analytics.ACTION_EDIT)
                    .build());

            firstNameEditText.setText(individual.getFirstName());
            lastNameEditText.setText(individual.getLastName());
            emailEditText.setText(individual.getEmail());
            phoneEditText.setText(individual.getPhone());
        }
    }

    private void saveIndividual() {
        Individual individual = individualId > 0 ? individualManager.findByRowId(individualId) : new Individual();
        if (individual != null) {
            if (StringUtils.isBlank(firstNameEditText.getText())) {
                firstNameLayout.setError(getString(R.string.required));
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
}
