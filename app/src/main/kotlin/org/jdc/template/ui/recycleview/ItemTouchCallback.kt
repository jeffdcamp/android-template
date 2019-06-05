package org.jdc.template.ui.recycleview

import androidx.recyclerview.widget.RecyclerView

class ItemTouchCallback(
        dragDirections: Int = DEFAULT_DRAG_DIRECTIONS_LIST,
        dragElevationHeight: Float = DEFAULT_DRAG_ELEVATION_HEIGHT,
        private val longPressDragEnabled: Boolean = true,
        private val onMove: (originalPosition: Int, endPosition: Int) -> Unit = { _, _ -> }
) : SimpleElevationItemTouchHelperCallback(dragDirections, 0, dragElevationHeight) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        val originalPosition = viewHolder.adapterPosition
        val endPosition = target.adapterPosition
        if (originalPosition == RecyclerView.NO_POSITION || endPosition == RecyclerView.NO_POSITION) {
            return false
        }

        onMove(originalPosition, endPosition)
        return true

    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Swipe not supported
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun isLongPressDragEnabled(): Boolean {
        return longPressDragEnabled
    }
}