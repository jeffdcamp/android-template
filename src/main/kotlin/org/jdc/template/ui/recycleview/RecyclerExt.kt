@file:JvmName("RecyclerExt")

package org.jdc.template.ui.recycleview

import android.support.annotation.MenuRes
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View

/**
 * RecyclerView Extensions
 */


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
    if (adapterPosition != android.support.v7.widget.RecyclerView.NO_POSITION) {
        block(adapterPosition)
    }
}

fun <T : RecyclerView.ViewHolder> T.executeOnValidPositionBoolean(defaultReturn: Boolean = false, block: (position: Int) -> Boolean): Boolean {
    if (adapterPosition != android.support.v7.widget.RecyclerView.NO_POSITION) {
        return block(adapterPosition)
    }

    return defaultReturn
}