package com.example.shows_saratedd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationViewModel : ViewModel() {

    private val registrationResultLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun getRegistrationResultLiveData(): LiveData<Boolean> {
        return registrationResultLiveData
    }

    fun onRegisterButtonClicked(username: String, password: String) {
        val registerRequest = RegisterRequest(
            email = username,
            password = password,
            passConfirmation = password
        )
        ApiModule.retrofit.register(registerRequest)
            .enqueue(object: Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    registrationResultLiveData.value = response.isSuccessful
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    registrationResultLiveData.value = false
                }

            })
    }
}