package com.bookitng.bookitng.models

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.google.gson.annotations.SerializedName

data class  LoginModel(
    var username: String?,
    var password: String?
)

data class LoginPayload (
    var token: String?
)

data class UserModel (
    var username: String?,
    var password: String?,
    var bearerToken: String?
)

data class Movie(
    var id: String?,
    var title: String?,
    @SerializedName("movie_id") var movieId: String?,
    var starring: String?,
    var synopsis: String?,
    var runtime: String?,
    var video: String?,
    var cinema: List<String>?,
    @SerializedName("show_time") var showTime: List<String>?,
    var state: String?,
    var date: String?,
    @SerializedName("created_at") var dateCreated: String?,
    @SerializedName("update_at") var updateAt: String?,
    @SerializedName("crawl_time") var crawlTime: String?
)

data class Meta(
    var count: String?,
    var pages: Int?,
    var left: Int?
)

data class TodayMovies(
    var data: List<Movie>?,
    var meta: Meta?
)

data class BusesForRoute(
        var data: List<BusTravel>?,
        var meta: Meta?
)

data class BusTravel(
    var id: String?,
    var origin: String?,
    var destination: String?,
    var price: String?,
    var service: String?,
    var departure: String?,
    var date: String?,
    var url: String?,
    var available: String?,
    var company: String?,
    @SerializedName("unique_id") var uniqueId: String?,
    @SerializedName("created_at") var createdAt: String?,
    @SerializedName("updated_at") var updatedAt: String?,
    @SerializedName("crawl_time") var crawlTime: String?
)

class BKTFragment(
    var activity: AppCompatActivity,
    var fragmentName: String,
    var fragment: Fragment,
    var nextFragment: BKTFragment?,
    var previousFragment: BKTFragment?
)