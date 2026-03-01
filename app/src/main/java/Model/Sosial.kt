package com.example.praktam3_2417051063.model
import androidx.annotation.DrawableRes

data class Sosial(
    val nama: String,
    val deskripsi: String,
    val teman: String,
    @DrawableRes val imageRes: Int
)