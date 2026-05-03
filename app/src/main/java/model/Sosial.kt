package model

import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName
data class Sosial (
    @SerializedName("nama")
    val nama: String,

    @SerializedName("deskripsi")
    val deskripsi: String,

    @SerializedName("teman")
    val teman: Int,

    @SerializedName("image_name")
    val imageName: String
)