package org.jdc.template.ui.activity

import android.view.View
import android.view.ViewGroup
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.jdc.template.ux.startup.StartupActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SmokeTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityScenarioRule(StartupActivity::class.java)

    @Test
    fun smokeTest() {
//        val overflowMenuButton = onView(allOf<View>(withContentDescription("More options"), childAtPosition(childAtPosition(withId(R.id.mainToolbar), 1), 1), isDisplayed()))
//        overflowMenuButton.perform(click())

//        val aboutMenuView = onView(
//            allOf(
//                withId(R.id.title), withText("About"), childAtPosition(
//                    childAtPosition(
//                        withId(R.id.content),
//                        0
//                    ),
//                    0
//                ), isDisplayed()
//            )
//        )
//        aboutMenuView.perform(click())

//        val createDatabaseButton = onView(allOf<View>(withId(R.id.createDatabaseButton), withText("Create Database"), isDisplayed()))
//        createDatabaseButton.perform(click())
//
//        pressBack()
//
//        val directoryRecyclerView = onView(allOf<View>(withId(R.id.recyclerView), isDisplayed()))
//
//        directoryRecyclerView.check(matches(recyclerViewWithItemCount(3)))
//        directoryRecyclerView.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
//
//        val nameTextView = onView(allOf<View>(withId(R.id.nameTextView), withText("Jeff Campbell"), isDisplayed()))
//        nameTextView.check(matches(withText("Jeff Campbell")))
//
//        val editMenuItemView = onView(allOf<View>(withId(R.id.menu_item_edit), withContentDescription("Edit"), isDisplayed()))
//        editMenuItemView.perform(click())
//
//        val firstNameEditText = onView(allOf<View>(withId(R.id.firstNameEditText), withText("Jeff"), isDisplayed()))
//        firstNameEditText.perform(click())
//
//        val firstNameEditText2 = onView(allOf<View>(withId(R.id.firstNameEditText), withText("Jeff"), isDisplayed()))
//        firstNameEditText2.perform(replaceText("Jeffery"))
//
//        val saveMenuItemView = onView(allOf<View>(withId(R.id.menu_item_save), withText("Save"), isDisplayed()))
//        saveMenuItemView.perform(click())
//
//        val nameTextView2 = onView(allOf<View>(withId(R.id.nameTextView), withText("Jeffery Campbell"), isDisplayed()))
//        nameTextView2.check(matches(withText("Jeffery Campbell")))
//
//        val imageButton = onView(
//            allOf<View>(
//                withContentDescription("Navigate up"), withParent(allOf<View>(withId(R.id.mainToolbar), isDisplayed())),
//                isDisplayed()
//            )
//        )
//        imageButton.perform(click())
//
//        val listItemTextView = onView(
//            allOf<View>(
//                withId(R.id.listItemTextView), withText("Jeffery Campbell"),
//                withParent(
//                    allOf<View>(
//                        withId(R.id.listItemLayout),
//                        withParent(withId(R.id.recyclerView))
//                    )
//                ),
//                isDisplayed()
//            )
//        )
//        listItemTextView.check(matches(withText("Jeffery Campbell")))
//
//        val directoryRecyclerView2 = onView(
//            allOf<View>(withId(R.id.recyclerView), isDisplayed())
//        )
//        directoryRecyclerView2.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
//
//        val deleteMenuItemView = onView(allOf<View>(withId(R.id.menu_item_delete), withContentDescription("Delete"), isDisplayed()))
//        deleteMenuItemView.perform(click())
//
//        val deleteDialogButton = onView(allOf<View>(withId(android.R.id.button1), withText("Delete"), isDisplayed()))
//        deleteDialogButton.perform(click())
//
//        val directoryRecyclerView3 = onView(allOf<View>(withId(R.id.recyclerView), isDisplayed()))
//        directoryRecyclerView3.check(matches(recyclerViewWithItemCount(2)))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
