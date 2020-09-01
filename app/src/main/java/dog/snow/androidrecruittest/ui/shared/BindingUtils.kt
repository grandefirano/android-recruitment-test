package dog.snow.androidrecruittest.ui.shared

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import dog.snow.androidrecruittest.R

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url:String?){
    url?.let {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_placeholder)
            .into(this)
    }
}