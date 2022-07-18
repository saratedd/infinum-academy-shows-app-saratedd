package com.example.shows_saratedd

import show.Show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shows_saratedd.databinding.ActivityShowsBinding
import show.ShowsAdapter

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

//    lateinit = declaring a property/variable wo definition
    private lateinit var binding: ActivityShowsBinding
    private lateinit var adapter: ShowsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        layout inflation = xml -> kotlin/java code
        binding = ActivityShowsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_shows)

        initShowsrecycler()
        initEmptyStateButton()
    }

    private fun initEmptyStateButton() {
        TODO("Not yet implemented")
    }

    private fun initShowsrecycler() {
        TODO("Not yet implemented")
    }
}