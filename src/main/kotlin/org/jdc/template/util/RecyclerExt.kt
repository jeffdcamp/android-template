package org.jdc.template.util

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.StatefulItemTouchHelper
import org.jdc.template.ui.recycleview.ItemTouchCallback
import org.jdc.template.ui.recycleview.MoveableItemAdapter
import org.jdc.template.ui.recycleview.SimpleElevationItemTouchHelperCallback

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

/**
 * Usage:
 * === LONG-PRESS (default) ===
 * recyclerView.setupDragDrop(adapter) {
 *     // code that happens when drag is finished
 *     saveOrder(adapter.items)
 * }
 *
 *
 * === DRAG HANDLE ===
 * Activity.kt
 * val itemTouchHelper = recyclerView.setupDragDrop(adapter, longPressDragEnabled = false) {
 *     // code that happens when drag is finished
 *     saveOrder(adapter.items)
 * }
 * adapter.itemTouchHelper = itemTouchHelper
 *
 * Adapter.kt
 * override var itemTouchHelper: ItemTouchHelper? = null
 *
 * override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
 *     return ViewHolder(parent).apply {
 *         itemView.dragHandleView.setOnTouchListener { _, motionEvent ->
 *             when(motionEvent.action) {
 *                 MotionEvent.ACTION_DOWN -> itemTouchHelper?.startDrag(this)
 *             }
 *             return@setOnTouchListener false
 *         }
 * }
 */
fun <R : RecyclerView> R.setupDragDrop(
        adapter: MoveableItemAdapter,
        dragDirections: Int = SimpleElevationItemTouchHelperCallback.DEFAULT_DRAG_DIRECTIONS_LIST,
        dragElevationHeight: Float = this.context.resources?.getDimensionPixelSize(org.jdc.template.R.dimen.drag_elevation_height)?.toFloat() ?: 0f,
        longPressDragEnabled: Boolean = true,
        onMove: (originalPosition: Int, endPosition: Int) -> Unit = { _, _ -> },
        onDragFinished: () -> Unit = {}): StatefulItemTouchHelper {

    return setupDragDrop(
            dragDirections,
            dragElevationHeight,
            longPressDragEnabled,
            { originalPosition, endPosition ->
                adapter.move(originalPosition, endPosition)
                onMove(originalPosition, endPosition)
            }, onDragFinished)

}

/**
 * Usage:
 * recyclerView.setupDragDrop(onMove = {originalPosition, endPosition -> adapter.move(originalPosition, endPosition)) {
 *     // code that happens when drag is finished
 *     saveOrder(adapter.items)
 * }
 *
 * === DRAG HANDLE ===
 * Activity.kt
 * val itemTouchHelper = recyclerView.setupDragDrop(longPressDragEnabled = false,
 *                                        onMove = {originalPosition, endPosition -> adapter.move(originalPosition, endPosition)) {
 *     // code that happens when drag is finished
 *     saveOrder(adapter.items)
 * }
 * adapter.itemTouchHelper = itemTouchHelper
 *
 * Adapter.kt
 * var itemTouchHelper: ItemTouchHelper? = null
 *
 * override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
 *     return ViewHolder(parent).apply {
 *         itemView.dragHandleView.setOnTouchListener { _, motionEvent ->
 *             when(motionEvent.action) {
 *                 MotionEvent.ACTION_DOWN -> itemTouchHelper?.startDrag(this)
 *             }
 *             return@setOnTouchListener false
 *         }
 * }
 */
fun <R : RecyclerView> R.setupDragDrop(
        dragDirections: Int = SimpleElevationItemTouchHelperCallback.DEFAULT_DRAG_DIRECTIONS_LIST,
        dragElevationHeight: Float = SimpleElevationItemTouchHelperCallback.DEFAULT_DRAG_ELEVATION_HEIGHT,
        longPressDragEnabled: Boolean = true,
        onMove: (originalPosition: Int, endPosition: Int) -> Unit = { _, _ -> },
        onDragFinished: () -> Unit = {}
): StatefulItemTouchHelper {

    var reordering = false

    val itemTouchCallback = ItemTouchCallback(dragDirections, dragElevationHeight, longPressDragEnabled) { originalPosition, endPosition ->
        onMove(originalPosition, endPosition)
    }

    val itemTouchHelper = StatefulItemTouchHelper(itemTouchCallback)
    itemTouchHelper.setOnActionStateChangedListener(object : StatefulItemTouchHelper.OnActionStateChangedListener {
        override fun onActionStateChanged(actionState: Int) {
            val save = reordering
            reordering = actionState == ItemTouchHelper.ACTION_STATE_DRAG
            if (save) {
                onDragFinished()
            }
        }
    })

    itemTouchHelper.attachToRecyclerView(this)
    return itemTouchHelper
}