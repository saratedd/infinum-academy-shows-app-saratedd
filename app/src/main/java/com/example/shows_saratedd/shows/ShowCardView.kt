package com.example.shows_saratedd.shows

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.example.shows_saratedd.R
import com.example.shows_saratedd.databinding.ViewShowItemBinding

class ShowCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    lateinit var binding: ViewShowItemBinding

    init {
        binding = ViewShowItemBinding.inflate(LayoutInflater.from(context), this)
    }

    fun setTitle(title: String) {
        binding.showTitle.text = title
    }

    fun setImage(imageUrl: String) {
        Glide
            .with(binding.root)
            .load(imageUrl)
            //.placeholder(R.drawable.ic_office)
            .into(binding.showImage)
    }

    fun setDesc(desc: String) {
        binding.showDesc.text = desc
    }
}