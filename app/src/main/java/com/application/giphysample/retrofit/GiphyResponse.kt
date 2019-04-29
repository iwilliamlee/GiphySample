package com.application.giphysample.retrofit

object GiphyResponse {
    data class Result(
        val data: List<Data>,
        val pagination: Pagination
    )
    data class Data(val images: Image)
    data class Image(val original: Original)
    data class Original(val url: String)

    data class Pagination(
        val total_count: Int,
        val count: Int,
        val offset: Int
    )
}