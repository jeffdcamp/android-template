package org.company.project.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import org.company.project.MyApplication;
import org.company.project.R;
import org.company.project.menu.CommonMenu;

import javax.inject.Inject;

public class MainActivity extends DrawerActivity {
//    public static final String TAG = MyApplication.createTag(MainActivity.class);

    @Inject
    public CommonMenu commonMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MyApplication.injectActivity(this);

        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.nav_main);

        setupDrawer(true, R.string.nav_main, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return commonMenu.onOptionsItemSelected(this, item) || super.onOptionsItemSelected(item);
    }
}
