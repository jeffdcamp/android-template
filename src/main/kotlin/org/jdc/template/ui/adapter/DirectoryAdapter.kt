package org.jdc.template.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devbrackets.android.recyclerext.adapter.RecyclerListAdapter
import kotlinx.android.synthetic.main.list_item.view.*
import org.jdc.template.R
import org.jdc.template.model.database.main.individual.Individual

class DirectoryAdapter(context: Context) : RecyclerListAdapter<DirectoryAdapter.ViewHolder, Individual>() {

    private val inflater: LayoutInflater

    // dual pane variables
    var lastSelectedItemId: Long = 0
    var listener: OnItemClickListener? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun setText(text: String) {
            itemView.text1.text = text
        }
    }

    init {
        this.inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val individual = getItem(position);
        if (individual == null) {
            return
        }

        val itemId = individual.id

        // bind data to view holder
        holder.itemView.text1.text = individual.getFullName()
        // Click listener
        holder.itemView.list_item.setOnClickListener { onItemClicked(holder, itemId) }
    }

    private fun onItemClicked(holder: ViewHolder, selectedItemId: Long) {
        this.lastSelectedItemId = selectedItemId
        listener?.onItemClick(selectedItemId);
    }

    interface OnItemClickListener {
        fun onItemClick(selectedItemId: Long)
    }
}
