package dog.snow.androidrecruittest.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dog.snow.androidrecruittest.MainActivity
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.databinding.DetailsFragmentBinding

class DetailsFragment : Fragment(R.layout.details_fragment){


    val viewModel:DetailsViewModel by viewModels()

    val details by lazy {
        viewModel.details
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.show()

        val args:DetailsFragmentArgs by navArgs()
        viewModel.setDetailsFromDatabase(args.id)


        val binding=DetailsFragmentBinding.inflate(inflater,container,false)

        return binding.root
    }
}