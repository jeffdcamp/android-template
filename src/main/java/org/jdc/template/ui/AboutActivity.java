package org.jdc.template.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.squareup.okhttp.ResponseBody;

import org.apache.commons.io.FileUtils;
import org.dbtools.android.domain.database.DatabaseWrapper;
import org.dbtools.android.domain.event.DatabaseChangeEvent;
import org.dbtools.android.domain.event.DatabaseChangeType;
import org.dbtools.android.domain.event.DatabaseEndTransactionEvent;
import org.dbtools.android.domain.event.DatabaseInsertEvent;
import org.dbtools.android.domain.event.DatabaseRowChange;
import org.dbtools.android.domain.event.GreenRobotEventBus;
import org.jdc.template.Analytics;
import org.jdc.template.App;
import org.jdc.template.BuildConfig;
import org.jdc.template.R;
import org.jdc.template.dagger.Injector;
import org.jdc.template.domain.DatabaseManager;
import org.jdc.template.domain.attached.crossdatabasequery.CrossDatabaseQuery;
import org.jdc.template.domain.attached.crossdatabasequery.CrossDatabaseQueryManager;
import org.jdc.template.domain.main.household.Household;
import org.jdc.template.domain.main.household.HouseholdManager;
import org.jdc.template.domain.main.individual.Individual;
import org.jdc.template.domain.main.individual.IndividualManager;
import org.jdc.template.domain.main.individualquery.IndividualQuery;
import org.jdc.template.domain.main.individualquery.IndividualQueryManager;
import org.jdc.template.domain.main.individualtype.IndividualType;
import org.jdc.template.domain.other.individuallist.IndividualList;
import org.jdc.template.domain.other.individuallist.IndividualListManager;
import org.jdc.template.domain.other.individuallistitem.IndividualListItem;
import org.jdc.template.domain.other.individuallistitem.IndividualListItemManager;
import org.jdc.template.util.RxUtil;
import org.jdc.template.util.WebServiceUtil;
import org.jdc.template.webservice.websearch.DtoResult;
import org.jdc.template.webservice.websearch.DtoSearchResponse;
import org.jdc.template.webservice.websearch.WebSearchService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AboutActivity extends BaseActivity {
    public static final String TAG = App.createTag(AboutActivity.class);

    @Bind(R.id.version_info)
    TextView versionTextView;

    @Bind(R.id.ab_toolbar)
    Toolbar toolbar;

    @Inject
    EventBus bus;

    @Inject
    Analytics analytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        Injector.get().inject(this);

        analytics.send(new HitBuilders.EventBuilder()
                .setCategory(Analytics.CATEGORY_ABOUT)
                .build());

        setSupportActionBar(toolbar);
        enableActionBarBackArrow(true);

        versionTextView.setText(getVersionName());
    }

    @Override
    public boolean registerEventBus() {
        return true;
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
        individualManager.save(individual1);

        Individual individual2 = new Individual();
        individual2.setFirstName("John");
        individual2.setLastName("Miller");
        individual2.setPhone("303-555-1111");
        individual2.setIndividualType(IndividualType.CHILD);
        individual2.setHouseholdId(household.getId());
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

    DatabaseManager noInjectionDatabaseManager = new DatabaseManager();

    private void createSampleDataNoInjection() {
        HouseholdManager householdManager = new HouseholdManager();
        IndividualManager individualManager = new IndividualManager();
        IndividualListManager individualListManager = new IndividualListManager();
        IndividualListItemManager individualListItemManager = new IndividualListItemManager();

        org.dbtools.android.domain.DBToolsEventBus bus = new GreenRobotEventBus(EventBus.getDefault());
        householdManager.setBus(bus);
        individualManager.setBus(bus);
        individualListManager.setBus(bus);
        individualListItemManager.setBus(bus);

        noInjectionDatabaseManager.setContext(getApplication());

        // Main Database
        DatabaseWrapper db = noInjectionDatabaseManager.getWritableDatabase(DatabaseManager.MAIN_DATABASE_NAME);
        noInjectionDatabaseManager.beginTransaction(DatabaseManager.MAIN_DATABASE_NAME);

        Household household = new Household();
        household.setName("Campbell");
        householdManager.save(db, household);

        Individual individual1 = new Individual();
        individual1.setFirstName("Jeff");
        individual1.setLastName("Campbell");
        individual1.setPhone("000-555-1234");
        individual1.setIndividualType(IndividualType.HEAD);
        individual1.setHouseholdId(household.getId());
        individualManager.save(db, individual1);

        Individual individual2 = new Individual();
        individual2.setFirstName("Tanner");
        individual2.setLastName("Campbell");
        individual2.setIndividualType(IndividualType.CHILD);
        individual2.setHouseholdId(household.getId());
        individualManager.save(db, individual2);
        noInjectionDatabaseManager.endTransaction(DatabaseManager.MAIN_DATABASE_NAME, true);


        // Other Database
        noInjectionDatabaseManager.beginTransaction(DatabaseManager.OTHER_DATABASE_NAME);

        DatabaseWrapper otherDb = noInjectionDatabaseManager.getWritableDatabase(DatabaseManager.MAIN_DATABASE_NAME);
        IndividualList newList = new IndividualList();
        newList.setName("My List");
        individualListManager.save(otherDb, newList);

        IndividualListItem newListItem = new IndividualListItem();
        newListItem.setListId(newList.getId());
        newListItem.setIndividualId(individual1.getId());
        individualListItemManager.save(otherDb, newListItem);

        noInjectionDatabaseManager.endTransaction(DatabaseManager.OTHER_DATABASE_NAME, true);

        Toast.makeText(this, "Database created", Toast.LENGTH_SHORT).show();
    }

    public static final String ATTACH_DATABASE_QUERY = "SELECT " + Individual.C_FIRST_NAME +
            " FROM " + Individual.TABLE +
            " JOIN " + IndividualListItem.TABLE + " ON " + Individual.FULL_C_ID + " = " + IndividualListItem.FULL_C_INDIVIDUAL_ID;

    private void testDatabaseWithInjection() {
        // (Optional) attach databases on demand (instead of in the DatabaseManager)
//        databaseManager.identifyDatabases(); // NOSONAR
//        databaseManager.addAttachedDatabase(DatabaseManager.ATTACH_DATABASE_NAME, DatabaseManager.MAIN_DATABASE_NAME, Arrays.asList(DatabaseManager.OTHER_DATABASE_NAME)); // NOSONAR

        List<String> names = findAllStringByRawQuery(databaseManager, DatabaseManager.ATTACHED_DATABASE_NAME, ATTACH_DATABASE_QUERY, null);
        for (String name : names) {
            Log.i(TAG, "Attached Database Item Name: " + name);
        }
    }

    private void testDatabaseNoInjection() {
        noInjectionDatabaseManager.setContext(getApplication());

        // (Optional) attach databases on demand (instead of in the DatabaseManager)
//        noInjectionDatabaseManager.identifyDatabases(); // NOSONAR
//        noInjectionDatabaseManager.addAttachedDatabase(DatabaseManager.ATTACH_DATABASE_NAME, DatabaseManager.MAIN_DATABASE_NAME, Arrays.asList(DatabaseManager.OTHER_DATABASE_NAME)); // NOSONAR

        List<String> names = findAllStringByRawQuery(noInjectionDatabaseManager, DatabaseManager.ATTACHED_DATABASE_NAME, ATTACH_DATABASE_QUERY, null);
        for (String name : names) {
            Log.i(TAG, "Attached Database Item Name: " + name);
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
        Log.e(TAG, "List Count: " + items.size());

        // show results
        for (IndividualQuery item : items) {
            Log.e(TAG, "Item Name: " + item.getName());
        }

        // CURSORS
        Cursor cursor = individualQueryManager.findCursorAll();

        // create new item
        IndividualQuery newInd = new IndividualQuery();
        newInd.setName("bubba");

        // add item to cursor
        Cursor newCursor = individualQueryManager.addAllToCursorTop(cursor, newInd, newInd);
        Log.e(TAG, "Count: " + newCursor.getCount());

        // show results
        if (newCursor.moveToFirst()) {
            do {
                IndividualQuery cursorIndividual = new IndividualQuery(newCursor);
                Log.e(TAG, "Cursor Individual: " + cursorIndividual.getName());
            } while (newCursor.moveToNext());
        }
        newCursor.close();

    }

    //    @OnClick(R.id.test)
    public void test2() {
        Log.e(TAG, "Cross database");
        long s = System.currentTimeMillis();
        Cursor allCrossCursor = crossDatabaseQueryManager.findCursorAll();
        Log.e(TAG, "Cross db query time: " + (System.currentTimeMillis() - s));
        if (allCrossCursor != null) {
//            Log.e(TAG, "Cross Count: " + allCrossCursor.getCount());
            if (allCrossCursor.moveToFirst()) {
                do {
                    CrossDatabaseQuery obj = new CrossDatabaseQuery(allCrossCursor);
                    Log.e(TAG, "Cursor Individual: " + obj.getName());
                } while (allCrossCursor.moveToNext());
            }
            allCrossCursor.close();
        }

        Log.e(TAG, "Cross db query time FINISH: " + (System.currentTimeMillis() - s));
    }

    @Inject
    WebSearchService webSearchService;

    @Inject
    WebServiceUtil webServiceUtil;

//    @OnClick(R.id.rest_test_button)
    public void testQueryWebServiceCall() {
        Call<DtoSearchResponse> call = webSearchService.search("Cat");

        call.enqueue(new Callback<DtoSearchResponse>() {
            @Override
            public void onResponse(Response<DtoSearchResponse> response, Retrofit retrofit) {
                processWebServiceResponse(response);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Search FAILED", t);
            }
        });
    }

    @OnClick(R.id.rest_test_button)
    public void testQueryWebServiceCallRx() {
        RxUtil.toRetrofitObservable(webSearchService.search("Cat"))
                .subscribeOn(Schedulers.io())
                .map(response -> RxUtil.verifyRetrofitResponse(response))
                .filter(dtoSearchResponse -> dtoSearchResponse != null) // don't continue if null
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dtoSearchResponse -> processSearchResponse(dtoSearchResponse), throwable -> Log.e(TAG, "Failed to get results", throwable));
    }

//    @OnClick(R.id.rest_test_button)
    public void testFullUrlQueryWebServiceCall() {
        Call<DtoSearchResponse> call = webSearchService.searchByFullUrl("https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=Cat");

        call.enqueue(new Callback<DtoSearchResponse>() {
            @Override
            public void onResponse(Response<DtoSearchResponse> response, Retrofit retrofit) {
                processWebServiceResponse(response);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Search FAILED", t);
            }
        });
    }

//    @OnClick(R.id.rest_test_button)
    public void testSaveQueryWebServiceCall() {
        Call<ResponseBody> call = webSearchService.searchToFile("Cat");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
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
                    Log.i(TAG, "Output file: [" + fileContents + "]");
                } catch (IOException e) {
                    Log.e(TAG, "Error reading file", e);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Search FAILED");
            }
        });
    }

    private void processWebServiceResponse(Response<DtoSearchResponse> response) {
        if (response.isSuccess()) {
            Log.e(TAG, "Search SUCCESS");
            processSearchResponse(response.body());
        } else {
            Log.e(TAG, "Search FAILURE: code (" + response.code() + ")");
        }
    }

    private void processSearchResponse(DtoSearchResponse searchResponse) {
        for (DtoResult dtoResult : searchResponse.getResponseData().getResults()) {
            Log.i(TAG, "Result: " + dtoResult.getTitle());
        }
    }

    @Subscribe
    public void handle(DatabaseInsertEvent event) {
        Log.e(TAG, "Item inserted on table " + event.getTableName());
        Log.e(TAG, "NewId " + event.getNewId());
    }

    @Subscribe
    public void handle(DatabaseChangeEvent event) {
        Log.e(TAG, "Database changed on table " + event.getTableName());
    }

    @Subscribe
    public void handle(DatabaseEndTransactionEvent event) {
        Log.e(TAG, "Database changed transaction end.  Tables changed: " + event.getAllTableName());
        boolean myTableUpdated = event.containsTable(Individual.TABLE);
        Log.e(TAG, "Individual table updated: " + myTableUpdated);
    }

    @OnClick(R.id.rx_test_button)
    public void testRx() {
        // Sample tests for Rx
        // RxTest.testConcurrentPeople();

        // Subscribe
        Subscription tableChangeSubscription = individualManager.tableChanges()
                .subscribe(changeType -> handleRxIndividualTableChange(changeType));

        Subscription rowChangeSubscription = individualManager.rowChanges()
                .subscribe(databaseRowChange -> handleRxDatabaseRowChange(databaseRowChange));


        // Make some changes
        String originalName;

        Individual individual = individualManager.findAll().get(0);
        if (individual != null) {
            originalName = individual.getFirstName();
            Log.e(TAG, "ORIGINAL NAME = " + originalName);

            // change name
            individual.setFirstName("Bobby");
            individualManager.save(individual);

            // restore name
            individual.setFirstName(originalName);
            individualManager.save(individual);
        } else {
            Log.e(TAG, "Cannot find individual");
        }

        // Unsubscribe
        tableChangeSubscription.unsubscribe();
        rowChangeSubscription.unsubscribe();
    }

    public void handleRxIndividualTableChange(DatabaseChangeType changeType) {
        Log.e(TAG, "Individual Table Changed [" + changeType + "]");
    }

    public void handleRxDatabaseRowChange(DatabaseRowChange change) {
        Individual individual = individualManager.findByRowId(change.getRowId());
        if (individual != null) {
            Log.e(TAG, "DATABASE CHANGE: NAME = " + individual.getFirstName());
        } else {
            Log.e(TAG, "Cannot find individual for row change");
        }
    }
}
