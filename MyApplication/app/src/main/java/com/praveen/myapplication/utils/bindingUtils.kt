package com.praveen.myapplication.utils

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.praveen.myapplication.R

@BindingAdapter("glideUrl")
fun loadImage(view: ImageView, url: String?) {
    val radius = view.context.resources.getDimensionPixelSize(R.dimen.spacing_6)
    if (url != null) {
        Glide.with(view.context).load(Uri.parse(url)).transform(RoundedCorners(radius))
            .placeholder(R.drawable.ic_home)
            .apply(RequestOptions().error(R.drawable.ic_home))
            .into(view)
    }
}