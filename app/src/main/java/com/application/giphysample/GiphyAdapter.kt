package com.application.giphysample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.application.giphysample.Model.GiphyModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.giphy_list_item.view.*


class GiphyAdapter(val context: Context) : PagedListAdapter<GiphyModel, ViewHolder>(GiphyDiffCallback), Filterable {

    val TAG = "GiphyAdapter"

    var searchItems: ArrayList<GiphyModel> = ArrayList()

//    init {
//        super(GiphyDiffCallback)
//        searchItems = items;
//    }

    override fun getItemCount(): Int {
        return searchItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.giphy_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(holder.ivImage)
        Glide.with(holder.itemView.context)
            .load(searchItems.get(position))
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.ivImage)
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if(charString.isEmpty()) {
//                    searchItems = items
                } else {
                    val filteredList : ArrayList<String> = ArrayList<String>();
//                    for(row in searchItems) {
//                        if(row.toLowerCase().contains(charString.toLowerCase())) {
//                            filteredList.add(row)
//                        }
//                    }
//                    searchItems = filteredList
                }
                val filteredResults = Filter.FilterResults()
                filteredResults.values = searchItems
                return filteredResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
//                searchItems = filterResults.values as ArrayList<String>
                notifyDataSetChanged()
            }
        }
    }

    fun updateGiphy(newGiphyList: ArrayList<GiphyModel>) {
        searchItems = newGiphyList
        notifyDataSetChanged()
    }

    companion object {
        val GiphyDiffCallback = object : DiffUtil.ItemCallback<GiphyModel>() {

            override fun areItemsTheSame(oldItem: GiphyModel, newItem: GiphyModel): Boolean {
                return oldItem.url.equals(newItem.url)
            }

            override fun areContentsTheSame(oldItem: GiphyModel, newItem: GiphyModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}

class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
    val ivImage = view.iv_giphy
}