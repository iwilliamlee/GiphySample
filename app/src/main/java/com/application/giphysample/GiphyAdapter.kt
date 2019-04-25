package com.application.giphysample

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import kotlinx.android.synthetic.main.giphy_list_item.view.*

class GiphyAdapter(val items: ArrayList<String>, val context: Context) : RecyclerView.Adapter<ViewHolder>(), Filterable {


    var searchItems: ArrayList<String> = ArrayList()

    init {
        searchItems = items;
    }

    override fun getItemCount(): Int {
        return searchItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.giphy_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvGiphy.text = searchItems.get(position)
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if(charString.isEmpty()) {
                    searchItems = items
                } else {
                    val filteredList : ArrayList<String> = ArrayList<String>();
                    for(row in searchItems) {
                        if(row.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    searchItems = filteredList
                }
                val filteredResults = Filter.FilterResults()
                filteredResults.values = searchItems
                return filteredResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                searchItems = filterResults.values as ArrayList<String>
                notifyDataSetChanged()
            }
        }
    }
}

class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
    val tvGiphy = view.tv_giphy
}