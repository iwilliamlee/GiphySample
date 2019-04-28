package com.application.giphysample.repo

object GiphyResponse {
    data class Result(val data: List<Data>)
    data class Data(val images: Image)
    data class Image(val original: Original)
    data class Original(val url: String)
}