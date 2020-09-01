package dog.snow.androidrecruittest.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dog.snow.androidrecruittest.MainActivity
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.databinding.DetailsFragmentBinding
import kotlinx.android.synthetic.main.layout_appbar.*

@AndroidEntryPoint
class DetailsFragment : Fragment(){


    private val viewModel:DetailsViewModel by viewModels()

    val details by lazy {
        viewModel.details
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).appbar.isVisible=true

        val args:DetailsFragmentArgs by navArgs()
        viewModel.setDetailsFromDatabase(args.id)


        val binding=DetailsFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        binding.viewModel=viewModel

        return binding.root
    }
}