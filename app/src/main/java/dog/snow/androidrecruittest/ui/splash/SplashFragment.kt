package dog.snow.androidrecruittest.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.MainActivity
@AndroidEntryPoint
class SplashFragment: Fragment(R.layout.splash_fragment) {

    val viewModel:SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updateCache()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.hide()


        observeNavigationState()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun observeNavigationState() {
        viewModel.navigateToListFragment.observe(viewLifecycleOwner, Observer { event->
           event.getContentIfNotHandled()?.let {
               findNavController().navigate(
                   SplashFragmentDirections.actionSplashFragmentToListFragment()
               )
           }

        })
    }


    private fun showError(errorMessage: String?) {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(R.string.cant_download_dialog_title)
            .setMessage(getString(R.string.cant_download_dialog_message, errorMessage))
            .setPositiveButton(R.string.cant_download_dialog_btn_positive) { _, _ -> /*tryAgain()*/ }
            .setNegativeButton(R.string.cant_download_dialog_btn_negative) { _, _ -> closeActivity() }
            .create()
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }

    private fun closeActivity(){
        (activity as MainActivity).finish()
    }
}