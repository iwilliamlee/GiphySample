package com.application.giphysample.Model

import com.application.giphysample.retrofit.GiphyResponse

data class GiphyModel (
    val pagination: GiphyResponse.Pagination,
    val url: String
)