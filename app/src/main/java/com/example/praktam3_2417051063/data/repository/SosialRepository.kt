package com.example.praktam3_2417051063.data.repository

import com.example.praktam3_2417051063.data.api.RetrofitClient
import com.example.praktam3_2417051063.data.model.Sosial

class SosialRepository {

    suspend fun getSosial(): List<Sosial> {
        return RetrofitClient.instance.getSosial()
    }
}