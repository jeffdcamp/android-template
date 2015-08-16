package org.jdc.template;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.jdc.template.ui.AboutActivity;
import org.jdc.template.ui.IndividualActivity;
import org.jdc.template.ui.IndividualEditActivity;
import org.jdc.template.ui.SettingsActivity;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class InternalIntents {

    @Inject
    public InternalIntents() {
    }

    public void showIndividual(@Nonnull Activity activity, long individualId) {
        Intent intent = new Intent(activity, IndividualActivity.class);
        intent.putExtra(IndividualActivity.EXTRA_ID, individualId);
        activity.startActivity(intent);
    }

    public void newIndividual(@Nonnull Activity activity) {
        Intent intent = new Intent(activity, IndividualEditActivity.class);
        intent.putExtra(IndividualEditActivity.EXTRA_ID, -1);
        activity.startActivity(intent);
    }

    public void editIndividual(@Nonnull Activity activity, long individualId) {
        Intent intent = new Intent(activity, IndividualEditActivity.class);
        intent.putExtra(IndividualEditActivity.EXTRA_ID, individualId);
        activity.startActivity(intent);
    }

    public void showSettings(@Nonnull Context context) {
        Intent settingIntent = new Intent(context, SettingsActivity.class);
        context.startActivity(settingIntent);
    }

    public void showHelp(Context context) {
        Intent aboutIntent = new Intent(context, AboutActivity.class);
        context.startActivity(aboutIntent);
    }
}
