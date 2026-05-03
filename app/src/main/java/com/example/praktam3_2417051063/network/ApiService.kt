package com.example.praktam3_2417051063.network

import model.Sosial
import retrofit2.http.GET

interface ApiService {
    @GET("sosial_source.json")
    suspend fun getSosial(): List<Sosial>
}