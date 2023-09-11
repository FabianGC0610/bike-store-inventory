package com.fabgod.bikestoreinventory.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.fabgod.bikestoreinventory.R
import com.fabgod.bikestoreinventory.databinding.DetailsFragmentBinding

/**
 * Fragment where the specific bike information is shown
 */
class DetailsFragment : Fragment() {

    private lateinit var binding: DetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.details_fragment,
            container,
            false,
        )

        // Set the correct color for the status bar
        setUpStatusBar()

        return binding.root
    }

    private fun setUpStatusBar() {
        val window = requireActivity().window
        window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.colorSecondaryDark)
    }
}
