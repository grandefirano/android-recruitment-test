package dog.snow.androidrecruittest.ui.details

import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("author")
fun TextView.setAuthor(username:String?){
    this.text="by $username"
    }
