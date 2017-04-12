package org.jdc.template.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;

import org.apache.commons.io.FileUtils;
import org.dbtools.android.domain.DatabaseTableChange;
import org.jdc.template.Analytics;
import org.jdc.template.BuildConfig;
import org.jdc.template.R;
import org.jdc.template.event.NewDataEvent;
import org.jdc.template.inject.Injector;
import org.jdc.template.job.SampleJob;
import org.jdc.template.model.database.AppDatabaseConfig;
import org.jdc.template.model.database.DatabaseManager;
import org.jdc.template.model.database.DatabaseManagerConst;
import org.jdc.template.model.database.main.household.Household;
import org.jdc.template.model.database.main.household.HouseholdManager;
import org.jdc.template.model.database.main.individual.Individual;
import org.jdc.template.model.database.main.individual.IndividualManager;
import org.jdc.template.model.database.other.individuallist.IndividualList;
import org.jdc.template.model.database.other.individuallist.IndividualListManager;
import org.jdc.template.model.database.other.individuallistitem.IndividualListItem;
import org.jdc.template.model.database.other.individuallistitem.IndividualListItemManager;
import org.jdc.template.model.type.IndividualType;
import org.jdc.template.model.webservice.colors.ColorService;
import org.jdc.template.model.webservice.colors.dto.DtoColor;
import org.jdc.template.model.webservice.colors.dto.DtoColors;
import org.jdc.template.util.RxUtil;
import org.jdc.template.util.WebServiceUtil;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import pocketbus.Bus;
import pocketbus.Subscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class AboutActivity extends BaseActivity {
    @BindView(R.id.versionTextView)
    TextView versionTextView;
    @BindView(R.id.versionDateTextView)
    TextView versionDateTextView;
    @BindView(R.id.mainToolbar)
    Toolbar toolbar;

    @Inject
    Analytics analytics;
    @Inject
    Bus bus;
    @Inject
    IndividualManager individualManager;
    @Inject
    HouseholdManager householdManager;
    @Inject
    IndividualListManager individualListManager;
    @Inject
    IndividualListItemManager individualListItemManager;

    @Inject
    ColorService colorService;
    @Inject
    WebServiceUtil webServiceUtil;

    public AboutActivity() {
        Injector.get().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        analytics.send(new HitBuilders.EventBuilder()
                .setCategory(Analytics.CATEGORY_ABOUT)
                .build());

        setSupportActionBar(toolbar);
        enableActionBarBackArrow();

        versionTextView.setText(BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")");
        versionDateTextView.setText(DateUtils.formatDateTime(this, BuildConfig.BUILD_TIME, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_YEAR));
    }

    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    protected void onStop() {
        bus.unregister(this);
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.createDatabaseButton)
    public void onCreateDatabaseButtonClick() {
        createSampleData();
    }

    private void createSampleData() {
        createSampleDataWithInjection();
//        createSampleDataNoInjection();
    }

    /**
     * Creates sample data WITH using injection
     */
    private void createSampleDataWithInjection() {
        // clear any existing items
        individualManager.deleteAll();
        householdManager.deleteAll();
        individualListManager.deleteAll();

        // MAIN Database
        householdManager.beginTransaction();

        Household household = new Household();
        household.setName("Campbell");
        householdManager.save(household);

        Individual individual1 = new Individual();
        individual1.setFirstName("Jeff");
        individual1.setLastName("Campbell");
        individual1.setPhone("801-555-0000");
        individual1.setIndividualType(IndividualType.HEAD);
        individual1.setIndividualTypeText(IndividualType.HEAD);
        individual1.setHouseholdId(household.getId());
        individual1.setBirthDate(LocalDate.of(1970, 1, 1));
        individual1.setAlarmTime(LocalTime.of(7, 0));
        individualManager.save(individual1);

        Individual individual2 = new Individual();
        individual2.setFirstName("John");
        individual2.setLastName("Miller");
        individual2.setPhone("303-555-1111");
        individual2.setIndividualType(IndividualType.CHILD);
        individual2.setIndividualTypeText(IndividualType.CHILD);
        individual2.setHouseholdId(household.getId());
        individual1.setBirthDate(LocalDate.of(1970, 1, 2));
        individual2.setAlarmTime(LocalTime.of(6, 0));
        individualManager.save(individual2);

        householdManager.endTransaction(true);

        // OTHER Database
        individualListManager.beginTransaction();
        IndividualList newList = new IndividualList();
        newList.setName("My List");
        individualListManager.save(newList);

        IndividualListItem newListItem = new IndividualListItem();
        newListItem.setListId(newList.getId());
        newListItem.setIndividualId(individual1.getId());
        individualListItemManager.save(newListItem);

        individualListManager.endTransaction(true);
    }

    /**
     * Creates sample data WITHOUT using injection
     */
    private void createSampleDataNoInjection() {
        DatabaseManager noInjectionDatabaseManager = new DatabaseManager(new AppDatabaseConfig(getApplication()));

        HouseholdManager householdManager = new HouseholdManager(noInjectionDatabaseManager);
        IndividualManager individualManager = new IndividualManager(noInjectionDatabaseManager);
        IndividualListManager individualListManager = new IndividualListManager(noInjectionDatabaseManager);
        IndividualListItemManager individualListItemManager = new IndividualListItemManager(noInjectionDatabaseManager);

        // Main Database
        noInjectionDatabaseManager.beginTransaction(DatabaseManagerConst.MAIN_DATABASE_NAME);

        Household household = new Household();
        household.setName("Campbell");
        householdManager.save(DatabaseManagerConst.MAIN_DATABASE_NAME, household);

        Individual individual1 = new Individual();
        individual1.setFirstName("Jeff");
        individual1.setLastName("Campbell");
        individual1.setPhone("000-555-1234");
        individual1.setIndividualType(IndividualType.HEAD);
        individual1.setIndividualTypeText(IndividualType.HEAD);
        individual1.setHouseholdId(household.getId());
        individual1.setAmount1(19.95F);
        individual1.setAmount2(1000000000.25D);
        individual1.setEnabled(true);
        individualManager.save(DatabaseManagerConst.MAIN_DATABASE_NAME, individual1);

        Individual individual2 = new Individual();
        individual2.setFirstName("Tanner");
        individual2.setLastName("Campbell");
        individual2.setIndividualType(IndividualType.CHILD);
        individual2.setIndividualTypeText(IndividualType.CHILD);
        individual2.setHouseholdId(household.getId());
        individual2.setAmount1(21.95F);
        individual2.setAmount2(2000000000.25D);
        individual2.setEnabled(false);
        individualManager.save(DatabaseManagerConst.MAIN_DATABASE_NAME, individual2);
        noInjectionDatabaseManager.endTransaction(DatabaseManagerConst.MAIN_DATABASE_NAME, true);


        // Other Database
        noInjectionDatabaseManager.beginTransaction(DatabaseManagerConst.OTHER_DATABASE_NAME);

        IndividualList newList = new IndividualList();
        newList.setName("My List");
        individualListManager.save(DatabaseManagerConst.MAIN_DATABASE_NAME, newList);

        IndividualListItem newListItem = new IndividualListItem();
        newListItem.setListId(newList.getId());
        newListItem.setIndividualId(individual1.getId());
        individualListItemManager.save(DatabaseManagerConst.MAIN_DATABASE_NAME, newListItem);

        noInjectionDatabaseManager.endTransaction(DatabaseManagerConst.OTHER_DATABASE_NAME, true);

        Toast.makeText(this, "Database created", Toast.LENGTH_SHORT).show();
    }

    /**
     * Simple web service call
     */
    //    @OnClick(R.id.rest_test_button)
    public void testQueryWebServiceCall() {
        Call<DtoColors> call = colorService.colors();

        call.enqueue(new Callback<DtoColors>() {
            @Override
            public void onResponse(Call<DtoColors> call, Response<DtoColors> response) {
                processColorsResponse(response);
            }

            @Override
            public void onFailure(Call<DtoColors> call, Throwable t) {
                Timber.e(t, "Search FAILED");
            }
        });
    }

    /**
     * Simple web service call using Rx
     */
    @OnClick(R.id.restTestButton)
    public void testQueryWebServiceCallRx() {
        RxUtil.toRetrofitObservable(colorService.colors())
                .subscribeOn(Schedulers.io())
                .map(response -> RxUtil.verifyRetrofitResponse(response))
                .filter(dtoSearchResponse -> dtoSearchResponse != null) // don't continue if null
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dtoColors -> processColorsResponse(dtoColors), throwable -> bus.post(new NewDataEvent(false, throwable)), () -> bus.post(new NewDataEvent(true)));
    }

    /**
     * Bus event example (when rest call finishes)
     */
    @Subscribe
    public void handle(NewDataEvent event) {
        Timber.i(event.getThrowable(), "Rest Service finished [%b]", event.isSuccess());
    }

    /**
     * Simple web service call using the full url (instead of just an endpoint)
     */
    //    @OnClick(R.id.rest_test_button)
    public void testFullUrlQueryWebServiceCall() {
        Call<DtoColors> call = colorService.colorsByFullUrl(ColorService.FULL_URL);

        call.enqueue(new Callback<DtoColors>() {
            @Override
            public void onResponse(Call<DtoColors> call, Response<DtoColors> response) {
                processColorsResponse(response);
            }

            @Override
            public void onFailure(Call<DtoColors> call, Throwable t) {
                Timber.e(t, "Search FAILED");
            }
        });
    }

    /**
     * Web service call that saves response to file, then processes the file (best for large JSON payloads)
     */
    //    @OnClick(R.id.rest_test_button)
    public void testSaveQueryWebServiceCall() {
        Call<ResponseBody> call = colorService.colorsToFile();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // delete any existing file
                File outputFile = new File(getExternalCacheDir(), "ws-out.json");
                if (outputFile.exists()) {
                    outputFile.delete();
                }

                // save the response body to file
                webServiceUtil.saveResponseToFile(response, outputFile);

                // show the output of the file
                try {
                    String fileContents = FileUtils.readFileToString(outputFile);
                    Timber.i("Output file: [%s]", fileContents);
                } catch (IOException e) {
                    Timber.e(e, "Error reading file");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Timber.e("Search FAILED");
            }
        });
    }

    private void processColorsResponse(Response<DtoColors> response) {
        if (response.isSuccessful()) {
            Timber.i("Search SUCCESS");
            processColorsResponse(response.body());
        } else {
            Timber.e("Search FAILURE: code (%d)", response.code());
        }
    }

    private void processColorsResponse(DtoColors dtoColors) {
        for (DtoColor dtoColor : dtoColors.getColors()) {
            Timber.i("Result: %s", dtoColor.getColorName());
        }
    }

    /**
     * Sample for creating a scheduled job
     */
    @OnClick(R.id.jobTestButton)
    public void jobTest() {
        SampleJob.schedule();
        SampleJob.schedule();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SampleJob.schedule();
    }

    /**
     * Rx and Table change listener tests
     */
    @OnClick(R.id.rxTestButton)
    public void testRx() {
        // Sample tests for Rx
        // Rx Subscribe
        Disposable disposable = individualManager.tableChanges().subscribe(changeType -> handleRxIndividualTableChange(changeType));

        // Standard Listener
        individualManager.addTableChangeListener(this::handleIndividualTableChange);

        // Make some changes
        String originalName;

        Individual individual = individualManager.findAll().get(0);
        if (individual != null) {
            originalName = individual.getFirstName();
            Timber.i("ORIGINAL NAME = %s", originalName);

            // change name
            individual.setFirstName("Bobby");
            individualManager.save(individual);

            // restore name
            individual.setFirstName(originalName);
            individualManager.save(individual);
        } else {
            Timber.e("Cannot find individual");
        }

        // Unsubscribe
        disposable.dispose();
    }

    public void handleIndividualTableChange(DatabaseTableChange change) {
        if (change.isInsert()) {
            Timber.i("Individual Table Insert");
        } else if (change.isUpdate()) {
            Timber.i("Individual Table Insert");
        } else if (change.isDelete()) {
            Timber.i("Individual Table Delete");
        }
    }

    public void handleRxIndividualTableChange(DatabaseTableChange change) {
        if (change.isInsert()) {
            Timber.i("Rx Individual Table Insert");
        } else if (change.isUpdate()) {
            Timber.i("Rx Individual Table Insert");
        } else if (change.isDelete()) {
            Timber.i("Rx Individual Table Delete");
        }
    }

}
