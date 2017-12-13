package org.jdc.template.ux.directory

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*
import org.jdc.template.R
import org.jdc.template.datasource.database.main.individual.IndividualDao
import org.jdc.template.ui.recycleview.RecyclerViewDiffAdapter
import org.jdc.template.ui.recycleview.setOnClickListener

class DirectoryAdapter : RecyclerViewDiffAdapter<IndividualDao.DirectoryListItem, DirectoryAdapter.ViewHolder>() {

    var itemClickListener: (IndividualDao.DirectoryListItem) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent).apply {
            setOnClickListener { position -> itemClickListener(items[position]) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val individual = items[position]

        // bind data to view holder
        holder.itemView.apply {
            listItemTextView.text = individual.getFullName()
        }
    }

    override fun areItemsTheSame(oldItem: IndividualDao.DirectoryListItem, newItem: IndividualDao.DirectoryListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: IndividualDao.DirectoryListItem, newItem: IndividualDao.DirectoryListItem): Boolean {
        return oldItem.id == newItem.id && oldItem.firstName == newItem.firstName && oldItem.lastName == newItem.lastName
    }

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
}
