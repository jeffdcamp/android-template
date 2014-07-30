package org.company.project.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.company.project.MyApplication;
import org.company.project.R;
import org.company.project.domain.individual.Individual;
import org.company.project.domain.individual.IndividualManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class IndividualFragment extends Fragment {
    public static final String TAG = MyApplication.createTag(IndividualFragment.class);

    private static final String ARG_ID = "ID";

    @InjectView(R.id.individual_name)
    TextView nameTextView;

    @InjectView(R.id.individual_subtitle)
    TextView subtitleTextView;

    @Inject
    IndividualManager individualManager;

    private long individualId;

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

        if (getArguments() != null) {
            individualId = getArguments().getLong(ARG_ID, -1);
        }
    }

    @Override
    public View onCreateView(@Nonnull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.individual_fragment, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.injectFragment(this);

        showIndividual();
    }

    private void showIndividual() {
        if (individualId <= 0) {
            return;
        }

        Individual individual = individualManager.findByRowId(individualId);
        if (individual != null) {
            nameTextView.setText(individual.getFullName());
            subtitleTextView.setText(individual.getPhone());
        }
    }
}
