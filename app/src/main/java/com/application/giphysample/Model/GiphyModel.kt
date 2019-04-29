package com.application.giphysample.Model

import com.application.giphysample.retrofit.GiphyResponse

/**
 * Model to use in rest of application
 */
data class GiphyModel (
    val pagination: GiphyResponse.Pagination,
    val url: String
)