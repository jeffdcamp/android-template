package org.jdc.template.ui.recycleview

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.atomic.AtomicInteger


@Suppress("MemberVisibilityCanPrivate", "unused")
abstract class RecyclerViewDiffAdapter<T, VH : RecyclerView.ViewHolder>() : RecyclerView.Adapter<VH>() {

    private var backingItems = mutableListOf<T>()
    var items: List<T>
        get() = backingItems
        set(value) {
            replace(backingItems, value)
        }

    private val dataVersion = AtomicInteger(0)
    private var job: Job? = null

    protected var detectMoves = true

    protected val lock = Any()
    protected var notifyOnChange = true

    abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean
    abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, items[position])
    }

    protected open fun onBindViewHolder(holder: VH, t: T) {

    }

    protected open fun getChangePayload(oldItem: T, newItem: T): Any? = null

    override fun getItemCount() = items.size

    private fun replace(oldItems: List<T>, newItems: List<T>) {
        job?.cancel()
        val version = dataVersion.incrementAndGet()
        when {
            oldItems.isEmpty() && newItems.isEmpty() -> return
            oldItems.isEmpty() -> {
                backingItems = newItems.toMutableList()
                notifyItemRangeInserted(0, newItems.size)
            }
            newItems.isEmpty() -> {
                val oldSize = oldItems.size
                backingItems = newItems.toMutableList()
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
            backingItems = newItems.toMutableList()
            diffResult.dispatchUpdatesTo(this@RecyclerViewDiffAdapter)
        }
    }

    /**
     * Moves the item at the `originalPosition` to the `endPosition`.
     * If the `endPosition` is greater than the number of items, it will be added to the
     * end of the list instead.
     *
     * @param originalPosition The position the object is in that needs to be moved
     * @param endPosition The end position for the item being moved
     */
    fun move(originalPosition: Int, endPosition: Int) {
        var endPosition = endPosition
        synchronized(lock) {
            if (originalPosition < 0 || endPosition < 0 || originalPosition >= itemCount) {
                return
            }

            if (endPosition >= itemCount) {
                endPosition = itemCount
            }

            if (originalPosition == endPosition) {
                return
            }

            val temp = backingItems[originalPosition]
            backingItems.removeAt(originalPosition)
            backingItems.add(endPosition, temp)
        }

        if (notifyOnChange) {
            notifyItemMoved(originalPosition, endPosition)
        }
    }
}