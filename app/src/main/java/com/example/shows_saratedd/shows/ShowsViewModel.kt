package com.example.shows_saratedd.shows

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shows_saratedd.ApiModule
import com.example.shows_saratedd.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Tag

class ShowsViewModel: ViewModel() {

    private val showsLiveData: MutableLiveData<List<Show>> by lazy {
        MutableLiveData<List<Show>>()
}

    private val showsResultLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getShowsResultLiveData(): LiveData<Boolean> {
        return showsResultLiveData
    }

    fun getShowsLiveData() : LiveData<List<Show>> {
        return showsLiveData
    }


//    init {
//        _showsLiveData.value = shows
//    }

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
}