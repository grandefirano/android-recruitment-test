package dog.snow.androidrecruittest.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import dog.snow.androidrecruittest.MainActivity
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.databinding.ListFragmentBinding

@AndroidEntryPoint
class ListFragment : Fragment(){

    private val viewModel:ListViewModel by viewModels()

    private val listOfResults by lazy {
        viewModel.listOfResults
    }

    private val adapter by lazy{
        ListAdapter{item,position,view->
            viewModel.onItemClicked(item.id)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.show()

        val binding= ListFragmentBinding.inflate(inflater,container,false)


        listOfResults.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        binding.viewModel=viewModel

        binding.rvItems.adapter

        return binding.root
    }
}