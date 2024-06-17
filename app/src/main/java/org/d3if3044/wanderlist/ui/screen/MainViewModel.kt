package org.d3if3044.wanderlist.ui.screen

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.d3if3044.wanderlist.network.ApiStatus
import org.d3if3044.wanderlist.network.Api
import org.d3if3044.wanderlist.network.ImageApi
import org.d3if3044.wanderlist.model.Destinasi
import org.d3if3044.wanderlist.model.DestinasiCreate
import org.d3if3044.wanderlist.model.ImageData
import java.io.ByteArrayOutputStream

class MainViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Destinasi>())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    var errorMessageNoToast = mutableStateOf<String?>(null)
        private set

    var querySuccess = mutableStateOf(false)
        private set


    fun retrieveData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
                data.value = Api.userService.getAllData(userId)
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("MainVM", "data error: ${e.message}")
                errorMessageNoToast.value = when (e.message) {
                    "HTTP 404 " -> "Anda belum memasukkan data."
                    else -> "Gagal memuat data."
                }
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }

    fun saveData(
        email: String,
        tujuan: String,
        kendaraan: String,
        deskripsi: String,
        bitmap: Bitmap
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val upload = ImageApi.imgService.uploadImg(
                    image = bitmap.toMultipartBody()
                )

                if (upload.success) {
                    Api.userService.addData(
                        DestinasiCreate(
                            email,
                            tujuan,
                            kendaraan,
                            deskripsi,
                            transformImageData(upload.data),
                            upload.data.deletehash!!
                        )
                    )
                    status.value = ApiStatus.LOADING
                    retrieveData(email)
                    querySuccess.value = true
                }
            } catch (e: Exception) {
                Log.d("MainVM", "save error: ${e.message}")
                errorMessage.value = when (e.message) {
                    "HTTP 500 " -> "Database idle, harap inputkan kembali data."
                    else -> "Terjadi kesalahan, harap coba lagi"
                }
            }
        }
    }

    fun deleteData(email: String, id: Int, deleteHash: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val upload = ImageApi.imgService.deleteImg(
                    deleteHash = deleteHash
                )
                if (upload.success) {
                    Api.userService.deleteData(id, email)
                    querySuccess.value = true
                    retrieveData(email)
                }
            } catch (e: Exception) {
                Log.d("MainVM", "delete error: ${e.message}")
                errorMessage.value = when (e.message) {
                    "HTTP 500 " -> "Database idle, harap inputkan kembali data."
                    else -> "Terjadi kesalahan, harap coba lagi"
                }
            }
        }
    }

    private fun Bitmap.toMultipartBody(): MultipartBody.Part {
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody(
            "image/jpg".toMediaTypeOrNull(), 0, byteArray.size
        )
        return MultipartBody.Part.createFormData("image", "image.jpg", requestBody)
    }

    fun transformImageData(imageData: ImageData): String {
        val extension = when (imageData.type) {
            "image/png" -> "png"
            "image/jpeg" -> "jpg"
            "image/gif" -> "gif"
            else -> throw IllegalArgumentException("Unsupported image type")
        }
        return "${imageData.id}.$extension"
    }

    fun clearMessage() {
        errorMessage.value = null
        querySuccess.value = false
    }

}