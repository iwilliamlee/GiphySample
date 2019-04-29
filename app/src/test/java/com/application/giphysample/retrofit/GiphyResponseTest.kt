package com.application.giphysample.retrofit

import com.application.giphysample.Model.GiphyModel
import io.reactivex.disposables.Disposable
import org.junit.Test

class GiphyResponseTest {

    private val TAG:String = "GiphyResponseTest"

    val giphyService by lazy {
        GiphyService.create()
    }

    var disposable: Disposable? = null


    @Test
    fun Test1() {
        val giphyModels: ArrayList<GiphyModel> = ArrayList()
        disposable = giphyService.searchGiphy(
            query = "sexy",
            limit = 25,
            offset = 0,
            rating = "G",
            lang = "eng")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val pagination: GiphyResponse.Pagination = it.pagination
                it.data.forEach{
                    val giphyModel: GiphyModel =
                        GiphyModel(
                            url = it.images.original.url,
                            pagination = pagination
                        )
                    System.out.println(giphyModel)
                }
            })
    }

}