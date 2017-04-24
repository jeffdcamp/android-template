package org.jdc.template.ux.directory;

import com.google.android.gms.analytics.HitBuilders;

import org.jdc.template.Analytics;
import org.jdc.template.model.database.main.individual.Individual;
import org.jdc.template.model.database.main.individual.IndividualConst;
import org.jdc.template.model.database.main.individual.IndividualManager;
import org.jdc.template.ui.mvp.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DirectoryPresenter extends BasePresenter {

    private final Analytics analytics;
    private final IndividualManager individualManager;

    private DirectoryContract.View view;
    private long modelTs = 0;

    @Inject
    public DirectoryPresenter(Analytics analytics, IndividualManager individualManager) {
        this.analytics = analytics;
        this.individualManager = individualManager;
    }

    public void init(DirectoryContract.View view) {
        this.view = view;
    }

    @Override
    public void load() {
        super.load();
    }

    @Override
    public void reload(boolean forceRefresh) {
        if (modelTs != individualManager.getLastTableModifiedTs()) {
            modelTs = individualManager.getLastTableModifiedTs();
            loadDirectoryList();
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

    private void loadDirectoryList() {
        modelTs = individualManager.getLastTableModifiedTs();
        Single<List<Individual>> observable = individualManager.findAllBySelectionRx(null, null, IndividualConst.C_FIRST_NAME + ", " + IndividualConst.C_LAST_NAME)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        addDisposable(observable.subscribe(data -> view.showIndividualList(data)));
    }

    public void newItemClicked() {
        analytics.send(new HitBuilders.EventBuilder()
                .setCategory(Analytics.CATEGORY_INDIVIDUAL)
                .setAction(Analytics.ACTION_NEW)
                .build());

        view.showNewIndividual();
    }
}
