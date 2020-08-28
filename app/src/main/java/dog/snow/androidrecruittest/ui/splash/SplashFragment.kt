package dog.snow.androidrecruittest.ui.splash

import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.ui.MainActivity

class SplashFragment: Fragment(R.layout.splash_fragment) {



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