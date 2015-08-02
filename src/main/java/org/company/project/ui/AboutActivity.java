package org.company.project.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.company.project.Analytics;
import org.company.project.App;
import org.company.project.BuildConfig;
import org.company.project.R;
import org.company.project.domain.DatabaseManager;
import org.company.project.domain.attached.crossdatabasequery.CrossDatabaseQuery;
import org.company.project.domain.attached.crossdatabasequery.CrossDatabaseQueryManager;
import org.company.project.domain.main.household.Household;
import org.company.project.domain.main.household.HouseholdManager;
import org.company.project.domain.main.individual.Individual;
import org.company.project.domain.main.individual.IndividualManager;
import org.company.project.domain.main.individualquery.IndividualQuery;
import org.company.project.domain.main.individualquery.IndividualQueryManager;
import org.company.project.domain.main.individualtype.IndividualType;
import org.company.project.domain.other.individuallist.IndividualList;
import org.company.project.domain.other.individuallist.IndividualListManager;
import org.company.project.domain.other.individuallistitem.IndividualListItem;
import org.company.project.domain.other.individuallistitem.IndividualListItemManager;
import org.company.project.webservice.websearch.DtoResult;
import org.company.project.webservice.websearch.DtoSearchResponse;
import org.company.project.webservice.websearch.WebSearchService;
import org.dbtools.android.domain.database.DatabaseWrapper;
import org.dbtools.android.domain.event.DatabaseChangeEvent;
import org.dbtools.android.domain.event.DatabaseEndTransactionEvent;
import org.dbtools.android.domain.event.DatabaseInsertEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author jcampbell
 */
public class AboutActivity extends AppCompatActivity {
    public static final String TAG = App.createTag(AboutActivity.class);

    @Bind(R.id.version_info)
    TextView versionTextView;

    @Bind(R.id.ab_toolbar)
    Toolbar toolbar;

    @Inject
    Bus bus;

    @Inject
    Analytics analytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        ButterKnife.bind(this);
        App.getInjectComponent(this).inject(this);

        analytics.send(new HitBuilders.EventBuilder()
                .setCategory(Analytics.CATEGORY_ABOUT)
                .build());

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        versionTextView.setText(getVersionName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
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

        Bus bus = new Bus();
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

    @OnClick(R.id.test_button)
    public void testQueryWebServiceCall() {
        webSearchService.search("Cat", new Callback<DtoSearchResponse>() {
            @Override
            public void success(DtoSearchResponse dtoSearchResponse, Response response) {
                Log.e(TAG, "Search SUCCESS");
                for (DtoResult dtoResult : dtoSearchResponse.getResponseData().getResults()) {
                    Log.i(TAG, "Result: " + dtoResult.getTitle());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Search FAILED");
            }
        });
    }

    @Subscribe
    public void onInsert(DatabaseInsertEvent event) {
        Log.e(TAG, "Item inserted on table " + event.getTableName());
        Log.e(TAG, "NewId " + event.getNewId());
    }

    @Subscribe
    public void onDatabaseChanged(DatabaseChangeEvent event) {
        Log.e(TAG, "Database changed on table " + event.getTableName());
    }

    @Subscribe
    public void onDatabaseChangedTransaction(DatabaseEndTransactionEvent event) {
        Log.e(TAG, "Database changed transaction end.  Tables changed: " + event.getAllTableName());
        boolean myTableUpdated = event.containsTable(Individual.TABLE);

    }
}
