package org.jdc.template.ui.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.jdc.template.R
import org.jdc.template.ux.startup.StartupActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * ./gradlew connectedAndroidTest
 */
@RunWith(AndroidJUnit4::class)
class SmokeTest {

    @get:Rule
    var activityTestRule = ActivityScenarioRule(StartupActivity::class.java)

    @Test
    fun smokeTest() {
        val overflowMenuButton = onView(allOf(withContentDescription("More options"), isDisplayed()))
        overflowMenuButton.perform(click())

        val aboutMenuView = onView(allOf(withId(R.id.title), withText("About"), isDisplayed()))
        aboutMenuView.perform(click())

        val createDatabaseButton = onView(allOf(withId(R.id.createDatabaseButton), withText("Create Database"), isDisplayed()))
        createDatabaseButton.perform(click())

        pressBack()

//        val directoryRecyclerView = onView(allOf(withId(R.id.recyclerView), isDisplayed()))

//        directoryRecyclerView.check(matches(recyclerViewWithItemCount(3)))
//        directoryRecyclerView.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        val nameTextView = onView(allOf(withId(R.id.nameTextView), withText("Jeff Campbell"), isDisplayed()))
        nameTextView.check(matches(withText("Jeff Campbell")))

//        val editMenuItemView = onView(allOf(withId(R.id.menu_item_edit), withContentDescription("Edit"), isDisplayed()))
//        editMenuItemView.perform(click())

//        val firstNameEditText = onView(allOf(withId(R.id.firstNameEditText), withText("Jeff"), isDisplayed()))
//        firstNameEditText.perform(click())
//
//        val firstNameEditText2 = onView(allOf(withId(R.id.firstNameEditText), withText("Jeff"), isDisplayed()))
//        firstNameEditText2.perform(replaceText("Jeffery"))

//        val saveMenuItemView = onView(allOf(withId(R.id.menu_item_save), withText("Save"), isDisplayed()))
//        saveMenuItemView.perform(click())

        val nameTextView2 = onView(allOf(withId(R.id.nameTextView), withText("Jeffery Campbell"), isDisplayed()))
        nameTextView2.check(matches(withText("Jeffery Campbell")))

        val imageButton = onView(
            allOf(withContentDescription("Navigate up"), withParent(allOf(withId(R.id.mainToolbar), isDisplayed())),
                isDisplayed()))
        imageButton.perform(click())

//        val listItemTextView = onView(
//            allOf(withId(R.id.listItemTextView), withText("Jeffery Campbell"),
//                withParent(allOf(withId(R.id.listItemLayout),
//                    withParent(withId(R.id.recyclerView)))),
//                isDisplayed()))
//        listItemTextView.check(matches(withText("Jeffery Campbell")))

//        val directoryRecyclerView2 = onView(
//            allOf(withId(R.id.recyclerView), isDisplayed()))
//        directoryRecyclerView2.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))

//        val deleteMenuItemView = onView(allOf(withId(R.id.menu_item_delete), withContentDescription("Delete"), isDisplayed()))
//        deleteMenuItemView.perform(click())

//        val deleteDialogButton = onView(allOf(withId(R.id.md_button_positive), withText("Delete"), isDisplayed()))
        val deleteDialogButton = onView(allOf(withId(android.R.id.button1), withText("Delete"), isDisplayed()))
        deleteDialogButton.perform(click())

//        val directoryRecyclerView3 = onView(allOf(withId(R.id.recyclerView), isDisplayed()))
//        directoryRecyclerView3.check(matches(recyclerViewWithItemCount(2)))
    }
}