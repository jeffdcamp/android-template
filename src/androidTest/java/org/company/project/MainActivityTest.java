package org.company.project;


import android.test.ActivityInstrumentationTestCase2;

import org.company.project.activity.MainActivity;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;


//@LargeTest
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity(); // launch the activity
    }

//    @MediumTest
    public void testHello(){
        onView(withId(R.id.test_stuff)).perform(typeText("Have a cup of Espresso."));
        onView(withId(R.id.test_button)).perform(click());
//        fail("nil");
    }


}