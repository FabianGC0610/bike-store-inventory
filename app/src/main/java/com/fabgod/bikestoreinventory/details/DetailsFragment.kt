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
import com.fabgod.bikestoreinventory.MainActivityViewModel
import com.fabgod.bikestoreinventory.R
import com.fabgod.bikestoreinventory.databinding.DetailsFragmentBinding
import com.fabgod.bikestoreinventory.list.ListFragment
import com.fabgod.bikestoreinventory.list.model.Bike
import com.fabgod.bikestoreinventory.utils.getRandomBikeImageResource
import com.fabgod.bikestoreinventory.utils.toBalanceFormat
import com.google.android.material.textfield.TextInputLayout

/**
 * Fragment where the specific bike information is shown
 */
class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var activityViewModel: MainActivityViewModel
    private lateinit var binding: DetailsFragmentBinding
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

        val detailsFragmentArgs by navArgs<DetailsFragmentArgs>()

        activityViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        viewModelFactory = DetailsViewModelFactory(
            detailsFragmentArgs.mode,
            detailsFragmentArgs.bike ?: Bike(),
        )
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailsViewModel::class.java]

        binding.menuBar.backArrowImageContainer.visibility = View.VISIBLE

        setUpBindingWithViewModel()

        viewModel.mode.observe(
            viewLifecycleOwner,
        ) { mode ->
            showSection(mode)
        }

        viewModel.eventBackAction.observe(
            viewLifecycleOwner,
        ) { backAction ->
            if (backAction) {
                onBackAction()
                viewModel.onBackActionComplete()
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
                activityViewModel.addBikeToList(getBikeDataToAdd())
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

    private fun setUpBindingWithViewModel() {
        binding.addBikeSection.detailsViewModel = viewModel
        binding.addBikeSection.lifecycleOwner = this
        binding.detailsSection.detailsViewModel = viewModel
        binding.detailsSection.lifecycleOwner = this
        binding.menuBar.detailsViewModel = viewModel
        binding.menuBar.lifecycleOwner = this
    }

    private fun onModelValidationTextChanged() {
        binding.addBikeSection.modelEditLayout.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.updateModel(text.toString())
            onModelValidation()
        }
    }

    private fun onModelValidation() {
        viewModel.validateModel(binding.addBikeSection.modelEditLayout.editText?.text.toString())
    }

    private fun onWheelSizeValidationTextChanged() {
        binding.addBikeSection.wheelSizeEditLayout.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.updateWheelSize(text.toString())
            onWheelSizeValidation()
        }
    }

    private fun onWheelSizeValidation() {
        viewModel.validateWheelSize(binding.addBikeSection.wheelSizeEditLayout.editText?.text.toString())
    }

    private fun onColorValidationTextChanged() {
        binding.addBikeSection.colorEditLayout.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.updateColor(text.toString())
            onColorValidation()
        }
    }

    private fun onColorValidation() {
        viewModel.validateColor(binding.addBikeSection.colorEditLayout.editText?.text.toString())
    }

    private fun onSizeValidationTextChanged() {
        binding.addBikeSection.sizeEditLayout.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.updateSize(text.toString())
            onSizeValidation()
        }
    }

    private fun onSizeValidation() {
        viewModel.validateSize(binding.addBikeSection.sizeEditLayout.editText?.text.toString())
    }

    private fun onPriceValidationTextChanged() {
        binding.addBikeSection.priceEditLayout.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.updatePrice(text.toString())
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
                price = priceEditLayout.editText?.text.toString().toBalanceFormat(),
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

    private fun showSection(mode: Int) {
        if (mode == ListFragment.SEE_DETAILS_MODE) {
            binding.detailsSection.detailsSectionLayout.visibility = View.VISIBLE
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

    private fun onBikeAdded() {
        val action = DetailsFragmentDirections.actionDetailsToList()
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
