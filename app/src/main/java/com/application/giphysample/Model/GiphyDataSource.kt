package com.application.giphysample.Model

import android.util.Log
import androidx.paging.ItemKeyedDataSource
import com.application.giphysample.retrofit.GiphyResponse
import com.application.giphysample.retrofit.GiphyService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GiphyDataSource(
    private val query: String,
    private val giphyService: GiphyService,
    private var compositeDisposable: CompositeDisposable)
    : ItemKeyedDataSource<GiphyResponse.Pagination, GiphyModel>() {

    private val TAG = "GiphyDataSource"

    override fun loadInitial(params: LoadInitialParams<GiphyResponse.Pagination>, callback: LoadInitialCallback<GiphyModel>) {
        Log.d(TAG, "Starting to load initial values")
//        compositeDisposable.add(
//            giphyService.searchGiphy(
//                apiKey = "HfQl65WIsUozzymYWvvsrNOZOdNB6szA",
//                query = "sexy",
//                limit = 25,
//                offset = 0,
//                rating = "G",
//                lang = "eng")
//                .subscribe({
////                callback.onResult(it)
//                Log.d(TAG, "Got intial values from api")
//                callback.onResult(convertResponseToModel(it))
//            }, {
//                    Log.d(TAG, "Error getting values: " + it.message)
//                })
//        )
//
//        val test = giphyService.searchGiphy(
//            apiKey = "HfQl65WIsUozzymYWvvsrNOZOdNB6szA",
//            query = "sexy",
//            limit = 25,
//            offset = 0,
//            rating = "G",
//            lang = "eng")
//            .subscribe({
//                //                callback.onResult(it)
//                Log.d(TAG, "Got intial values from api")
//                callback.onResult(convertResponseToModel(it))
//            }, {
//                Log.d(TAG, "Error getting values: " + it.message)
//            })

        giphyService.searchGiphyCallback(query="sexy", offset = 0).enqueue(object : Callback<GiphyResponse.Result> {
            override fun onFailure(call: Call<GiphyResponse.Result>?, t: Throwable?) {
                Log.d(TAG, "Got failure from call")
            }

            override fun onResponse(call: Call<GiphyResponse.Result>?, response: Response<GiphyResponse.Result>?) {
                Log.d(TAG, "Got response from api in call")
                //
            }
        })

    }

    override fun loadAfter(params: LoadParams<GiphyResponse.Pagination>, callback: LoadCallback<GiphyModel>) {
        Log.d(TAG, "Starting to load after values")
        compositeDisposable.add(
            giphyService.searchGiphy(
                query = query,
                offset = params.key.offset + 1
            )        .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                //                callback.onResult(it)
                Log.d(TAG, "Got after values from api")
                callback.onResult(convertResponseToModel(it))
            })
        )
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadBefore(params: LoadParams<GiphyResponse.Pagination>, callback: LoadCallback<GiphyModel>) {
        //ignored since we only append
    }

    override fun getKey(item: GiphyModel): GiphyResponse.Pagination {
        return item.pagination
    }


    private fun convertResponseToModel(giphyResult: GiphyResponse.Result) : ArrayList<GiphyModel> {
        var giphyModels: ArrayList<GiphyModel> = ArrayList()
        giphyResult.data.forEach({
            giphyModels.add(
                GiphyModel(
                    url = it.images.original.url,
                    pagination = giphyResult.pagination
            ))
        })
        return giphyModels
    }

}