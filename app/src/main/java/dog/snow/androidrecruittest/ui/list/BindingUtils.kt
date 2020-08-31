package dog.snow.androidrecruittest.ui.list

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import dog.snow.androidrecruittest.GlideApp
import dog.snow.androidrecruittest.R


@BindingAdapter("thumbnailUrl")
fun ImageView.setThumbnailUrl(url:String){

    Log.d("TAG", "setThumbnailUrl: $url")
    GlideApp.with(context)
        .load(url)
        .error(R.drawable.ic_logo_sd_symbol)
        .into(this)
}