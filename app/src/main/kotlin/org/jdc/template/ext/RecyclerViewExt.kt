package org.jdc.template.ext

import androidx.annotation.MenuRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.StatefulItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
 * View Holder Extensions
 */

/**
 *     var itemClickListener: (MyItem) -> Unit = {}
 *
 *     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
 *         return ViewHolder(parent).apply {
 *             setValidPositionClickListener { position -> itemClickListener(items[position]) }
 *         }
 *     }
 */
fun <T : RecyclerView.ViewHolder> T.setOnClickListener(view: View = itemView, block: (position: Int) -> Unit) {
    view.setOnClickListener {
        // make sure that the adapterPosition is valid
        executeOnValidPosition { position ->
            block(position)
        }
    }
}

/**
 *     var itemLongClickListener: (MyItem) -> Unit = {}
 *
 *     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
 *         return ViewHolder(parent).apply {
 *             setOnLongClickListener { position -> itemLongClickListener(items[position]) }
 *         }
 *     }
 */
fun <T : RecyclerView.ViewHolder> T.setOnLongClickListener(view: View = itemView, block: (position: Int) -> Boolean) {
    view.setOnLongClickListener {
        // make sure that the adapterPosition is valid
        executeOnValidPositionBoolean { position ->
            block(position)
        }
    }
}

/**
 *     var menuItemClickListener: (MyItem, MenuItem) -> Boolean = { _, _ -> false }
 *
 *     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
 *         return ViewHolder(parent).apply {
 *             setOnMenuClickListener(itemView.menuButton, R.menu.menu_popup_notebook) { position, menuItem -> menuItemClickListener(items[position], menuItem) }
 *         }
 *     }
 */
fun <T : RecyclerView.ViewHolder> T.setOnMenuClickListener(view: View,
                                                                                        @MenuRes menuResourceId: Int,
                                                                                        onPreparePopupMenu: (position: Int, menu: Menu) -> Unit = { _, _ ->},
                                                                                        block: (position: Int, menuItem: MenuItem) -> Boolean) {
    view.setOnClickListener {
        executeOnValidPosition { position ->
            val popupMenu = PopupMenu(view.context, view)
            val inflater = popupMenu.menuInflater
            val menu = popupMenu.menu

            inflater.inflate(menuResourceId, menu)

            // customization to menu
            onPreparePopupMenu(position, menu)

            // click listener
            popupMenu.setOnMenuItemClickListener { menuItem ->
                block(position, menuItem)
            }

            // show
            popupMenu.show()
        }
    }
}

fun <T : RecyclerView.ViewHolder> T.executeOnValidPosition(block: (position: Int) -> Unit) {
    if (adapterPosition != RecyclerView.NO_POSITION) {
        block(adapterPosition)
    }
}

fun <T : RecyclerView.ViewHolder> T.executeOnValidPositionBoolean(defaultReturn: Boolean = false, block: (position: Int) -> Boolean): Boolean {
    if (adapterPosition != RecyclerView.NO_POSITION) {
        return block(adapterPosition)
    }

    return defaultReturn
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
 * === LONG-PRESS (default) ===
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