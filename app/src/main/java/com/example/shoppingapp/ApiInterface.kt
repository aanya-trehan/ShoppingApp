package com.example.shoppingapp

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("products")
    abstract fun getProductData() : Call<MyData>

}