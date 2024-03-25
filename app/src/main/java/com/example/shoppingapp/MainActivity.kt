package com.example.shoppingapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView=findViewById<RecyclerView>(R.id.recyclerView)

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData=retrofitBuilder.getProductData()

//        retrofitData.enqueue(object : Callback<MyData> {
        // do control + shift + space after enqueue
        retrofitData.enqueue(object : Callback<MyData?> {

            // if api call is a success
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                var responseBody = response.body()
                val productArray= responseBody?.products!!
//                val tvProducts=findViewById<TextView>(R.id.tvName)
//                tvProducts.text=productArray.toString()

                myAdapter=MyAdapter(this@MainActivity,productArray)
                recyclerView.adapter=myAdapter
                recyclerView.layoutManager=LinearLayoutManager(this@MainActivity)
            }

            // if api call is a failure
            override fun onFailure(p0: Call<MyData?>, p1: Throwable) {
                Log.d(TAG,"onFailure: " + p1.message)
            }
        })

//        })
    }
}