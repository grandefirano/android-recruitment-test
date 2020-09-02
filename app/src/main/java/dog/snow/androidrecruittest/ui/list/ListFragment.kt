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
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dog.snow.androidrecruittest.MainActivity
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.databinding.ListFragmentBinding
import kotlinx.android.synthetic.main.layout_appbar.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import kotlinx.android.synthetic.main.list_item.view.*

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val TAG = "ListFragment"
    private val viewModel: ListViewModel by viewModels()

    private val listOfResults by lazy {
        viewModel.listOfResults
    }

    private val searchQuery by lazy {
        viewModel.searchQuery
    }

    private val listAdapter by lazy {
        ListAdapter(PhotoItemClickListener { item,view ->
            viewModel.onItemClicked(item.id,view)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initActionBar()
        val binding = ListFragmentBinding.inflate(inflater, container, false)

        observeNavigationState()
        observeQuery()
        setRecyclerViewAndAdapter(binding)
        return binding.root
    }

    private fun initActionBar() {
        (activity as MainActivity).apply {
            appbar.isVisible = true

            title = getString(R.string.app_name)
            supportActionBar?.setLogo(getDrawable(R.drawable.ic_logo_sd_symbol))
            supportActionBar?.setDisplayUseLogoEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            appbar.toolbar.titleMarginStart = resources.getDimensionPixelSize(R.dimen.margin_xlarge)
        }
    }

    private fun observeNavigationState() {
        viewModel.navigateToDetailsFragment.observe(viewLifecycleOwner, Observer { event ->
            Log.d(TAG, "observeNavigationState: navigate")

            event.getContentIfNotHandled()?.let {  idViewPair->
                val id=idViewPair.first
                val view=idViewPair.second
                Log.d(TAG, "observeNavigationState: view $view")
                val extras= FragmentNavigatorExtras(view.tv_photo_title to "title_$id")
                findNavController().navigate(
                    ListFragmentDirections.actionListFragmentToDetailsFragment(id),
                    extras
                )
            }

        })
    }

    private fun observeQuery() {
        searchQuery.observe(viewLifecycleOwner, Observer { query ->
            Log.d(TAG, "onCreateView:search query changed $query ")
            viewModel.filterList(query)
        })
    }

    private fun setRecyclerViewAndAdapter(binding: ListFragmentBinding) {
        listOfResults.observe(viewLifecycleOwner, Observer { items ->

            items?.let {
                listAdapter.submitList(items)

                binding.rvItems.isVisible = items.isNotEmpty()
                binding.emptyView.tvEmpty.isVisible = items.isEmpty()
            }
        })

        binding.viewModel = viewModel
        binding.rvItems.apply {
            adapter = listAdapter
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
        binding.rvItems.layoutManager = LinearLayoutManager(context)

    }
}