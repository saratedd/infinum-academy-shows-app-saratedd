package com.example.shows_saratedd.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.shows_saratedd.show_details.ShowDetailsViewModel
import java.lang.IllegalArgumentException

class ShowDetailsViewModelFactory(val database: ShowsDatabase): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(ShowDetailsViewModel::class.java))
            return super.create(modelClass)

        throw IllegalArgumentException("i'm an error. you got me.")
    }
}