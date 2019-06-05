package org.jdc.template.ui.recycleview

import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

@Suppress("unused")
abstract class MovableListAdapter<T, VH : RecyclerView.ViewHolder>(diffCallback: DiffUtil.ItemCallback<T>) : RecyclerView.Adapter<VH>(), MoveableItemAdapter {

    private val asyncListDiffer = AsyncListDiffer(AdapterListUpdateCallback(this), AsyncDifferConfig.Builder(diffCallback).build())

    @Suppress("MemberVisibilityCanBePrivate") // allow override for a subclass
    protected val lock = Any()

    /**
     * Submits a new list to be diffed, and displayed.
     *
     *
     * If a list is already being displayed, a diff will be computed on a background thread, which
     * will dispatch Adapter.notifyItem events on the main thread.
     *
     * @param list The new list to be displayed.
     */
    fun submitList(list: List<T>) {
        asyncListDiffer.submitList(list)
    }

    protected fun getItem(position: Int): T = asyncListDiffer.currentList[position]

    override fun getItemCount() = asyncListDiffer.currentList.size

    fun getCurrentList(): List<T> = asyncListDiffer.currentList

    /**
     * Moves the item at the `originalPosition` to the `endPosition`.
     * If the `endPosition` is greater than the number of items, it will be added to the
     * end of the list instead.
     *
     * @param originalPosition The position the object is in that needs to be moved
     * @param endPosition The end position for the item being moved
     */
    override fun move(originalPosition: Int, endPosition: Int) {
        var finalPosition = endPosition
        synchronized(lock) {
            if (originalPosition < 0 || finalPosition < 0 || originalPosition >= itemCount) {
                return
            }

            if (finalPosition >= itemCount) {
                finalPosition = itemCount
            }

            if (originalPosition == finalPosition) {
                return
            }

            val modifiedList = ArrayList(asyncListDiffer.currentList)

            val movingItem = modifiedList[originalPosition]
            modifiedList.removeAt(originalPosition)
            modifiedList.add(finalPosition, movingItem)

            asyncListDiffer.submitList(modifiedList)
        }
    }
}