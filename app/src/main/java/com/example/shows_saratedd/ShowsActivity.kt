package com.example.shows_saratedd

import android.content.Intent
import show.Show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shows_saratedd.databinding.ActivityShowsBinding
import show.ShowsAdapter

class ShowsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_SHOW_NAME = "showName"
        const val EXTRA_SHOW_DESC = "showDesc"
        const val EXTRA_SHOW_IMAGE = "showImage"
        const val EXTRA_USER = "user"
    }

    private val shows = listOf(
//        Show("0",
//            "The Office",
//            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
//            R.drawable.ic_office),
//        Show("1",
//            "Stranger Things",
//            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
//            R.drawable.ic_stranger_things),
//        Show("2",
//            "Krv nije voda",
//            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
//            R.drawable.krv_nije_voda)
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

    //    lateinit = declaring a property/variable wo definition
    private lateinit var binding: ActivityShowsBinding
    private lateinit var adapter: ShowsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        layout inflation = xml -> kotlin/java code
        binding = ActivityShowsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var user = intent.extras?.getString(LoginActivity.EXTRA_EMAIL)

        if (user != null) {
            initShowsRecycler(user)
        } else initShowsRecycler("anoniman")
        initLoadShowsButton()

        binding.showsLogout.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
//        initEmptyStateButton()
    }


    private fun initShowsRecycler(user: String) {
//        onitemclickcallback
        adapter = ShowsAdapter(emptyList()) { show ->
            val intent = Intent(this, ShowDetailsActivity::class.java)
            intent.putExtra(EXTRA_SHOW_NAME, show.name)
            intent.putExtra(EXTRA_SHOW_DESC, show.description)
            intent.putExtra(EXTRA_SHOW_IMAGE, show.imageResourceID)
            intent.putExtra(EXTRA_USER, user)
            startActivity(intent)
        }
//        binding.showsRecycler.layoutManager = LinearLayoutManager(this)
//        binding.showsRecycler.layoutManager = ConstraintLayout(this)
        binding.showsRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.showsRecycler.adapter = adapter
        binding.showsRecycler.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
    }

    private fun initLoadShowsButton() {
        binding.loadButton.setOnClickListener {
            adapter.addAllShows(shows)
            binding.showsRecycler.isVisible = true
            binding.emptyStateIcon.isVisible = false
            binding.emptyStateText.isVisible = false
            binding.loadButton.isVisible = false

        }
    }

//    private fun initEmptyStateButton() {
//        TODO("Not yet implemented")
//    }

}