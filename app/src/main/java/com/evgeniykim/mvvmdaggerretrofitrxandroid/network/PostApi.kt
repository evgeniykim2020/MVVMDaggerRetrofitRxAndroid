package com.evgeniykim.mvvmdaggerretrofitrxandroid.network

import com.evgeniykim.mvvmdaggerretrofitrxandroid.model.Post
import io.reactivex.Observable
import retrofit2.http.GET

interface PostApi {

    @GET("/posts")
    fun getPosts(): Observable<List<Post>>
}