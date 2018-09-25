package org.jdc.template;

import android.view.View;

import org.hamcrest.Description;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;

public class CustomMatchers {
    public static BoundedMatcher<View, RecyclerView> recyclerViewWithItemCount(final int expectedCount) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            private int actualCount;

            @Override
            protected boolean matchesSafely(RecyclerView view) {
                actualCount = view.getAdapter().getItemCount();
                return  actualCount == expectedCount;
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("RecyclerView Count expected: [" + expectedCount + "] actual: [" + actualCount + "]");
            }
        };
    }

}