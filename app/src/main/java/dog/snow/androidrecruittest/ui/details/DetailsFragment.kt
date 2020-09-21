package dog.snow.androidrecruittest.ui.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import dagger.hilt.android.AndroidEntryPoint
import dog.snow.androidrecruittest.MainActivity
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.databinding.DetailsFragmentBinding



@AndroidEntryPoint
class DetailsFragment : Fragment() {


    private val viewModel: DetailsViewModel by viewModels()

    val details by lazy {
        viewModel.details
    }
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: DetailsFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initActionBar()

        viewModel.setDetailsFromDatabase(args.id)

        details.observe(viewLifecycleOwner, Observer { details ->
            (activity as MainActivity).supportActionBar?.title = details.photoTitle
        })


        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addConstraintAnimationOperations()
        setTransitionNames()
    }

    private fun setTransitionNames() {
        ViewCompat.setTransitionName(binding.tvPhotoTitle, "title_${args.id}")
        ViewCompat.setTransitionName(binding.ivPhoto, "image_${args.id}")

    }

    private fun initActionBar() {
        (activity as MainActivity).apply {
            val appBar=this.binding.layoutIncludeAppbar.appbar
            val toolbar=this.binding.layoutIncludeAppbar.toolbar

            appBar.apply {
                isVisible = true
                setExpanded(true, true)
            }
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayUseLogoEnabled(false)
            }
            toolbar.apply {
                titleMarginStart = resources.getDimensionPixelSize(R.dimen.margin_normal)
                setNavigationOnClickListener {
                    onBackPressed()
                }
            }

        }

    }

    private fun addConstraintAnimationOperations() {
        var set = false
        val constraint1 = ConstraintSet()
        val root= binding.root
        constraint1.clone(root)
        val constraint2 = ConstraintSet()
        constraint2.clone(context, R.layout.details_after_fragment)

        binding.btMore.setOnClickListener {
            TransitionManager.beginDelayedTransition(root)
            if (!set) {
                constraint2.applyTo(root)
                set = !set
            }
        }

        binding.ivPhoto.setOnClickListener {
            TransitionManager.beginDelayedTransition(root)
            if (set) {
                constraint1.applyTo(root)
                set = !set
            }
        }

    }
}