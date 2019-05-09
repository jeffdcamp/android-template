package org.jdc.template.ui.activity

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.allOf
import org.jdc.template.CustomMatchers.recyclerViewWithItemCount
import org.jdc.template.R
import org.jdc.template.ux.startup.StartupActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SmokeTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(StartupActivity::class.java)

    @Test
    fun smokeTest() {
        val overflowMenuButton = onView(allOf<View>(withContentDescription("More options"), isDisplayed()))
        overflowMenuButton.perform(click())

        val aboutMenuView = onView(allOf<View>(withId(R.id.title), withText("About"), isDisplayed()))
        aboutMenuView.perform(click())

        val createDatabaseButton = onView(allOf<View>(withId(R.id.createDatabaseButton), withText("Create Database"), isDisplayed()))
        createDatabaseButton.perform(click())

        pressBack()

        val directoryRecyclerView = onView(allOf<View>(withId(R.id.recyclerView), isDisplayed()))

        directoryRecyclerView.check(matches(recyclerViewWithItemCount(3)))
        directoryRecyclerView.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        val nameTextView = onView(allOf<View>(withId(R.id.nameTextView), withText("Jeff Campbell"), isDisplayed()))
        nameTextView.check(matches(withText("Jeff Campbell")))

        val editMenuItemView = onView(allOf<View>(withId(R.id.menu_item_edit), withContentDescription("Edit"), isDisplayed()))
        editMenuItemView.perform(click())

        val firstNameEditText = onView(allOf<View>(withId(R.id.firstNameEditText), withText("Jeff"), isDisplayed()))
        firstNameEditText.perform(click())

        val firstNameEditText2 = onView(allOf<View>(withId(R.id.firstNameEditText), withText("Jeff"), isDisplayed()))
        firstNameEditText2.perform(replaceText("Jeffery"))

        val saveMenuItemView = onView(allOf<View>(withId(R.id.menu_item_save), withText("Save"), isDisplayed()))
        saveMenuItemView.perform(click())

        val nameTextView2 = onView(allOf<View>(withId(R.id.nameTextView), withText("Jeffery Campbell"), isDisplayed()))
        nameTextView2.check(matches(withText("Jeffery Campbell")))

        val imageButton = onView(
                allOf<View>(withContentDescription("Navigate up"), withParent(allOf<View>(withId(R.id.mainToolbar), isDisplayed())),
                        isDisplayed()))
        imageButton.perform(click())

        val listItemTextView = onView(
                allOf<View>(withId(R.id.listItemTextView), withText("Jeffery Campbell"),
                        withParent(allOf<View>(withId(R.id.listItemLayout),
                                withParent(withId(R.id.recyclerView)))),
                        isDisplayed()))
        listItemTextView.check(matches(withText("Jeffery Campbell")))

        val directoryRecyclerView2 = onView(
                allOf<View>(withId(R.id.recyclerView), isDisplayed()))
        directoryRecyclerView2.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))

        val deleteMenuItemView = onView(allOf<View>(withId(R.id.menu_item_delete), withContentDescription("Delete"), isDisplayed()))
        deleteMenuItemView.perform(click())

        val deleteDialogButton = onView(allOf<View>(withId(R.id.md_button_positive), withText("Delete"), isDisplayed()))
        deleteDialogButton.perform(click())

        val directoryRecyclerView3 = onView(allOf<View>(withId(R.id.recyclerView), isDisplayed()))
        directoryRecyclerView3.check(matches(recyclerViewWithItemCount(2)))
    }
}
