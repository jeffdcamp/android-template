package org.jdc.template.ui.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;

import org.apache.commons.io.FileUtils;
import org.dbtools.android.domain.DBToolsTableChangeListener;
import org.dbtools.android.domain.DatabaseTableChange;
import org.dbtools.android.domain.database.DatabaseWrapper;
import org.jdc.template.Analytics;
import org.jdc.template.BuildConfig;
import org.jdc.template.R;
import org.jdc.template.event.NewDataEvent;
import org.jdc.template.inject.Injector;
import org.jdc.template.job.SampleJob;
import org.jdc.template.model.database.AppDatabaseConfig;
import org.jdc.template.model.database.DatabaseManager;
import org.jdc.template.model.database.DatabaseManagerConst;
import org.jdc.template.model.database.attached.crossdatabasequery.CrossDatabaseQuery;
import org.jdc.template.model.database.attached.crossdatabasequery.CrossDatabaseQueryManager;
import org.jdc.template.model.database.main.household.Household;
import org.jdc.template.model.database.main.household.HouseholdManager;
import org.jdc.template.model.database.main.individual.Individual;
import org.jdc.template.model.database.main.individual.IndividualConst;
import org.jdc.template.model.database.main.individual.IndividualManager;
import org.jdc.template.model.database.main.individualdata.IndividualData;
import org.jdc.template.model.database.main.individualdata.IndividualDataManager;
import org.jdc.template.model.database.main.individualquery.IndividualQuery;
import org.jdc.template.model.database.main.individualquery.IndividualQueryManager;
import org.jdc.template.model.database.main.individualtype.IndividualType;
import org.jdc.template.model.database.other.individuallist.IndividualList;
import org.jdc.template.model.database.other.individuallist.IndividualListManager;
import org.jdc.template.model.database.other.individuallistitem.IndividualListItem;
import org.jdc.template.model.database.other.individuallistitem.IndividualListItemConst;
import org.jdc.template.model.database.other.individuallistitem.IndividualListItemManager;
import org.jdc.template.model.webservice.colors.ColorService;
import org.jdc.template.model.webservice.colors.dto.DtoColor;
import org.jdc.template.model.webservice.colors.dto.DtoColors;
import org.jdc.template.util.RxUtil;
import org.jdc.template.util.WebServiceUtil;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import pocketbus.Bus;
import pocketbus.Subscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class AboutActivity extends BaseActivity {
    @BindView(R.id.version_info)
    TextView versionTextView;
    @BindView(R.id.ab_toolbar)
    Toolbar toolbar;

    @Inject
    Analytics analytics;
    @Inject
    Bus bus;

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
        enableActionBarBackArrow(true);

        versionTextView.setText(getVersionName());
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

    private String getVersionName() {
        String versionString = BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")";
        versionString += "\n" + DateUtils.formatDateTime(this, BuildConfig.BUILD_TIME, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_YEAR);

        return versionString;
    }

    @OnClick(R.id.create_database_button)
    public void onCreateDatabaseButtonClick() {
        createSampleData();
    }

    private boolean useInjection = true;

    private void createSampleData() {
        if (useInjection) {
            createSampleDataWithInjection();
        } else {
            createSampleDataNoInjection();
        }
    }

    @Inject
    DatabaseManager databaseManager;

    @Inject
    IndividualManager individualManager;

    @Inject
    HouseholdManager householdManager;

    @Inject
    IndividualListManager individualListManager;

    @Inject
    IndividualListItemManager individualListItemManager;

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
        individual1.setHouseholdId(household.getId());
        individual1.setBirthDate(LocalDate.of(1970, 1, 1));
        individual1.setAlarmTime(LocalTime.of(7, 0));
        individualManager.save(individual1);

        Individual individual2 = new Individual();
        individual2.setFirstName("John");
        individual2.setLastName("Miller");
        individual2.setPhone("303-555-1111");
        individual2.setIndividualType(IndividualType.CHILD);
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


    private void createSampleDataNoInjection() {
        DatabaseManager noInjectionDatabaseManager = new DatabaseManager(new AppDatabaseConfig(getApplication()));

        HouseholdManager householdManager = new HouseholdManager(noInjectionDatabaseManager);
        IndividualManager individualManager = new IndividualManager(noInjectionDatabaseManager);
        IndividualListManager individualListManager = new IndividualListManager(noInjectionDatabaseManager);
        IndividualListItemManager individualListItemManager = new IndividualListItemManager(noInjectionDatabaseManager);

        // Main Database
        DatabaseWrapper db = noInjectionDatabaseManager.getWritableDatabase(DatabaseManagerConst.MAIN_DATABASE_NAME);
        noInjectionDatabaseManager.beginTransaction(DatabaseManagerConst.MAIN_DATABASE_NAME);

        Household household = new Household();
        household.setName("Campbell");
        householdManager.save(db, household);

        Individual individual1 = new Individual();
        individual1.setFirstName("Jeff");
        individual1.setLastName("Campbell");
        individual1.setPhone("000-555-1234");
        individual1.setIndividualType(IndividualType.HEAD);
        individual1.setHouseholdId(household.getId());
        individual1.setAmount1(19.95F);
        individual1.setAmount2(1000000000.25D);
        individual1.setEnabled(true);
        individualManager.save(db, individual1);

        Individual individual2 = new Individual();
        individual2.setFirstName("Tanner");
        individual2.setLastName("Campbell");
        individual2.setIndividualType(IndividualType.CHILD);
        individual2.setHouseholdId(household.getId());
        individual2.setAmount1(21.95F);
        individual2.setAmount2(2000000000.25D);
        individual2.setEnabled(false);
        individualManager.save(db, individual2);
        noInjectionDatabaseManager.endTransaction(DatabaseManagerConst.MAIN_DATABASE_NAME, true);


        // Other Database
        noInjectionDatabaseManager.beginTransaction(DatabaseManagerConst.OTHER_DATABASE_NAME);

        DatabaseWrapper otherDb = noInjectionDatabaseManager.getWritableDatabase(DatabaseManagerConst.MAIN_DATABASE_NAME);
        IndividualList newList = new IndividualList();
        newList.setName("My List");
        individualListManager.save(otherDb, newList);

        IndividualListItem newListItem = new IndividualListItem();
        newListItem.setListId(newList.getId());
        newListItem.setIndividualId(individual1.getId());
        individualListItemManager.save(otherDb, newListItem);

        noInjectionDatabaseManager.endTransaction(DatabaseManagerConst.OTHER_DATABASE_NAME, true);

        Toast.makeText(this, "Database created", Toast.LENGTH_SHORT).show();
    }

    public static final String ATTACH_DATABASE_QUERY = "SELECT " + IndividualConst.C_FIRST_NAME +
            " FROM " + IndividualConst.TABLE +
            " JOIN " + IndividualListItemConst.TABLE + " ON " + IndividualConst.FULL_C_ID + " = " + IndividualListItemConst.FULL_C_INDIVIDUAL_ID;

    private void testDatabaseWithInjection() {
        // (Optional) attach databases on demand (instead of in the DatabaseManager)
//        databaseManager.identifyDatabases(); // NOSONAR
//        databaseManager.addAttachedDatabase(DatabaseManager.ATTACH_DATABASE_NAME, DatabaseManager.MAIN_DATABASE_NAME, Arrays.asList(DatabaseManager.OTHER_DATABASE_NAME)); // NOSONAR

        List<String> names = findAllStringByRawQuery(databaseManager, DatabaseManagerConst.ATTACHED_DATABASE_NAME, ATTACH_DATABASE_QUERY, null);
        for (String name : names) {
            Timber.i("Attached Database Item Name: %s", name);
        }
    }

    public List<String> findAllStringByRawQuery(DatabaseManager dbManager, String databaseName, String rawQuery, String[] selectionArgs) {
        List<String> foundItems;

        Cursor cursor = dbManager.getWritableDatabase(databaseName).rawQuery(rawQuery, selectionArgs);
        if (cursor != null) {
            foundItems = new ArrayList<>(cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    foundItems.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            foundItems = new ArrayList<>();
        }

        return foundItems;
    }

    @Inject
    IndividualQueryManager individualQueryManager;

    @Inject
    CrossDatabaseQueryManager crossDatabaseQueryManager;

    //    @OnClick(R.id.test_button)
    public void testQuery() {
        // OBJECTS
//        List<IndividualQuery> items = individualQueryManager.findAllByRawQuery(IndividualQuery.QUERY_RAW, new String[]{"Buddy"});
        List<IndividualQuery> items = individualQueryManager.findAll();
        Timber.i("List Count: %d", items.size());

        // show results
        for (IndividualQuery item : items) {
            Timber.i("Item Name: %s", item.getName());
        }

        // CURSORS
        Cursor cursor = individualQueryManager.findCursorAll();

        // create new item
        IndividualQuery newInd = new IndividualQuery();
        newInd.setName("bubba");

        // add item to cursor
        Cursor newCursor = individualQueryManager.addAllToCursorTop(cursor, newInd, newInd);
        Timber.i("Count: %d", newCursor.getCount());

        // show results
        if (newCursor.moveToFirst()) {
            do {
                IndividualQuery cursorIndividual = new IndividualQuery(newCursor);
                Timber.i("Cursor Individual: %s", cursorIndividual.getName());
            } while (newCursor.moveToNext());
        }
        newCursor.close();

    }

    //    @OnClick(R.id.test)
    public void test2() {
        Timber.i("Cross database");
        long s = System.currentTimeMillis();
        Cursor allCrossCursor = crossDatabaseQueryManager.findCursorAll();
        Timber.i("Cross db query time: %d", (System.currentTimeMillis() - s));
        if (allCrossCursor != null) {
            if (allCrossCursor.moveToFirst()) {
                do {
                    CrossDatabaseQuery obj = new CrossDatabaseQuery(allCrossCursor);
                    Timber.i("Cursor Individual: %s", obj.getName());
                } while (allCrossCursor.moveToNext());
            }
            allCrossCursor.close();
        }

        Timber.i("Cross db query time FINISH: %d", (System.currentTimeMillis() - s));
    }

    @Inject
    ColorService colorService;

    @Inject
    WebServiceUtil webServiceUtil;

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

    @OnClick(R.id.rest_test_button)
    public void testQueryWebServiceCallRx() {
        RxUtil.toRetrofitObservable(colorService.colors())
                .subscribeOn(Schedulers.io())
                .map(response -> RxUtil.verifyRetrofitResponse(response))
                .filter(dtoSearchResponse -> dtoSearchResponse != null) // don't continue if null
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dtoColors -> processColorsResponse(dtoColors), throwable -> bus.post(new NewDataEvent(false, throwable)), () -> bus.post(new NewDataEvent(true)));
    }

    @Subscribe
    public void handle(NewDataEvent event) {
        Timber.i(event.getThrowable(), "Rest Service finished [%b]", event.isSuccess());
    }

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

    @Inject
    IndividualDataManager individualDataManager;

    @OnClick(R.id.test_button)
    public void testNoPrimaryKeyAndUnique() {
        IndividualData data = new IndividualData();
        data.setExternalId(555);
        data.setExternalId(1);
        data.setName("Foo");
        individualDataManager.save(data);

        Timber.i("findAll count (should always be 1): %d", individualDataManager.findAll().size());
    }

    @OnClick(R.id.job_test_button)
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

    @OnClick(R.id.rx_test_button)
    public void testRx() {
        // Sample tests for Rx
        // RxTest.testConcurrentPeople();

        // Rx Subscribe
        Subscription tableChangeSubscription = individualManager.tableChanges()
                .subscribe(changeType -> handleRxIndividualTableChange(changeType));

        // Standard Listener
        individualManager.addTableChangeListener(new DBToolsTableChangeListener() {
            @Override
            public void onTableChange(DatabaseTableChange tableChange) {
                handleIndividualTableChange(tableChange);
            }
        });

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
        tableChangeSubscription.unsubscribe();
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
