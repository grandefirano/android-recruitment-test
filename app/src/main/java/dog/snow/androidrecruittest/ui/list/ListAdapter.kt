package dog.snow.androidrecruittest.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dog.snow.androidrecruittest.databinding.ListItemBinding

class ListAdapter(private val clickListener: PhotoItemClickListener) :
    androidx.recyclerview.widget.ListAdapter<ListItem, ListAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder =
        ViewHolder.from(parent)


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), clickListener)

    class ViewHolder private constructor(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ListItem, clickListener: PhotoItemClickListener) {

            ViewCompat.setTransitionName(binding.tvPhotoTitle, "title_${item.id}")
            ViewCompat.setTransitionName(binding.ivThumb, "image_${item.id}")
            binding.listItem = item
            binding.clickListener = clickListener
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                oldItem == newItem
        }
    }
}

class PhotoItemClickListener(private val clickListener: (item: ListItem, view: View) -> Unit) {
    fun onClick(item: ListItem, view: View) = clickListener(item, view)
}