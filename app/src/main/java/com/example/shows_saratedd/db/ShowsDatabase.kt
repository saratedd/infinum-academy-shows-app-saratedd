package com.example.shows_saratedd.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class ShowsDatabase : RoomDatabase() {

    companion object {

        @Volatile
        private var INSTANCE: ShowsDatabase? = null

        fun getDatabase(context: Context): ShowsDatabase {
            return INSTANCE ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    context, ShowsDatabase::class.java, "shows_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = database
                database
            }
        }
    }

    abstract fun showDao(): ShowDao
    abstract fun reviewDao(): ReviewDao
}