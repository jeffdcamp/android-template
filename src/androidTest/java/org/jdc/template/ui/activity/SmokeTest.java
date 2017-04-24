package org.jdc.template.ui.activity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.jdc.template.R;
import org.jdc.template.ux.startup.StartupActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.jdc.template.CustomMatchers.recyclerViewWithItemCount;

@RunWith(AndroidJUnit4.class)
public class SmokeTest {

    @Rule
    public ActivityTestRule<StartupActivity> mActivityTestRule = new ActivityTestRule<>(StartupActivity.class);

    @Test
    public void smokeTest() {
        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"), isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(org.jdc.template.R.id.title), withText("About"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.createDatabaseButton), withText("Create Database"), isDisplayed()));
        appCompatButton.perform(click());

        pressBack();

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerView), isDisplayed()));

        recyclerView.check(matches(recyclerViewWithItemCount(2)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.nameTextView), withText("Jeff Campbell"), isDisplayed()));
        textView.check(matches(withText("Jeff Campbell")));

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(org.jdc.template.R.id.menu_item_edit), withContentDescription("Edit"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.firstNameEditText), withText("Jeff"),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(org.jdc.template.R.id.firstNameEditText), withText("Jeff"),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("Jeffery"));

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(org.jdc.template.R.id.menu_item_save), withText("Save"), withContentDescription("Save"), isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.nameTextView), withText("Jeffery Campbell"), isDisplayed()));
        textView2.check(matches(withText("Jeffery Campbell")));

        ViewInteraction imageButton = onView(
                allOf(withContentDescription("Navigate up"),withParent(allOf(withId(R.id.mainToolbar), isDisplayed())),
                        isDisplayed()));
        imageButton.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(org.jdc.template.R.id.text1), withText("Jeffery Campbell"),
                        withParent(allOf(withId(org.jdc.template.R.id.list_item),
                                withParent(withId(R.id.recyclerView)))),
                        isDisplayed()));
        textView3.check(matches(withText("Jeffery Campbell")));

        ViewInteraction recyclerView3 = onView(
                allOf(withId(org.jdc.template.R.id.recyclerView), isDisplayed()));
        recyclerView3.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(org.jdc.template.R.id.menu_item_delete), withContentDescription("Delete"), isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("Delete"),
                        withParent(allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                withParent(withClassName(is("android.widget.LinearLayout"))))),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction recyclerView4 = onView(
                allOf(withId(org.jdc.template.R.id.recyclerView), isDisplayed()));


        recyclerView4.check(matches(recyclerViewWithItemCount(1)));
    }
}
