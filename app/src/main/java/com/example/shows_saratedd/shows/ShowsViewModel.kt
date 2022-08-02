package com.example.shows_saratedd.shows

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shows_saratedd.ApiModule
import com.example.shows_saratedd.FileUtil
import com.example.shows_saratedd.R
import com.example.shows_saratedd.login.LoginFragment
import com.example.shows_saratedd.login.ProfilePhotoResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Tag

class ShowsViewModel: ViewModel() {

    private lateinit var sharedPreferences: SharedPreferences

    private val showsLiveData: MutableLiveData<List<Show>> by lazy {
        MutableLiveData<List<Show>>()
    }

    private val showsResultLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val internetLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getShowsResultLiveData(): LiveData<Boolean> {
        return showsResultLiveData
    }

    fun getShowsLiveData() : LiveData<List<Show>> {
        return showsLiveData
    }

    fun onLoadShowsButtonClicked () {
        ApiModule.retrofit.getShowsAlpha()
            .enqueue(object : Callback<ShowsResponse> {
                override fun onResponse(call: Call<ShowsResponse>, response: Response<ShowsResponse>) {
                    showsLiveData.value = response.body()?.shows
                    showsResultLiveData.value = response.isSuccessful
                }

                override fun onFailure(call: Call<ShowsResponse>, t: Throwable) {
                    showsResultLiveData.value = false
                    Log.d("shows", "onfailure: " + t.message)
                }

            })
    }

    fun updateUserProfilePhoto(context: Context) {
        sharedPreferences = context.getSharedPreferences(LoginFragment.LOGIN, Context.MODE_PRIVATE)

        val file = FileUtil.getImageFile(context)!!
        val email = sharedPreferences.getString(LoginFragment.USER, "").toString()

        val request = MultipartBody
            .Part
//            .Builder()
//            .setType(MultipartBody.FORM)
//            .addFormDataPart("email", email)
//            .addFormDataPart("image", "avatar.jpg", file.asRequestBody("multipart/form-data".toMediaType()))
            .createFormData("image", "avatar.jpg", file.asRequestBody("multipart/form-data".toMediaType()))
//            .build()

        ApiModule.retrofit.updateProfilePhoto(request)
            .enqueue(object : Callback<ProfilePhotoResponse> {
                override fun onResponse(call: Call<ProfilePhotoResponse>, response: Response<ProfilePhotoResponse>) {
                    if (response.isSuccessful) {
                        //nesto
                    } else {
                        //nesto
                    }
                }
                override fun onFailure(call: Call<ProfilePhotoResponse>, t: Throwable) {
                    // nesto
                }

            })
    }
}