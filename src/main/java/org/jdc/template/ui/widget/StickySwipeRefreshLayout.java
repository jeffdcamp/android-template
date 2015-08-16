package org.jdc.template.ui.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class StickySwipeRefreshLayout extends SwipeRefreshLayout {

    /**
     * A StickyListHeadersListView whose parent view is this SwipeRefreshLayout
     */
    private StickyListHeadersListView stickyListHeadersListView;

    public StickySwipeRefreshLayout(Context context) {
        super(context);
    }

    public StickySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setStickyListHeadersListView(StickyListHeadersListView stickyListHeadersListView) {
        this.stickyListHeadersListView = stickyListHeadersListView;
    }

    @Override
    public boolean canChildScrollUp() {
        if (stickyListHeadersListView != null) {
            // In order to scroll a StickyListHeadersListView up:
            // Firstly, the wrapped ListView must have at least one item
            return (stickyListHeadersListView.getListChildCount() > 0) &&
                    // And then, the first visible item must not be the first item
                    ((stickyListHeadersListView.getFirstVisiblePosition() > 0) ||
                            // If the first visible item is the first item, (we've reached the first item)
                            // make sure that its top must not cross over the padding top of the wrapped ListView
                            (stickyListHeadersListView.getListChildAt(0).getTop() < 0));

            // If the wrapped ListView is empty or, the first item is located below the padding top of the wrapped ListView,
            // we can allow performing refreshing now
        } else {
            // Fall back to default implementation
            return super.canChildScrollUp();
        }
    }
}