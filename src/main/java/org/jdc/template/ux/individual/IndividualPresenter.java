package org.jdc.template.ux.individual;


import com.google.android.gms.analytics.HitBuilders;

import org.jdc.template.Analytics;
import org.jdc.template.model.database.main.individual.Individual;
import org.jdc.template.model.database.main.individual.IndividualManager;
import org.jdc.template.ui.mvp.BasePresenter;

import javax.inject.Inject;

public class IndividualPresenter extends BasePresenter {
    private final IndividualManager individualManager;
    private final Analytics analytics;

    private IndividualContract.View view;
    private long individualId;
    private long modelTs = 0L;

    @Inject
    public IndividualPresenter(IndividualManager individualManager, Analytics analytics) {
        this.individualManager = individualManager;
        this.analytics = analytics;
    }

    public void init(IndividualContract.View view, long individualId) {
        this.view = view;
        this.individualId = individualId;
    }

    @Override
    public void load() {
        analytics.send(new HitBuilders.EventBuilder()
                .setCategory(Analytics.CATEGORY_INDIVIDUAL)
                .setAction(Analytics.ACTION_VIEW)
                .build());
        loadIndividual();
    }

    @Override
    public void reload(boolean forceRefresh) {
        if (forceRefresh || modelTs != individualManager.getLastTableModifiedTs()) {
            loadIndividual();
        }
    }

    @Override
    public void register() {
        super.register();
    }

    @Override
    public void unregister() {
        super.unregister();
    }

    private void loadIndividual() {
        if (individualId <= 0) {
            return;
        }

        modelTs = individualManager.getLastTableModifiedTs();

        Individual individual = individualManager.findByRowId(individualId);
        if (individual != null) {
            view.showIndividual(individual);
        }
    }

    void deleteIndividualClicked() {
        view.promptDeleteIndividual();
    }

    public void deleteIndividual() {
        individualManager.delete(individualId);

        analytics.send(new HitBuilders.EventBuilder()
                .setCategory(Analytics.CATEGORY_INDIVIDUAL)
                .setAction(Analytics.ACTION_DELETE)
                .build());

        view.close();
    }

    void editIndividualClicked() {
        view.showEditIndividual(individualId);
    }
}
