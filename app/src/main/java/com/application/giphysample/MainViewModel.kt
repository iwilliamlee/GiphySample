package com.application.giphysample

import android.util.Log
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

    private val sourceFactory: GiphyDataSourceFactory

    init {
        sourceFactory = GiphyDataSourceFactory("aquaman", GiphyService.create(), compositeDisposable)
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
        Log.d(TAG, "Clearing listeners")
        compositeDisposable.dispose()
    }
}