package org.jdc.template.ux.directory

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*
import org.jdc.template.R
import org.jdc.template.datasource.database.main.individual.IndividualDao

class DirectoryAdapter : RecyclerView.Adapter<DirectoryAdapter.ViewHolder>() {

    var list: List<IndividualDao.DirectoryListItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: (IndividualDao.DirectoryListItem) -> Unit = {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent).apply {
            clickListener = { itemClickListener(list[it.adapterPosition]) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val individual = list[position]

        // bind data to view holder
        holder.listItemTextView.text = individual.getFullName()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)) {
        // Store views here, because synthetics are not cached on views (https://youtrack.jetbrains.com/issue/KT-10542)
        val listItemTextView = itemView.listItemTextView

        init {
            itemView.setOnClickListener { clickListener(this) }
        }

        var clickListener: (ViewHolder) -> Unit = {}
    }
}
