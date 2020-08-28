package dog.snow.androidrecruittest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dog.snow.androidrecruittest.MainActivity
import dog.snow.androidrecruittest.R

class ListFragment : Fragment(R.layout.list_fragment){


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.show()
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}