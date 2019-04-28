package com.application.giphysample

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.giphy_list_item.view.*

class GiphyAdapter(val items: ArrayList<String>, val context: Context) : RecyclerView.Adapter<ViewHolder>(), Filterable {

    val TAG = "GiphyAdapter"

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
//        Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(holder.ivImage)
        Glide.with(holder.itemView.context)
            .load("https://media.giphy.com/media/7rj2ZgttvgomY/giphy.gif")
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.ivImage)
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
    val ivImage = view.iv_giphy
}