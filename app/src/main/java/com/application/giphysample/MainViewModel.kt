package com.application.giphysample

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.application.giphysample.Model.GiphyDataSourceFactory
import com.application.giphysample.Model.GiphyModel
import com.application.giphysample.retrofit.GiphyResponse
import com.application.giphysample.retrofit.GiphyService
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executors

class MainViewModel : ViewModel() {

    val TAG:String = "MainViewModel"
    var giphyList: LiveData<PagedList<GiphyModel>>

    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 25

    private var sourceFactory: GiphyDataSourceFactory

    /**
     * Initiate giphy daaasource with search as spiderman
     */
    init {
        sourceFactory = GiphyDataSourceFactory("spiderman", GiphyService.create(), compositeDisposable)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        giphyList = LivePagedListBuilder<GiphyResponse.Pagination, GiphyModel>(sourceFactory, config)
            .setFetchExecutor(Executors.newSingleThreadExecutor())
            .build()

    }

    /**
     * Called from Activity when the query is updated
     * Remove listeners,
     * Recreate the data source with new query
     * reassign listener
     */
    fun updateQuery(context: AppCompatActivity, newQuery: String) {
        giphyList.removeObservers(context)
        sourceFactory = GiphyDataSourceFactory(newQuery, GiphyService.create(), compositeDisposable)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        giphyList = LivePagedListBuilder<GiphyResponse.Pagination, GiphyModel>(sourceFactory, config)
            .setFetchExecutor(Executors.newSingleThreadExecutor())
            .build()
    }

    override fun onCleared() {
        super.onCleared()
//        Log.d(TAG, "Clearing listeners")
        compositeDisposable.dispose()
    }
}