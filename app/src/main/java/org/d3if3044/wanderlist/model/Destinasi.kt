package org.d3if3044.wanderlist.model

data class Destinasi(
    val id: Int,
    val user_email: String,
    val destinasi: String,
    val kendaraan: String,
    val deskripsi: String,
    val image_id: String,
    val delete_hash: String,
    val created_at: String
)

data class DestinasiCreate(
    val user_email: String,
    val destinasi: String,
    val kendaraan: String,
    val deskripsi: String,
    val image_id: String,
    val delete_hash: String
)
