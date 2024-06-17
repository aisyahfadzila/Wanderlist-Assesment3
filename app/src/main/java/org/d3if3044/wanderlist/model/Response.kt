package org.d3if3044.wanderlist.model

import com.squareup.moshi.Json

data class Response(

	@Json(name="kendaraan")
	val kendaraan: String? = null,

	@Json(name="user_email")
	val userEmail: String? = null,

	@Json(name="created_at")
	val createdAt: String? = null,

	@Json(name="deskripsi")
	val deskripsi: String? = null,

	@Json(name="destinasi")
	val destinasi: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="image_id")
	val imageId: String? = null
)
