package org.jdc.template

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

object CustomMatchers {
    fun recyclerViewWithItemCount(expectedCount: Int): BoundedMatcher<View?, RecyclerView> {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            private var actualCount = 0
            override fun matchesSafely(view: RecyclerView): Boolean {
                actualCount = view.adapter?.itemCount ?: -1
                return actualCount == expectedCount
            }

            override fun describeTo(description: Description) {
                description.appendText("RecyclerView Count expected: [$expectedCount] actual: [$actualCount]")
            }
        }
    }
}