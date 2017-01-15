package org.jdc.template.ui.menu;

import android.content.Context;
import android.view.MenuItem;

import org.jdc.template.InternalIntents;
import org.jdc.template.R;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class CommonMenu {
    private final InternalIntents internalIntents;

    @Inject
    public CommonMenu(InternalIntents internalIntents) {
        this.internalIntents = internalIntents;
    }

    public boolean onOptionsItemSelected(Context context, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_search:
                return true;
            case R.id.menu_item_settings:
                internalIntents.showSettings(context);
                return true;
            case R.id.menu_item_about:
                internalIntents.showHelp(context);
                return true;
            default:
                Timber.i("Unknown common menu item id: %d, ignoring", item.getItemId());
                return false;
        }
    }
}
