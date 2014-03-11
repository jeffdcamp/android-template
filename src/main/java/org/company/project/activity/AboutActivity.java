package org.company.project.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

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

    @Inject
    public AboutActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        ButterKnife.inject(this);
        MyApplication.injectActivity(this);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        versionTextView.setText(BuildConfig.VERSION_NAME);
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

    boolean useInjection = true;

    @OnClick(R.id.button1)
    public void onCreateDatabaseButtonClick() {
        createSampleDataWithInjection();
    }

    @OnClick(R.id.button2)
    public void onTestDatabaseButtonClick() {
        testDatabase();
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

    private void createSameleData() {
        if (useInjection) {
            createSampleDataWithInjection();
        } else {
            createSampleDataNoInjection();
        }
    }

    private void testDatabase() {
        if (useInjection) {
            testDatabaseWithInjection();
        } else {
            testDatabaseNoInjection();
        }
    }

    private void createSampleDataWithInjection() {
        // MAIN Database
        householdManager.beginTransaction();

        Household household = new Household();
        household.setName("Campbell");
        householdManager.save(household);

        Individual individual1 = new Individual();
        individual1.setFirstName("Jeff");
        individual1.setLastName("Campbell");
        individual1.setIndividualType(IndividualType.HEAD);
        individual1.setHouseholdId(household.getId());
        individualManager.save(individual1);

        Individual individual2 = new Individual();
        individual2.setFirstName("Tanner");
        individual2.setLastName("Campbell");
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
        noInjectionDatabaseManager.setContext(this);

        // Main Database
        SQLiteDatabase db = noInjectionDatabaseManager.getWritableDatabase(DatabaseManager.MAIN_DATABASE_NAME);
        noInjectionDatabaseManager.beginTransaction(DatabaseManager.MAIN_DATABASE_NAME);

        Household household = new Household();
        household.setName("Campbell");
        HouseholdManager.save(db, household);

        Individual individual1 = new Individual();
        individual1.setFirstName("Jeff");
        individual1.setLastName("Campbell");
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
    }

    public static final String ATTACH_DATABASE_QUERY = "SELECT " + Individual.C_FIRST_NAME +
            " FROM " + Individual.TABLE +
            " JOIN " + IndividualListItem.TABLE + " ON " + Individual.FULL_C_ID + " = " + IndividualListItem.FULL_C_INDIVIDUAL_ID;

    private void testDatabaseWithInjection() {
        // (Optional) attach databases on demand (instead of in the DatabaseManager)
//        databaseManager.identifyDatabases();
//        databaseManager.addAttachedDatabase(DatabaseManager.ATTACH_DATABASE_NAME, DatabaseManager.MAIN_DATABASE_NAME, Arrays.asList(DatabaseManager.OTHER_DATABASE_NAME));

        List<String> names = findAllStringByRawQuery(databaseManager, DatabaseManager.ATTACH_DATABASE_NAME, ATTACH_DATABASE_QUERY, null);
        for (String name : names) {
            Log.i(TAG, "Attached Database Item Name: " + name);
        }
    }

    private void testDatabaseNoInjection() {
        noInjectionDatabaseManager.setContext(this);

        // (Optional) attach databases on demand (instead of in the DatabaseManager)
//        noInjectionDatabaseManager.identifyDatabases();
//        noInjectionDatabaseManager.addAttachedDatabase(DatabaseManager.ATTACH_DATABASE_NAME, DatabaseManager.MAIN_DATABASE_NAME, Arrays.asList(DatabaseManager.OTHER_DATABASE_NAME));

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
