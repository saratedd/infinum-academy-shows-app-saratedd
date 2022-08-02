package com.example.shows_saratedd.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.shows_saratedd.shows.ShowsViewModel
import java.lang.IllegalArgumentException

class ShowsViewModelFactory(val database: ShowsDatabase): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(ShowsViewModel::class.java))
            return super.create(modelClass)

        throw IllegalArgumentException("i'm an error. you got me.")
    }
}