package org.jdc.template;


import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import org.jdc.template.ui.DirectoryActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
public class DirectoryActivityTest extends ActivityInstrumentationTestCase2<DirectoryActivity> {
    public DirectoryActivityTest() {
        super(DirectoryActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity(); // launch the activity
    }

//    @MediumTest
    public void testAbout(){
        // Open the overflow menu OR open the options menu,
        // depending on if the device has a hardware or software overflow menu button.
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

//        onView(withId(R.id.menu_item_about)).perform(click());
        onView(withText("About")).perform(click());

        onView(withText("My Application")).check(matches(isDisplayed()));
    }




}