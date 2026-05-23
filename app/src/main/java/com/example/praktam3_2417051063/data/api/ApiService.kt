package com.example.praktam3_2417051063.data.api

import com.example.praktam3_2417051063.data.model.Sosial
import retrofit2.http.GET

interface ApiService {
    @GET("sosial_source.json")
    suspend fun getSosial(): List<Sosial>
}