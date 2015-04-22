package org.company.project.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.company.project.InternalIntents;
import org.company.project.R;

import javax.inject.Inject;

public abstract class DrawerActivity extends AppCompatActivity {

    @Inject
    InternalIntents internalIntents;

    public enum Item {
        MAIN, LIBRARY, STORE, SETTINGS, HELP;

        public static Item getItem(IDrawerItem drawerItem) {
            return values()[drawerItem.getIdentifier()];
        }
    }

    public Drawer.Result setupDrawerWithBackButton(ActionBar actionBar) {
        Drawer.Result result = setupDrawerWithDrawerButton(null);

        // set the back arrow in the toolbar
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        return result;
    }

    public Drawer.Result setupDrawerWithDrawerButton(Toolbar toolbar) {
        return new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_main).withIdentifier(Item.MAIN.ordinal()),
                        new PrimaryDrawerItem().withName(R.string.drawer_my_library).withIdentifier(Item.LIBRARY.ordinal()),
                        new PrimaryDrawerItem().withName(R.string.drawer_store).withIdentifier(Item.STORE.ordinal()),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_settings).withCheckable(false).withIdentifier(Item.SETTINGS.ordinal()),
                        new SecondaryDrawerItem().withName(R.string.drawer_help).withIdentifier(Item.HELP.ordinal())
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        onDrawerItemClicked(parent, view, position, id, drawerItem);
                    }
                })
                .build();
    }

    public void onDrawerItemClicked(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
        Item selectedItem = Item.getItem(drawerItem);
        switch (selectedItem) {
            case MAIN:
                break;
            case LIBRARY:
                break;
            case STORE:
                break;
            case SETTINGS:
                internalIntents.showSettings(this);
                break;
            case HELP:
                internalIntents.showHelp(this);
                break;
        }
    }
}
