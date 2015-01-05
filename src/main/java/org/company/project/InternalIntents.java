package org.company.project;

import android.app.Activity;
import android.content.Intent;

import org.company.project.ui.IndividualActivity;
import org.company.project.ui.IndividualEditActivity;

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
}
