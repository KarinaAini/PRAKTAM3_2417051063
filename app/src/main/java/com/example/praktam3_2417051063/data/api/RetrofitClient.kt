package com.example.praktam3_2417051063.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://gist.githubusercontent.com/KarinaAini/0ed98556453a97bd1a825bbedc54f0d9/raw/48b0ecd4232b4d84a7c2663fd9fd45811b2006ef/"

    val instance: com.example.praktam3_2417051063.data.api.ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}