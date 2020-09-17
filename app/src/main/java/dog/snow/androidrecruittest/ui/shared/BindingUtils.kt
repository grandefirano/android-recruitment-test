package dog.snow.androidrecruittest.ui.shared

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import dog.snow.androidrecruittest.R


@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    url?.let {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_placeholder)
            .into(this)
    }
}

@BindingAdapter("animationDirection")
fun View.setAnimationDirection(direction:AnimationDirection){
   val animation=when(direction){
       AnimationDirection.LEFT->AnimationUtils.loadAnimation(context, R.anim.slide_in_left)
       AnimationDirection.RIGHT->AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
   }
    this.animation=animation

}


enum class AnimationDirection{
    LEFT,RIGHT
}
