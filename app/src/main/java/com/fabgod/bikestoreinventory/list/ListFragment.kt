package com.fabgod.bikestoreinventory.list

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fabgod.bikestoreinventory.R
import com.fabgod.bikestoreinventory.databinding.ListFragmentBinding
import com.fabgod.bikestoreinventory.list.model.Bike
import com.fabgod.bikestoreinventory.list.model.Bikes
import com.fabgod.bikestoreinventory.utils.dummyBikeList
import com.google.android.material.card.MaterialCardView

/**
 * Fragment where the list of items (bikes) is shown and
 * the action of add a new item is available
 */
class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var binding: ListFragmentBinding
    private lateinit var viewModelFactory: ListViewModelFactory
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpList() {
        for (bike in viewModel.list.value?.bikes ?: mutableListOf()) {
            val constraintLayout = createBikeItem(bike)
            binding.bikesLayout.addView(constraintLayout)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createBikeItem(bike: Bike): ConstraintLayout {
        // Create a new ConstraintLayout
        val constraintLayout = ConstraintLayout(requireContext())
        constraintLayout.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
        )
        constraintLayout.setBackgroundResource(R.drawable.bike_item_background_selector)
        constraintLayout.tooltipText = getString(R.string.list_bike_selected_tooltip_text)
        constraintLayout.setOnClickListener {
            viewModel.saveBikeSelected(bike)
            viewModel.onBikeSelected()
        }

        // Create Views
        val modelTextView = TextView(requireContext())
        modelTextView.id = View.generateViewId()
        modelTextView.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
        )
        modelTextView.text = getString(R.string.list_model_format, bike.model)
        modelTextView.setTextAppearance(R.style.ListItemTitleStyle)

        val colorTextView = TextView(requireContext())
        colorTextView.id = View.generateViewId()
        colorTextView.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
        )
        colorTextView.text = getString(R.string.list_color_format, bike.color)
        modelTextView.setTextAppearance(R.style.ListItemSubtitleStyle)

        val priceTextView = TextView(requireContext())
        priceTextView.id = View.generateViewId()
        priceTextView.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
        )
        priceTextView.text = getString(R.string.list_price_format, bike.price)
        modelTextView.setTextAppearance(R.style.ListItemSubtitleStyle)

        val bikeImageContainer = MaterialCardView(requireContext())
        bikeImageContainer.id = View.generateViewId()
        bikeImageContainer.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
        )
        bikeImageContainer.cardElevation = 16f
        bikeImageContainer.radius = 300f

        val bikeImageView = ImageView(requireContext())
        bikeImageView.id = View.generateViewId()
        bikeImageView.layoutParams = ConstraintLayout.LayoutParams(380, 380)
        bikeImageView.setImageResource(bike.imageResource)

        // Add TextViews to the ConstraintLayout
        constraintLayout.addView(modelTextView)
        constraintLayout.addView(colorTextView)
        constraintLayout.addView(priceTextView)
        constraintLayout.addView(bikeImageContainer)
        bikeImageContainer.addView(bikeImageView)

        modelTextView.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
        colorTextView.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
        priceTextView.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START

        bikeImageView.scaleType = ImageView.ScaleType.CENTER_CROP

        // Set constraints using ConstraintSet
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        // Add constraints for Views
        constraintSet.connect(bikeImageContainer.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 100)
        constraintSet.connect(bikeImageContainer.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 100)
        constraintSet.connect(bikeImageContainer.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 100)

        constraintSet.connect(modelTextView.id, ConstraintSet.BOTTOM, colorTextView.id, ConstraintSet.TOP)
        constraintSet.connect(modelTextView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 16)
        constraintSet.connect(modelTextView.id, ConstraintSet.START, bikeImageContainer.id, ConstraintSet.END, 100)
        constraintSet.connect(modelTextView.id, ConstraintSet.TOP, bikeImageContainer.id, ConstraintSet.TOP)

        constraintSet.connect(colorTextView.id, ConstraintSet.BOTTOM, priceTextView.id, ConstraintSet.TOP)
        constraintSet.connect(colorTextView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 16)
        constraintSet.connect(colorTextView.id, ConstraintSet.START, bikeImageContainer.id, ConstraintSet.END, 100)
        constraintSet.connect(colorTextView.id, ConstraintSet.TOP, modelTextView.id, ConstraintSet.BOTTOM)

        constraintSet.connect(priceTextView.id, ConstraintSet.BOTTOM, bikeImageContainer.id, ConstraintSet.BOTTOM)
        constraintSet.connect(priceTextView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 16)
        constraintSet.connect(priceTextView.id, ConstraintSet.START, bikeImageContainer.id, ConstraintSet.END, 100)
        constraintSet.connect(priceTextView.id, ConstraintSet.TOP, colorTextView.id, ConstraintSet.BOTTOM)

        constraintSet.applyTo(constraintLayout)

        return constraintLayout
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
