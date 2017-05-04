package org.jdc.template.util

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

fun <T : RecyclerView> T.getScrollPosition(): Int {
    val manager = layoutManager
    manager ?: return 0

    if (manager.itemCount <= 0) {
        return 0
    }

    return when (manager) {
        is LinearLayoutManager -> manager.findFirstCompletelyVisibleItemPosition()
        is GridLayoutManager -> manager.findFirstCompletelyVisibleItemPosition()
        else -> 0
    }
}

fun <T : RecyclerView> T.setScrollPosition(position: Int) {
    val manager = layoutManager
    manager ?: return

    return when (manager) {
        is LinearLayoutManager -> manager.scrollToPositionWithOffset(position, 0)
        is GridLayoutManager -> manager.scrollToPositionWithOffset(position, 0)
        else -> {
            // do nothing
        }
    }
}