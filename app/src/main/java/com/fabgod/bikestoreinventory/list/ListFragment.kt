package com.fabgod.bikestoreinventory.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabgod.bikestoreinventory.R
import com.fabgod.bikestoreinventory.databinding.ListFragmentBinding
import com.fabgod.bikestoreinventory.list.adapter.ListAdapter
import com.fabgod.bikestoreinventory.utils.dummyBikeList

/**
 * Fragment where the list of items (bikes) is shown and
 * the action of add a new item is available
 */
class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var binding: ListFragmentBinding
    private lateinit var listAdapter: ListAdapter

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

        viewModel = ViewModelProvider(this)[ListViewModel::class.java]

        binding.listViewModel = viewModel
        binding.lifecycleOwner = this

        binding.addButton.setOnClickListener {
            val action = ListFragmentDirections.actionListToDetails()
            findNavController().navigate(action)
        }

        binding.menuBar.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpList(view)
    }

    private fun navigateToDetails() {
        val action = ListFragmentDirections.actionListToDetails()
        findNavController().navigate(action)
    }

    private fun setUpList(view: View) {
        listAdapter = ListAdapter(requireContext(), dummyBikeList) {
            navigateToDetails()
        }
        binding.bikesList.layoutManager = LinearLayoutManager(view.context)
        binding.bikesList.adapter = listAdapter
    }

    private fun setUpStatusBar() {
        val window = requireActivity().window
        window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.colorSecondaryDark)
    }
}
