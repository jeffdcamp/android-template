package org.jdc.template.ui.adapter

import android.content.Context
import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devbrackets.android.recyclerext.adapter.RecyclerCursorAdapter
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.list_item_dual_pane.view.*
import org.jdc.template.R.layout.list_item_dual_pane
import org.jdc.template.dagger.Injector
import org.jdc.template.model.database.main.individual.Individual
import org.jdc.template.event.DirectoryItemClickedEvent
import pocketbus.Bus
import javax.inject.Inject

class DirectoryAdapter(context: Context, cursor: Cursor?, dualPane: Boolean) : RecyclerCursorAdapter<DirectoryAdapter.ViewHolder>(cursor) {
    @Inject
    lateinit var bus: Bus

    private val inflater: LayoutInflater

    // dual pane variables
    private var dualPane = false
    var lastSelectedItemId: Long = 0
    private var lastSelectedViewHolder: ViewHolder? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        @Bind(R.id.text1)
//        internal var text1TextView: TextView
//        @Bind(R.id.list_item)
//        internal var listItemView: View

        // dual pane items
//        @Bind(R.id.list_item_full_layout)
//        internal var listItemFullLayout: View
//        @Bind(R.id.listview_sidebar_selected)
//        internal var sideBarSelectedView: View

        fun setText(text: String) {
            itemView.text1.text = text
        }

        fun setItemSelected(selected: Boolean) {
            itemView.list_item_full_layout.isSelected = selected
            itemView.listview_sidebar_selected.visibility = if (selected) View.VISIBLE else View.GONE
        }
    }

    init {
        Injector.get().inject(this)

        this.inflater = LayoutInflater.from(context)
        this.dualPane = dualPane
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = inflater.inflate(list_item_dual_pane, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, cursor: Cursor, position: Int) {
        val individual = Individual(cursor)
        val itemId = individual.id

        // bind data to view holder
        holder.itemView.text1.text = individual.getFullName()
        if (dualPane) {
            holder.setItemSelected(itemId == lastSelectedItemId)
        }

        // Click listener
        holder.itemView.list_item.setOnClickListener { onItemClicked(holder, itemId) }
    }

    private fun onItemClicked(holder: ViewHolder, selectedItemId: Long) {
        this.lastSelectedItemId = selectedItemId
        toggleDualPaneSelection(holder)

        bus.post(DirectoryItemClickedEvent(selectedItemId))
    }

    // dual pane methods
    private fun toggleDualPaneSelection(holder: ViewHolder) {
        if (dualPane) {
            if (lastSelectedViewHolder != null) {
                lastSelectedViewHolder!!.setItemSelected(false)
            }
            holder.setItemSelected(true)

            lastSelectedViewHolder = holder
        }
    }
}
