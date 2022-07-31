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
//    private val shows = listOf<Show>(
//        Show(
//            "The Office",
//            "The Office is an American mockumentary sitcom television series that depicts the everyday work lives of office employees in the Scranton, Pennsylvania, branch of the fictional Dunder Mifflin Paper Company. It aired on NBC from March 24, 2005, to May 16, 2013, lasting a total of nine seasons.",
//            R.drawable.ic_office
//        ),
//        Show(
//            "Stranger Things",
//            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam non rutrum felis. Quisque dignissim tellus a velit vehicula, non malesuada lorem eleifend. Maecenas vitae varius metus, a mollis sem. Mauris ut urna nulla. Suspendisse eget magna in ex luctus porttitor sit amet id odio. Cras in tincidunt erat, sed rutrum erat. Integer mattis, turpis id suscipit vestibulum, neque justo venenatis ligula, maximus auctor augue urna ut ipsum.",
//            R.drawable.ic_stranger_things
//        ),
//        Show(
//            "Krv nije voda",
//            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam non rutrum felis. Quisque dignissim tellus a velit vehicula, non malesuada lorem eleifend. Maecenas vitae varius metus, a mollis sem. Mauris ut urna nulla. Suspendisse eget magna in ex luctus porttitor sit amet id odio. Cras in tincidunt erat, sed rutrum erat. Integer mattis, turpis id suscipit vestibulum, neque justo venenatis ligula, maximus auctor augue urna ut ipsum.",
//            R.drawable.krv_nije_voda
//        )
//    )
//    casts mutable livedata to immutable livedata
//    jesam li mogla u mutablelivedata samo ubaciti 'shows'?
    private val _showsLiveData = MutableLiveData<List<Show>>()
    val showsLiveData: LiveData<List<Show>> = _showsLiveData

    private val showsResultLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getShowsResultLiveData(): LiveData<Boolean> {
        return showsResultLiveData
    }

    fun initShows() : LiveData<List<Show>> {
        return showsLiveData
    }


//    init {
//        _showsLiveData.value = shows
//    }

    fun onLoadShowsButtonClicked () {
        ApiModule.retrofit.getShowsAlpha()
            .enqueue(object : Callback<ShowsResponse> {
                override fun onResponse(call: Call<ShowsResponse>, response: Response<ShowsResponse>) {
                    val responseBody = response.body()
                    showsResultLiveData.value = response.isSuccessful
                }

                override fun onFailure(call: Call<ShowsResponse>, t: Throwable) {
                    showsResultLiveData.value = false
                    Log.d("shows", "onfailure: " + t.message)
                }

            })
    }
}