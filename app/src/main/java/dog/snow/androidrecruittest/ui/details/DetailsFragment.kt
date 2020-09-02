package dog.snow.androidrecruittest.ui.details


import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import dagger.hilt.android.AndroidEntryPoint
import dog.snow.androidrecruittest.MainActivity
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.databinding.DetailsFragmentBinding
import kotlinx.android.synthetic.main.layout_appbar.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

@AndroidEntryPoint
class DetailsFragment : Fragment() {


    private val viewModel: DetailsViewModel by viewModels()

    val details by lazy {
        viewModel.details
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initActionBar()

        val args: DetailsFragmentArgs by navArgs()
        viewModel.setDetailsFromDatabase(args.id)

        details.observe(viewLifecycleOwner, Observer { details ->
            (activity as MainActivity).title = details.photoTitle
        })


        val binding = DetailsFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        ViewCompat.setTransitionName(binding.tvPhotoTitle,"title_${args.id}")
        ViewCompat.setTransitionName(binding.tvPhotoTitle,"image_${args.id}")
        binding.viewModel = viewModel

        return binding.root
    }

    private fun initActionBar() {
        (activity as MainActivity).apply {
            appbar.isVisible = true
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayUseLogoEnabled(false)
            appbar.toolbar.titleMarginStart = resources.getDimensionPixelSize(R.dimen.margin_normal)
            appbar.setExpanded(true, true)
            appbar.toolbar.setNavigationOnClickListener{
                onBackPressed()
            }
        }

    }


}