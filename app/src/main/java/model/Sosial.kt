package model

import androidx.annotation.DrawableRes

data class Sosial(
    val nama: String,
    val deskripsi: String,
    val teman: Int,
    @DrawableRes val imageRes: Int
)