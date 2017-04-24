package org.jdc.template.ux.individualedit;

import com.google.android.gms.analytics.HitBuilders;

import org.jdc.template.Analytics;
import org.jdc.template.model.database.main.individual.Individual;
import org.jdc.template.model.database.main.individual.IndividualManager;
import org.jdc.template.ui.mvp.BasePresenter;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import javax.inject.Inject;

public class IndividualEditPresenter extends BasePresenter {
    private final IndividualManager individualManager;
    private final Analytics analytics;

    private IndividualEditContract.View view;
    private long individualId;
    private Individual individual = new Individual();

    @Inject
    public IndividualEditPresenter(IndividualManager individualManager, Analytics analytics) {
        this.individualManager = individualManager;
        this.analytics = analytics;
    }


    public void init(IndividualEditContract.View view, long individualId) {
        this.view = view;
        this.individualId = individualId;
    }

    @Override
    public void load() {
        loadIndividual();
    }

    private void loadIndividual() {
        individual = individualManager.findByRowId(individualId);
        if (individual != null) {
            analytics.send(new HitBuilders.EventBuilder()
                    .setCategory(Analytics.CATEGORY_INDIVIDUAL)
                    .setAction(Analytics.ACTION_EDIT)
                    .build());

            view.showIndividual(individual);
        }
    }

    public void alarmTimeClicked() {
        view.showAlarmTimeSelector(individual.getAlarmTime());
    }

    public void alarmTimeSelected(LocalTime time) {
            individual.setAlarmTime(time);
            view.showAlarmTime(time);
    }

    public void birthdayClicked() {
        view.showBirthDateSelector(individual.getBirthDate());
    }

    public void birthDateSelected(LocalDate date) {
            individual.setBirthDate(date);
            view.showBirthDate(date);
    }

    public void saveIndividual() {
        if (view.validateIndividualData()) {
            view.getIndividualDataFromUi(individual);

            individualManager.save(individual);
            analytics.send(new HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_INDIVIDUAL).setAction(Analytics.ACTION_EDIT_SAVE).build());

            view.close();
        }
    }
}
