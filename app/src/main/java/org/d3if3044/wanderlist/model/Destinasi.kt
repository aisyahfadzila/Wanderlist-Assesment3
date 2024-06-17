package org.d3if3044.wanderlist.model

import com.squareup.moshi.Json

data class Destinasi(
    @Json(name = "kendaraan")
    val kendaraan: String,

    @Json(name = "user_email")
    val userEmail: String,

    @Json(name = "created_at")
    val createdAt: String,

    @Json(name = "deskripsi")
    val deskripsi: String,

    @Json(name = "destinasi")
    val destinasi: String,

    @Json(name = "id")
    val id: Int,

    @Json(name = "image_id")
    val imageId: String
)

data class DestinasiCreate(
    val user_email: String,
    val destinasi: String,
    val kendaraan: String,
    val deskripsi: String,
    val image_id: String = ""
)

data class Image(
    val filename: String
)