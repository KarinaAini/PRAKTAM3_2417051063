package com.example.praktam3_2417051063.data.model

import com.google.gson.annotations.SerializedName

data class Sosial(
    @SerializedName("nama")
    val nama: String,

    @SerializedName("deskripsi")
    val deskripsi: String,

    @SerializedName("teman")
    val teman: Int,

    @SerializedName("image_url")
    val imageUrl: String
)