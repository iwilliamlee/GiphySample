package com.application.giphysample.retrofit

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import java.util.concurrent.TimeUnit


interface GiphyService {

    @GET("search")
    fun searchGiphy(@Query("api_key") apiKey: String = "HfQl65WIsUozzymYWvvsrNOZOdNB6szA",
                      @Query("q") query: String,
                      @Query("limit") limit: Int = 25,
                      @Query("offset") offset: Int,
                      @Query("rating") rating: String = "G",
                      @Query("lang") lang: String = "eng"):
            Observable<GiphyResponse.Result>



    @GET("search")
    fun searchGiphyCallback(@Query("api_key") apiKey: String = "HfQl65WIsUozzymYWvvsrNOZOdNB6szA",
                    @Query("q") query: String,
                    @Query("limit") limit: Int = 25,
                    @Query("offset") offset: Int,
                    @Query("rating") rating: String = "G",
                    @Query("lang") lang: String = "eng"):
            Call<GiphyResponse.Result>

    companion object {
        fun create(): GiphyService {
//            val logging = HttpLoggingInterceptor()
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//            val httpClient = OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .connectTimeout(5, TimeUnit.MINUTES) // Change it as per your requirement
//                .readTimeout(5, TimeUnit.MINUTES)// Change it as per your requirement
//                .writeTimeout(5, TimeUnit.MINUTES)// Change it as per your requirement
//            // <-- this is the important line!

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.giphy.com/v1/gifs/")
//                .client(httpClient.build())
                .build()

            return retrofit.create(GiphyService::class.java)
        }
    }

}