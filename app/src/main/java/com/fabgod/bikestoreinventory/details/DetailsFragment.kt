package com.fabgod.bikestoreinventory.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fabgod.bikestoreinventory.R
import com.fabgod.bikestoreinventory.databinding.DetailsFragmentBinding
import com.fabgod.bikestoreinventory.list.ListFragment
import com.fabgod.bikestoreinventory.list.model.Bike
import com.fabgod.bikestoreinventory.list.model.Bikes
import com.fabgod.bikestoreinventory.utils.SharedPreferencesInstance
import com.fabgod.bikestoreinventory.utils.getRandomBikeImageResource
import com.fabgod.bikestoreinventory.utils.toBalanceFormat
import com.google.android.material.textfield.TextInputLayout

/**
 * Fragment where the specific bike information is shown
 */
class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: DetailsFragmentBinding
    private lateinit var session: SharedPreferencesInstance
    private lateinit var viewModelFactory: DetailsViewModelFactory
    private val bikeImageToAdd = getRandomBikeImageResource()

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

        // Get SharedPreferencesInstance class to save and check session
        session = SharedPreferencesInstance(requireActivity())

        val detailsFragmentArgs by navArgs<DetailsFragmentArgs>()

        viewModelFactory = DetailsViewModelFactory(
            detailsFragmentArgs.mode,
            detailsFragmentArgs.bikeList ?: Bikes(),
            detailsFragmentArgs.bike ?: Bike(),
        )
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailsViewModel::class.java]

        binding.menuBar.backArrow.visibility = View.VISIBLE
        binding.addBikeSection.detailsViewModel = viewModel
        binding.addBikeSection.lifecycleOwner = this

        binding.menuBar.backArrow.setOnClickListener {
            onBackAction()
        }
        binding.menuBar.profileImage.setOnClickListener {
            viewModel.onLogOut()
        }

        viewModel.mode.observe(
            viewLifecycleOwner,
        ) { mode ->
            showSection(mode, detailsFragmentArgs.bike ?: Bike())
        }

        viewModel.eventLogOut.observe(
            viewLifecycleOwner,
        ) { logOut ->
            if (logOut) {
                onLogOut()
                viewModel.onLogOutComplete()
            }
        }

        setTextChangedValidations()

        return binding.root
    }

    private fun setUpObservers() {
        viewModel.eventBikeAdded.observe(
            viewLifecycleOwner,
        ) { bikeAdded ->
            if (bikeAdded) {
                checkValidations()
            }
        }

        viewModel.isFormValid.observe(
            viewLifecycleOwner,
        ) { isFormValid ->
            if (isFormValid) {
                viewModel.addBikeToList(getBikeDataToAdd())
                onBikeAdded()
                viewModel.onBikeAddedComplete()
            }
        }

        viewModel.modelValidationState.observe(
            viewLifecycleOwner,
        ) { state ->
            if (state is DetailsViewModel.FieldState.Empty) {
                showTextInputError(binding.addBikeSection.modelEditLayout)
            } else {
                hideTextInputError(binding.addBikeSection.modelEditLayout)
            }
        }

        viewModel.wheelSizeValidationState.observe(
            viewLifecycleOwner,
        ) { state ->
            if (state is DetailsViewModel.FieldState.Empty) {
                showTextInputError(binding.addBikeSection.wheelSizeEditLayout)
            } else {
                hideTextInputError(binding.addBikeSection.wheelSizeEditLayout)
            }
        }

        viewModel.colorValidationState.observe(
            viewLifecycleOwner,
        ) { state ->
            if (state is DetailsViewModel.FieldState.Empty) {
                showTextInputError(binding.addBikeSection.colorEditLayout)
            } else {
                hideTextInputError(binding.addBikeSection.colorEditLayout)
            }
        }

        viewModel.sizeValidationState.observe(
            viewLifecycleOwner,
        ) { state ->
            if (state is DetailsViewModel.FieldState.Empty) {
                showTextInputError(binding.addBikeSection.sizeEditLayout)
            } else {
                hideTextInputError(binding.addBikeSection.sizeEditLayout)
            }
        }

        viewModel.priceValidationState.observe(
            viewLifecycleOwner,
        ) { state ->
            if (state is DetailsViewModel.FieldState.Empty) {
                showTextInputError(binding.addBikeSection.priceEditLayout)
            } else {
                hideTextInputError(binding.addBikeSection.priceEditLayout)
            }
        }
    }

    private fun populateViews(bike: Bike) {
        binding.detailsSection.apply {
            modelInformation.text = bike.model
            wheelSizeInformation.text = bike.wheelSize
            colorInformation.text = bike.color
            sizeInformation.text = bike.size
            priceInformation.text =
                getString(R.string.details_price_format, bike.price.toBalanceFormat())
            bikeImage.setImageResource(bike.imageResource)
        }
    }

    private fun onModelValidationTextChanged() {
        binding.addBikeSection.modelEditLayout.editText?.doOnTextChanged { _, _, _, _ ->
            onModelValidation()
        }
    }

    private fun onModelValidation() {
        viewModel.validateModel(binding.addBikeSection.modelEditLayout.editText?.text.toString())
    }

    private fun onWheelSizeValidationTextChanged() {
        binding.addBikeSection.wheelSizeEditLayout.editText?.doOnTextChanged { _, _, _, _ ->
            onWheelSizeValidation()
        }
    }

    private fun onWheelSizeValidation() {
        viewModel.validateWheelSize(binding.addBikeSection.wheelSizeEditLayout.editText?.text.toString())
    }

    private fun onColorValidationTextChanged() {
        binding.addBikeSection.colorEditLayout.editText?.doOnTextChanged { _, _, _, _ ->
            onColorValidation()
        }
    }

    private fun onColorValidation() {
        viewModel.validateColor(binding.addBikeSection.colorEditLayout.editText?.text.toString())
    }

    private fun onSizeValidationTextChanged() {
        binding.addBikeSection.sizeEditLayout.editText?.doOnTextChanged { _, _, _, _ ->
            onSizeValidation()
        }
    }

    private fun onSizeValidation() {
        viewModel.validateSize(binding.addBikeSection.sizeEditLayout.editText?.text.toString())
    }

    private fun onPriceValidationTextChanged() {
        binding.addBikeSection.priceEditLayout.editText?.doOnTextChanged { _, _, _, _ ->
            onPriceValidation()
        }
    }

    private fun onPriceValidation() {
        viewModel.validatePrice(binding.addBikeSection.priceEditLayout.editText?.text.toString())
    }

    private fun setTextChangedValidations() {
        onModelValidationTextChanged()
        onWheelSizeValidationTextChanged()
        onColorValidationTextChanged()
        onSizeValidationTextChanged()
        onPriceValidationTextChanged()
    }

    private fun getBikeDataToAdd(): Bike {
        binding.addBikeSection.apply {
            return Bike(
                model = modelEditLayout.editText?.text.toString(),
                wheelSize = wheelSizeEditLayout.editText?.text.toString(),
                color = colorEditLayout.editText?.text.toString(),
                size = sizeEditLayout.editText?.text.toString(),
                price = priceEditLayout.editText?.text.toString(),
                imageResource = bikeImageToAdd,
            )
        }
    }

    private fun showTextInputError(view: TextInputLayout) {
        view.error = getString(R.string.field_error_message)
    }

    private fun hideTextInputError(view: TextInputLayout) {
        view.error = null
    }

    private fun showSection(mode: Int, bike: Bike) {
        if (mode == ListFragment.SEE_DETAILS_MODE) {
            binding.detailsSection.detailsSectionLayout.visibility = View.VISIBLE
            populateViews(bike)
        } else {
            binding.addBikeSection.addBikeSectionLayout.visibility = View.VISIBLE
            binding.addBikeSection.bikeImage.setImageResource(bikeImageToAdd)
            setUpObservers()
        }
    }

    private fun checkValidations() {
        onModelValidation()
        onWheelSizeValidation()
        onColorValidation()
        onSizeValidation()
        onPriceValidation()
        viewModel.validateForm()
    }

    private fun onLogOut() {
        session.deleteSession()
        val action = DetailsFragmentDirections.actionDetailsToLogin()
        findNavController().navigate(action)
    }

    private fun onBikeAdded() {
        val action = DetailsFragmentDirections.actionDetailsToList(viewModel.list.value)
        findNavController().navigate(action)
    }

    private fun onBackAction() {
        findNavController().popBackStack()
    }

    private fun setUpStatusBar() {
        val window = requireActivity().window
        window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.colorSecondaryDark)
    }
}
