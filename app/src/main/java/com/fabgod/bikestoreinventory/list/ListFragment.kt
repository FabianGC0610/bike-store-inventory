package com.fabgod.bikestoreinventory.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabgod.bikestoreinventory.R
import com.fabgod.bikestoreinventory.databinding.ListFragmentBinding
import com.fabgod.bikestoreinventory.list.adapter.ListAdapter
import com.fabgod.bikestoreinventory.list.model.Bike
import com.fabgod.bikestoreinventory.list.model.Bikes
import com.fabgod.bikestoreinventory.utils.dummyBikeList

/**
 * Fragment where the list of items (bikes) is shown and
 * the action of add a new item is available
 */
class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var binding: ListFragmentBinding
    private lateinit var viewModelFactory: ListViewModelFactory
    private lateinit var listAdapter: ListAdapter
    private var bikeList = dummyBikeList

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

        viewModelFactory = ListViewModelFactory(R.id.drawerLayout)
        viewModel = ViewModelProvider(this, viewModelFactory)[ListViewModel::class.java]

        binding.listViewModel = viewModel
        binding.lifecycleOwner = this
        binding.menuBar.listViewModel = viewModel
        binding.menuBar.lifecycleOwner = this

        binding.menuBar.menuIconImageContainer.visibility = View.VISIBLE

        getBikeList()

        viewModel.eventBikeSelected.observe(
            viewLifecycleOwner,
        ) { bikeSelected ->
            if (bikeSelected) {
                navigateToDetails(
                    SEE_DETAILS_MODE,
                    viewModel.bikeSelected.value,
                )
                viewModel.onBikeSelectedComplete()
            }
        }

        viewModel.eventAddBike.observe(
            viewLifecycleOwner,
        ) { addBike ->
            if (addBike) {
                navigateToDetails(
                    ADD_NEW_BIKE_MODE,
                    null,
                )
                viewModel.onAddBikeComplete()
            }
        }

        viewModel.eventOpenMenu.observe(
            viewLifecycleOwner,
        ) { openMenu ->
            if (openMenu) {
                onOpenMenu()
                viewModel.onOpenMenuComplete()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpList(view)
    }

    private fun setUpList(view: View) {
        listAdapter = ListAdapter(requireContext(), viewModel.list.value?.bikes ?: mutableListOf()) { bike ->
            viewModel.saveBikeSelected(bike)
            viewModel.onBikeSelected()
        }
        binding.bikesList.layoutManager = LinearLayoutManager(view.context)
        binding.bikesList.adapter = listAdapter
    }

    private fun getBikeList() {
        val listFragmentArgs by navArgs<ListFragmentArgs>()
        if (listFragmentArgs.bikeList == null) {
            viewModel.saveList(Bikes(bikeList))
        } else {
            viewModel.saveList(Bikes(listFragmentArgs.bikeList?.bikes ?: mutableListOf()))
        }
    }

    private fun onOpenMenu() {
        val drawerLayout =
            requireActivity().findViewById<DrawerLayout>(viewModel.drawerLayoutId.value ?: 0)
        drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun navigateToDetails(mode: Int, bike: Bike?) {
        val action = ListFragmentDirections.actionListToDetails(mode, bike, viewModel.list.value)
        findNavController().navigate(action)
    }

    private fun setUpStatusBar() {
        val window = requireActivity().window
        window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.colorSecondaryDark)
    }

    companion object {
        const val SEE_DETAILS_MODE = 0
        const val ADD_NEW_BIKE_MODE = 1
    }
}
