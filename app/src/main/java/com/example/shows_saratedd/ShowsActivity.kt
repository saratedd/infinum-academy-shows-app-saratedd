package com.example.shows_saratedd

import Show.Show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ShowsActivity : AppCompatActivity() {

    private val shows = listOf(
        Show("0",
            "The Office",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
            R.drawable.ic_office),
        Show("1",
            "Stranger Things",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
            R.drawable.ic_stranger_things),
        Show("2",
            "Krv nije voda",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
            R.drawable.krv_nije_voda)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shows)
    }
}