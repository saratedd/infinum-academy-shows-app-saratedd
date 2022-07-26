package com.example.shows_saratedd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import show.Show

class ShowsViewModel: ViewModel() {
    private val shows = listOf<Show>(
        Show(
            "The Office",
            "The Office is an American mockumentary sitcom television series that depicts the everyday work lives of office employees in the Scranton, Pennsylvania, branch of the fictional Dunder Mifflin Paper Company. It aired on NBC from March 24, 2005, to May 16, 2013, lasting a total of nine seasons.",
            R.drawable.ic_office
        ),
        Show(
            "Stranger Things",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam non rutrum felis. Quisque dignissim tellus a velit vehicula, non malesuada lorem eleifend. Maecenas vitae varius metus, a mollis sem. Mauris ut urna nulla. Suspendisse eget magna in ex luctus porttitor sit amet id odio. Cras in tincidunt erat, sed rutrum erat. Integer mattis, turpis id suscipit vestibulum, neque justo venenatis ligula, maximus auctor augue urna ut ipsum.",
            R.drawable.ic_stranger_things
        ),
        Show(
            "Krv nije voda",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam non rutrum felis. Quisque dignissim tellus a velit vehicula, non malesuada lorem eleifend. Maecenas vitae varius metus, a mollis sem. Mauris ut urna nulla. Suspendisse eget magna in ex luctus porttitor sit amet id odio. Cras in tincidunt erat, sed rutrum erat. Integer mattis, turpis id suscipit vestibulum, neque justo venenatis ligula, maximus auctor augue urna ut ipsum.",
            R.drawable.krv_nije_voda
        )
    )
//  casts mutable livedata to immutable livedata
    private val _showsLiveData = MutableLiveData<List<Show>>()
    val showsLiveData: LiveData<List<Show>> = _showsLiveData

    init {
        _showsLiveData.value = shows
    }
}