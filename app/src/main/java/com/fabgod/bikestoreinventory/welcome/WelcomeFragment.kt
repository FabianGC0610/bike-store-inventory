package com.fabgod.bikestoreinventory.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fabgod.bikestoreinventory.R
import com.fabgod.bikestoreinventory.databinding.WelcomeFragmentBinding

/**
 * Fragment where the user gets a message of first time in the app
 */
class WelcomeFragment : Fragment() {

    private lateinit var binding: WelcomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.welcome_fragment,
            container,
            false,
        )

        // Set the correct color for the status bar
        setUpStatusBar()

        binding.continueButton.setOnClickListener {
            val action = WelcomeFragmentDirections.actionWelcomeToInstructions()
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun setUpStatusBar() {
        val window = requireActivity().window
        window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.colorPrimaryDark)
    }
}
