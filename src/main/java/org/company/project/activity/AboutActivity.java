package org.company.project.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
import org.company.project.domain.individualtype.IndividualType;

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

    @OnClick(R.id.button)
    public void onCreateDatabaseButtonClick() {
        createSampleDataNoInjection();
    }

    @Inject
    IndividualManager individualManager;

    @Inject
    HouseholdManager householdManager;

    private void createSampleDataWithInjection() {
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

    }

    private void createSampleDataNoInjection() {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.setContext(this);

        SQLiteDatabase db = databaseManager.getWritableDatabase(DatabaseManager.MAIN_DATABASE_NAME);
        databaseManager.beginTransaction(DatabaseManager.MAIN_DATABASE_NAME);

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

        databaseManager.endTransaction(DatabaseManager.MAIN_DATABASE_NAME, true);
    }
}
