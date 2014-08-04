package org.company.project.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.company.project.BuildConfig;
import org.company.project.MyApplication;
import org.company.project.R;
import org.company.project.domain.DatabaseManager;
import org.company.project.domain.household.Household;
import org.company.project.domain.household.HouseholdManager;
import org.company.project.domain.individual.Individual;
import org.company.project.domain.individual.IndividualManager;
import org.company.project.domain.individuallist.IndividualList;
import org.company.project.domain.individuallist.IndividualListManager;
import org.company.project.domain.individuallistitem.IndividualListItem;
import org.company.project.domain.individuallistitem.IndividualListItemManager;
import org.company.project.domain.individualtype.IndividualType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author jcampbell
 */
public class AboutActivity extends ActionBarActivity {
    public static final String TAG = MyApplication.createTag(AboutActivity.class);

    @InjectView(R.id.version_info)
    TextView versionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        ButterKnife.inject(this);
        MyApplication.injectActivity(this);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        versionTextView.setText(getVersionName());
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
        String buildTimeText;
        try {
            // Parse ISO8601-format time into local time.
            DateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
            DateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            inFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date buildTime = inFormat.parse(BuildConfig.BUILD_TIME);
            buildTimeText = outFormat.format(buildTime);
        } catch (ParseException e) {
            throw new IllegalStateException("Unable to decode build time: " + BuildConfig.BUILD_TIME, e);
        }

        String versionString = BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")";
        versionString += "\n" + buildTimeText;

        return versionString;
    }

    @OnClick(R.id.button1)
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
        noInjectionDatabaseManager.setContext(getApplication());

        // Main Database
        SQLiteDatabase db = noInjectionDatabaseManager.getWritableDatabase(DatabaseManager.MAIN_DATABASE_NAME);
        noInjectionDatabaseManager.beginTransaction(DatabaseManager.MAIN_DATABASE_NAME);

        Household household = new Household();
        household.setName("Campbell");
        HouseholdManager.save(db, household);

        Individual individual1 = new Individual();
        individual1.setFirstName("Jeff");
        individual1.setLastName("Campbell");
        individual1.setPhone("000-555-1234");
        individual1.setIndividualType(IndividualType.HEAD);
        individual1.setHouseholdId(household.getId());
        IndividualManager.save(db, individual1);

        Individual individual2 = new Individual();
        individual2.setFirstName("Tanner");
        individual2.setLastName("Campbell");
        individual2.setIndividualType(IndividualType.CHILD);
        individual2.setHouseholdId(household.getId());
        IndividualManager.save(db, individual2);
        noInjectionDatabaseManager.endTransaction(DatabaseManager.MAIN_DATABASE_NAME, true);


        // Other Database
        noInjectionDatabaseManager.beginTransaction(DatabaseManager.OTHER_DATABASE_NAME);

        SQLiteDatabase otherDb = noInjectionDatabaseManager.getWritableDatabase(DatabaseManager.MAIN_DATABASE_NAME);
        IndividualList newList = new IndividualList();
        newList.setName("My List");
        IndividualListManager.save(otherDb, newList);

        IndividualListItem newListItem = new IndividualListItem();
        newListItem.setListId(newList.getId());
        newListItem.setIndividualId(individual1.getId());
        IndividualListItemManager.save(otherDb, newListItem);

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

        List<String> names = findAllStringByRawQuery(databaseManager, DatabaseManager.ATTACH_DATABASE_NAME, ATTACH_DATABASE_QUERY, null);
        for (String name : names) {
            Log.i(TAG, "Attached Database Item Name: " + name);
        }
    }

    private void testDatabaseNoInjection() {
        noInjectionDatabaseManager.setContext(getApplication());

        // (Optional) attach databases on demand (instead of in the DatabaseManager)
//        noInjectionDatabaseManager.identifyDatabases(); // NOSONAR
//        noInjectionDatabaseManager.addAttachedDatabase(DatabaseManager.ATTACH_DATABASE_NAME, DatabaseManager.MAIN_DATABASE_NAME, Arrays.asList(DatabaseManager.OTHER_DATABASE_NAME)); // NOSONAR

        List<String> names = findAllStringByRawQuery(noInjectionDatabaseManager, DatabaseManager.ATTACH_DATABASE_NAME, ATTACH_DATABASE_QUERY, null);
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
}
