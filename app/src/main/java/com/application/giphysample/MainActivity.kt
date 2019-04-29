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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    var searchView: SearchView? = null
    private lateinit var mainViewModel: MainViewModel
    private lateinit var giphyAdapter: GiphyAdapter

    val giphyService by lazy {
        GiphyService.create()
    }

    var disposable: Disposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv_giphy_list = findViewById<RecyclerView>(R.id.giphy_list)
//        rv_giphy_list.layoutManager = LinearLayoutManager(this)
        rv_giphy_list.layoutManager = GridLayoutManager(this, 2)
        giphyAdapter = GiphyAdapter(this)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


        rv_giphy_list.adapter = giphyAdapter
        rv_giphy_list.itemAnimator = DefaultItemAnimator()
//        disposable = mainViewModel.giphyList
        mainViewModel.giphyList.observe(this, Observer<PagedList<GiphyModel>> {
            Log.d(TAG, "giphy lits has changed")
            giphyAdapter.submitList(it) })

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.app_bar_search)?.actionView as SearchView
        searchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView!!.maxWidth = Integer.MAX_VALUE
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newQuery: String?): Boolean {
//                mAdapter?.filter?.filter(newQuery)
                val giphyKey: String = "HfQl65WIsUozzymYWvvsrNOZOdNB6szA"

                if(newQuery != null) {
                    disposable?.dispose()
                    Log.d(TAG, "Searching for new giphy")
                    disposable = giphyService.searchGiphy(
                        apiKey = giphyKey,
                        query = newQuery,
                        limit = 25,
                        offset = 0,
                        rating = "G",
                        lang = "eng")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            Log.d(TAG, "Got new list!")
                            var newList: ArrayList<String> = ArrayList()
                            it.data.forEach{
                                newList.add(it.images.original.url)
                            }
//                            mAdapter?.updateGiphy(newGiphyList = newList);

                        })
                }

                return false;
            }

            override fun onQueryTextChange(query: String?): Boolean {
//                mAdapter?.filter?.filter(query)
                return false;
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

}
