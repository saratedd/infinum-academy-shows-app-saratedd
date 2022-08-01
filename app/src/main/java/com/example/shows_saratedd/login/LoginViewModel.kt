package com.example.shows_saratedd.login

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shows_saratedd.ApiModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val TOKEN_TYPE = "Bearer"

    }

    private val loginResultLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val responseLiveData: MutableLiveData<Response<LoginResponse>> by lazy {
        MutableLiveData<Response<LoginResponse>>()
    }

    fun getLoginResultLiveData(): LiveData<Boolean> {
        return loginResultLiveData
    }

    fun getResponseLiveData(): LiveData<Response<LoginResponse>> {
        return responseLiveData
    }

    fun onLoginButtonClicked(username: String, password: String, context: Context) {
        val loginRequest = LoginRequest(
            email = username,
            password = password
        )
        sharedPreferences = context.getSharedPreferences(LoginFragment.LOGIN, Context.MODE_PRIVATE)

        ApiModule.retrofit.login(loginRequest)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    loginResultLiveData.value = response.isSuccessful
                    sharedPreferences.edit{
                        putString("token_type", response.headers()[TOKEN_TYPE])
                        putString("access_token", response.headers()["access-token"])
                        putString("client", response.headers()["client"])
                        putString("uid", response.headers()["uid"])
                    }
//                    response.headers().get("token-type")
//                    response.headers().get("access-token")
//                    response.headers().get("client")
//                    response.headers().get("uid")
                    responseLiveData.value = response
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    loginResultLiveData.value = false
                }

            })
    }
}