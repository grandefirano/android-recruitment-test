package dog.snow.androidrecruittest.ui.details

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import dog.snow.androidrecruittest.R


@BindingAdapter("author")
fun TextView.setAuthor(username:String?){
    this.text="by $username"
    }
