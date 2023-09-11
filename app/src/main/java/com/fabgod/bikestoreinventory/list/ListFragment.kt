package com.fabgod.bikestoreinventory.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fabgod.bikestoreinventory.R
import com.fabgod.bikestoreinventory.databinding.ListFragmentBinding

/**
 * Fragment where the list of items (bikes) is shown and
 * the action of add a new item is available
 */
class ListFragment : Fragment() {

    private lateinit var binding: ListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.list_fragment,
            container,
            false,
        )

        // Set the correct color for the status bar
        setUpStatusBar()

        binding.addButton.setOnClickListener {
            val action = ListFragmentDirections.actionListToDetails()
            findNavController().navigate(action)
        }

        binding.menuBar.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun setUpStatusBar() {
        val window = requireActivity().window
        window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.colorSecondaryDark)
    }
}
