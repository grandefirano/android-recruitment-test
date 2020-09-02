package dog.snow.androidrecruittest.ui.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.android.synthetic.main.layout_appbar.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

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
            (activity as MainActivity).title = details.photoTitle
        })


        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addConstraintAnimationOperations()
        addEnteringAnimation()
    }

    private fun addEnteringAnimation() {
        ViewCompat.setTransitionName(binding.tvPhotoTitle, "title_${args.id}")
        ViewCompat.setTransitionName(binding.ivPhoto, "image_${args.id}")

        val rightAnim = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
        rightAnim.let{
            tv_email.animation=it
            tv_email_label.animation=it
            iv_email_icon.animation = it
            tv_phone.animation=it
            tv_phone_label.animation=it
            iv_phone_icon.animation = it
            tv_username.animation=it
            tv_username_label.animation=it
            iv_username_icon.animation = it
            tv_album_title.animation=it
            tv_album_title_label.animation=it
            iv_album_title_icon.animation = it
            tv_album_title.animation = it
            bt_more.animation = it
        }


    }

    private fun initActionBar() {
        (activity as MainActivity).apply {
            appbar.isVisible = true
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayUseLogoEnabled(false)
            appbar.toolbar.titleMarginStart = resources.getDimensionPixelSize(R.dimen.margin_normal)
            appbar.setExpanded(true, true)
            appbar.toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
        }

    }

    private fun addConstraintAnimationOperations() {
        var set = false
        val constraint1 = ConstraintSet()
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