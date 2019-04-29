package com.application.giphysample

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.giphysample.Model.GiphyModel
import com.application.giphysample.retrofit.GiphyService
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    var searchView: SearchView? = null
    private lateinit var mainViewModel: MainViewModel
    private lateinit var giphyAdapter: GiphyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv_giphy_list = findViewById<RecyclerView>(R.id.giphy_list)
        rv_giphy_list.layoutManager = GridLayoutManager(this, 2)
        giphyAdapter = GiphyAdapter(this)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


        rv_giphy_list.adapter = giphyAdapter
        rv_giphy_list.itemAnimator = DefaultItemAnimator()
        mainViewModel.giphyList.observe(this, Observer<PagedList<GiphyModel>> {
            giphyAdapter.submitList(it)
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        Glide.get(this).clearMemory();
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.app_bar_search)?.actionView as SearchView
        searchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView!!.maxWidth = Integer.MAX_VALUE
        val context = this as AppCompatActivity;

        //When search changes, alert and reobserve viewmodel
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newQuery: String?): Boolean {
                if(newQuery != null) {
                    Glide.get(context).clearMemory();
                    mainViewModel.updateQuery(context, newQuery)
                    mainViewModel.giphyList.observe(context, Observer<PagedList<GiphyModel>> {
                        Log.d(TAG, "giphy lits has changed")
                        giphyAdapter.submitList(it)
                    })
                }
                return false;
            }

            //Do nothing because we only care about full searches
            override fun onQueryTextChange(query: String?): Boolean {
                return false;
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

}
