package dog.snow.androidrecruittest.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.MainActivity
import dog.snow.androidrecruittest.databinding.SplashFragmentBinding
import kotlinx.android.synthetic.main.layout_appbar.*

@AndroidEntryPoint
class SplashFragment : Fragment() {

    val viewModel: SplashViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.updateCache()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).appbar.isVisible = false


        val binding = SplashFragmentBinding.inflate(inflater, container, false)

        observeNavigationState()
        observeErrorState()

        binding.viewModel=viewModel
        binding.lifecycleOwner=viewLifecycleOwner

        return binding.root
    }



    private fun observeNavigationState() {
        viewModel.navigateToListFragment.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToListFragment()
                )
            }

        })
    }

    private fun observeErrorState() {
        viewModel.showError.observe(viewLifecycleOwner, Observer { message ->
            if (message !== null) {
                showError(message)
            }
        })
    }


    private fun showError(errorMessage: String?) {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(R.string.cant_download_dialog_title)
            .setMessage(getString(R.string.cant_download_dialog_message, errorMessage))
            .setPositiveButton(R.string.cant_download_dialog_btn_positive) { _, _ -> viewModel.tryAgain() }
            .setNegativeButton(R.string.cant_download_dialog_btn_negative) { _, _ -> closeActivity() }
            .create()
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }

    private fun closeActivity() {
        (activity as MainActivity).finish()
    }
}