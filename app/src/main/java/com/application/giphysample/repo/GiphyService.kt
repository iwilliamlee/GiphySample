package com.application.giphysample.repo

import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {

    @GET("search")
    fun searchGiphy(@Query("api_key") apiKey: String,
                      @Query("q") query: String,
                      @Query("limit") limit: Int,
                      @Query("offset") offset: Int,
                      @Query("rating") rating: String,
                      @Query("lang") lang: String):
            Flowable<GiphyResponse.Result>


    companion object {
        fun create(): GiphyService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.giphy.com/v1/gifs/")
                .build()
            return retrofit.create(GiphyService::class.java)
        }
    }

}