package dog.snow.androidrecruittest.ui.list

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


@BindingAdapter("thumbnailUrl")
fun ImageView.setThumbnailUrl(url:String){
    Picasso.get().load(url).into(this)
}