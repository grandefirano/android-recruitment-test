package dog.snow.androidrecruittest.ui.shared

import android.widget.ImageView
import androidx.core.app.ActivityCompat.startPostponedEnterTransition
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dog.snow.androidrecruittest.R


@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url:String?){
    url?.let {
        Picasso.get()
            .load(url)
            .noFade()
            .placeholder(R.drawable.ic_placeholder)
            .into(this)

            }
//    , object : Callback {
//        override fun onSuccess() {
//            startPostponedEnterTransition()
//        }
//
//        fun onError() {
//            startPostponedEnterTransition()
//        })
    }

@BindingAdapter("thumbnailUrl")
fun ImageView.setThumbnailUrl(url:String?){
    url?.let {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_placeholder)
            .into(this)
    }
}