package com.application.giphysample.Model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.application.giphysample.retrofit.GiphyResponse
import com.application.giphysample.retrofit.GiphyService
import io.reactivex.disposables.CompositeDisposable

class GiphyDataSourceFactory(
    private val query: String,
    private val giphyService: GiphyService,
    private val compositeDisposable: CompositeDisposable)
: DataSource.Factory<GiphyResponse.Pagination, GiphyModel>() {

    private val TAG:String= "GiphyFactory"
    val usersDataSourceLiveData = MutableLiveData<GiphyDataSource>()


    override fun create(): DataSource<GiphyResponse.Pagination, GiphyModel> {
        Log.d(TAG, "Creating Data source")
        val giphyDataSource = GiphyDataSource(query, giphyService, compositeDisposable)
        usersDataSourceLiveData.postValue(giphyDataSource)
        return giphyDataSource
    }
}