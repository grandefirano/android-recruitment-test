package dog.snow.androidrecruittest.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dog.snow.androidrecruittest.MainActivity
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.databinding.ListFragmentBinding
import kotlinx.android.synthetic.main.layout_appbar.*

@AndroidEntryPoint
class ListFragment : Fragment(){

    private val TAG = "ListFragment"
    private val viewModel:ListViewModel by viewModels()

    private val listOfResults by lazy {
        viewModel.listOfResults
    }

    private val searchQuery by lazy {
        viewModel.searchQuery
    }

    private val adapter by lazy{
        ListAdapter(PhotoItemClickListener { item ->
            viewModel.onItemClicked(item.id)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).appbar.isVisible=true
        (activity as MainActivity).title=getString(R.string.app_name)

        val binding= ListFragmentBinding.inflate(inflater,container,false)

        observeNavigationState()
        observeQuery()
        setRecyclerViewAndAdapter(binding)

        //TODO: make logic


        return binding.root
    }

    private fun observeNavigationState() {
        viewModel.navigateToDetailsFragment.observe(viewLifecycleOwner, Observer {event->
            Log.d(TAG, "observeNavigationState: navigate")
            event.getContentIfNotHandled()?.let { id->
                findNavController().navigate(
                    ListFragmentDirections.actionListFragmentToDetailsFragment(id)
                )
            }

        })
    }

    private fun observeQuery() {
        searchQuery.observe(viewLifecycleOwner, Observer {query->
            Log.d(TAG, "onCreateView:search query changed $query ")
            viewModel.filterList(query)
        })
    }

    private fun setRecyclerViewAndAdapter(binding: ListFragmentBinding) {
        listOfResults.observe(viewLifecycleOwner, Observer {items->

            items?.let {
                adapter.submitList(items)

                binding.rvItems.isVisible = items.isNotEmpty()
                binding.emptyView.tvEmpty.isVisible = items.isEmpty()
            }
        })

        binding.viewModel=viewModel
        binding.rvItems.adapter=adapter
        binding.rvItems.layoutManager=LinearLayoutManager(context)

    }
}