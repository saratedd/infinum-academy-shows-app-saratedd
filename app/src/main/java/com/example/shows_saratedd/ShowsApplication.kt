package com.example.shows_saratedd

import android.app.Application
import com.example.shows_saratedd.db.ShowsDatabase
import java.util.concurrent.Executors

class ShowsApplication : Application() {

    val database by lazy {
        ShowsDatabase.getDatabase(this)
    }

    override fun onCreate() {
        super.onCreate()

        Executors.newSingleThreadExecutor().execute {
//            database.showDao().insertAllShows()
        }
    }
}