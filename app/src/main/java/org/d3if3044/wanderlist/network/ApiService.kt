package org.d3if3044.wanderlist.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if3044.wanderlist.model.MessageResponse
import org.d3if3044.wanderlist.model.Destinasi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://wanderlist-ecru.vercel.app/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()



private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface UserApi {
    @Multipart
    @POST("destinasi/")
    suspend fun addData(
        @Part("destinasi") destinasi: RequestBody,
        @Part("kendaraan") kendaraan: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("user_email") userEmail: RequestBody,
        @Part file: MultipartBody.Part
    ): Destinasi
    @GET("destinasi/")
    suspend fun getAllData(
        @Query("email") email: String,
    ): List<Destinasi>

    @DELETE("destinasi/{destination_id}")
    suspend fun deleteData(
        @Path("destination_id") id: Int,
        @Query("email") email: String
    ): MessageResponse
}


object Api {
    val userService: UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }

    fun getImageUrl(imageId: String): String{
        return BASE_URL + "destinasi/images/$imageId"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }