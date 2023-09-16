package com.fabgod.bikestoreinventory.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fabgod.bikestoreinventory.R
import com.fabgod.bikestoreinventory.databinding.InstructionsFragmentBinding

/**
 * Fragment where the information needed to understand each screen functionality is shown
 */
class InstructionsFragment : Fragment() {

    private lateinit var viewModel: InstructionsViewModel
    private lateinit var binding: InstructionsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.instructions_fragment,
            container,
            false,
        )

        // Set the correct color for the status bar
        setUpStatusBar()

        viewModel = ViewModelProvider(this)[InstructionsViewModel::class.java]

        binding.instructionsViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.eventButtonClicked.observe(
            viewLifecycleOwner,
        ) { buttonClicked ->
            if (buttonClicked) {
                onButtonClicked()
                viewModel.onButtonClickedComplete()
            }
        }

        return binding.root
    }

    private fun onButtonClicked() {
        val action = InstructionsFragmentDirections.actionInstructionsToList()
        findNavController().navigate(action)
    }

    private fun setUpStatusBar() {
        val window = requireActivity().window
        window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.colorSecondaryDark)
    }
}
