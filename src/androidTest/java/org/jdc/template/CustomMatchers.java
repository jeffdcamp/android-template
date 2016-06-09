package org.jdc.template;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;

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
