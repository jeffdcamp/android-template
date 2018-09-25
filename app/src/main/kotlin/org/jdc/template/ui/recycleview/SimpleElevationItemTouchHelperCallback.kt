package org.jdc.template.ui.recycleview

import android.graphics.Canvas
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper

/**
 * Extends the [android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback] to provide
 * support for specifying the elevation to use when an item is active (being dragged or swiped)
 */
/**
 * Creates a Callback for the given drag and swipe allowance. These values serve as
 * defaults and if you want to customize behavior per ViewHolder, you can override
 * [.getSwipeDirs]
 * and / or [.getDragDirs].
 *
 * @param dragDirections  Binary OR of direction flags in which the Views can be dragged. Must be composed of
 * [ItemTouchHelper.LEFT], [ItemTouchHelper.RIGHT],
 * [ItemTouchHelper.START], [ItemTouchHelper.END],
 * [ItemTouchHelper.UP] and [ItemTouchHelper.DOWN]
 * @param swipeDirections Binary OR of direction flags in which the Views can be swiped. Must be composed of
 * [ItemTouchHelper.LEFT], [ItemTouchHelper.RIGHT],
 * [ItemTouchHelper.START], [ItemTouchHelper.END],
 * [ItemTouchHelper.UP] and [ItemTouchHelper.DOWN]
 * @param dragElevationHeight The elevation change to use when an item becomes active
 */

abstract class SimpleElevationItemTouchHelperCallback(
        dragDirections: Int = DEFAULT_DRAG_DIRECTIONS_LIST,
        swipeDirections: Int = DEFAULT_SWIPE_DIRECTIONS,
        private val dragElevationHeight: Float = DEFAULT_DRAG_ELEVATION_HEIGHT
) : ItemTouchHelper.SimpleCallback(dragDirections, swipeDirections) {

    private var isElevated = false
    private var originalElevation = 0f

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        //To avoid elevation conflicts with the Lollipop+ implementation, we will always inform the super that we aren't active
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, false)
        if (isCurrentlyActive && !isElevated) {
            updateElevation(recyclerView, viewHolder, true)
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        updateElevation(recyclerView, viewHolder, false)
    }

    /**
     * Updates the elevation for the specified `holder` by either increasing
     * or decreasing by the specified amount
     *
     * @param recyclerView The recyclerView to use when calculating the new elevation
     * @param holder The ViewHolder to increase or decrease the elevation for
     * @param elevate True if the `holder` should have it's elevation increased
     */
    private fun updateElevation(recyclerView: RecyclerView, holder: RecyclerView.ViewHolder, elevate: Boolean) {
        if (elevate) {
            originalElevation = ViewCompat.getElevation(holder.itemView)
            val newElevation = dragElevationHeight + findMaxElevation(recyclerView)
            ViewCompat.setElevation(holder.itemView, newElevation)
            isElevated = true
        } else {
            ViewCompat.setElevation(holder.itemView, originalElevation)
            originalElevation = 0f
            isElevated = false
        }
    }

    /**
     * Finds the elevation of the highest visible viewHolder to make sure the elevated view
     * from [.updateElevation] is above
     * all others.
     *
     * @param recyclerView The RecyclerView to use when determining the height of all the visible ViewHolders
     */
    private fun findMaxElevation(recyclerView: RecyclerView): Float {
        return (0 until recyclerView.childCount)
                .map { recyclerView.getChildAt(it) }
                .map { ViewCompat.getElevation(it) }
                .max()
                ?: 0f
    }

    companion object {
        const val DEFAULT_DRAG_DIRECTIONS_LIST = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        const val DEFAULT_DRAG_DIRECTIONS_GRID = DEFAULT_DRAG_DIRECTIONS_LIST or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        const val DEFAULT_SWIPE_DIRECTIONS = 0
        const val DEFAULT_DRAG_ELEVATION_HEIGHT = 8f //NOTE: the support library implementation uses 1f as the default
    }
}