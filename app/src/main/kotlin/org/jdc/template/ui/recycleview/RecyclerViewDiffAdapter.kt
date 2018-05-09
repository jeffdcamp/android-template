package org.jdc.template.ui.recycleview

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.atomic.AtomicInteger


@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class RecyclerViewDiffAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>(), MoveableItemAdapter {

    private var _items = mutableListOf<T>()
    var items: List<T>
        get() = _items
        set(value) {
            replace(_items, value)
        }

    private val dataVersion = AtomicInteger(0)
    private var job: Job? = null

    protected var detectMoves = true

    protected val lock = Any()
    protected var notifyOnChange = true

    /**
     * Called by the DiffUtil to decide whether two object represent the same Item.
     * <p>
     * For example, if your items have unique ids, this method should check their id equality.
     *
     * @param oldItem The item in the old list
     * @param newItem The item in the new list
     * @return True if the two items represent the same object or false if they are different.
     */
    abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    /**
     * Called by the DiffUtil when it wants to check whether two items have the same data.
     * DiffUtil uses this information to detect if the contents of an item has changed.
     * <p>
     * DiffUtil uses this method to check equality instead of {@link Object#equals(Object)}
     * so that you can change its behavior depending on your UI.
     * For example, if you are using DiffUtil with a
     * {@link android.support.v7.widget.RecyclerView.Adapter RecyclerView.Adapter}, you should
     * return whether the items' visual representations are the same.
     * <p>
     * This method is called only if {@link #areItemsTheSame(int, int)} returns
     * {@code true} for these items.
     *
     * @param oldItem The item in the old list
     * @param newItem The item in the new list which replaces the oldItem
     * @return True if the contents of the items are the same or false if they are different.
     */
    abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean

    /**
     * When {@link #areItemsTheSame(int, int)} returns {@code true} for two items and
     * {@link #areContentsTheSame(int, int)} returns false for them, DiffUtil
     * calls this method to get a payload about the change.
     * <p>
     * For example, if you are using DiffUtil with {@link RecyclerView}, you can return the
     * particular field that changed in the item and your
     * {@link android.support.v7.widget.RecyclerView.ItemAnimator ItemAnimator} can use that
     * information to run the correct animation.
     * <p>
     * Default implementation returns {@code null}.
     *
     * @param oldItem The item in the old list
     * @param newItem The item in the new list
     *
     * @return A payload object that represents the change between the two items or null.
     */
    protected open fun getChangePayload(oldItem: T, newItem: T): Any? = null

    abstract fun onBindViewHolder(holder: VH, item: T)

    override fun onBindViewHolder(holder: VH, position: Int, payloads: List<Any>) {
        onBindViewHolder(holder, items[position], payloads)
    }

    @Deprecated("This method is unreachable for RecyclerViewDiffAdapter override onBindViewHolder(holder: VH, item: T) OR override onBindViewHolder(holder: VH, position: Int, payloads: List<Any>)", ReplaceWith(""))
    override fun onBindViewHolder(holder: VH, position: Int) {
        error("This method is unreachable")
    }

    protected open fun onBindViewHolder(holder: VH, item: T, payloads: List<Any>) {
        onBindViewHolder(holder, item)
    }

    override fun getItemCount() = items.size

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

            val temp = _items[originalPosition]
            _items.removeAt(originalPosition)
            _items.add(finalPosition, temp)
        }

        if (notifyOnChange) {
            notifyItemMoved(originalPosition, finalPosition)
        }
    }

    private fun replace(oldItems: List<T>, newItems: List<T>) {
        job?.cancel()
        val version = dataVersion.incrementAndGet()
        when {
            oldItems.isEmpty() && newItems.isEmpty() -> return
            oldItems.isEmpty() -> {
                _items = newItems.toMutableList()
                notifyItemRangeInserted(0, newItems.size)
            }
            newItems.isEmpty() -> {
                val oldSize = oldItems.size
                _items = newItems.toMutableList()
                notifyItemRangeRemoved(0, oldSize)
            }
            else -> calculateDiff(version, oldItems, newItems)
        }
    }

    private fun calculateDiff(startVersion: Int, oldItems: List<T>, newItems: List<T>) {
        job = launch(UI) {
            val deferred = async(coroutineContext + CommonPool) {
                DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                    override fun getOldListSize() = oldItems.size
                    override fun getNewListSize() = newItems.size
                    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = areItemsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])
                    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = areContentsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])
                    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int) = getChangePayload(oldItems[oldItemPosition], newItems[newItemPosition])
                }, detectMoves)
            }
            val diffResult = deferred.await()
            if (startVersion != dataVersion.get()) {
                return@launch
            }
            _items = newItems.toMutableList()
            diffResult.dispatchUpdatesTo(this@RecyclerViewDiffAdapter)
        }
    }
}