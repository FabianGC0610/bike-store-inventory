package com.fabgod.bikestoreinventory.login

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
import com.fabgod.bikestoreinventory.databinding.LoginFragmentBinding
import com.fabgod.bikestoreinventory.utils.SharedPreferencesInstance

/**
 * Fragment where the user can set his credentials (log in or create account)
 */
class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory
    private lateinit var binding: LoginFragmentBinding
    private lateinit var session: SharedPreferencesInstance

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.login_fragment,
            container,
            false,
        )

        // Set the correct color for the status bar
        setUpStatusBar()

        // Get SharedPreferencesInstance class to save and check session
        session = SharedPreferencesInstance(requireActivity())

        viewModelFactory = LoginViewModelFactory(session.getSession())
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

        binding.loginViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.isSessionSaved.observe(
            viewLifecycleOwner,
        ) { isSessionSaved ->
            if (isSessionSaved) {
                navigateToListScreen()
            }
        }

        viewModel.eventSessionSaved.observe(
            viewLifecycleOwner,
        ) { logIn ->
            if (logIn) {
                onLogIn()
                viewModel.onLogInCompleteComplete()
            }
        }

        return binding.root
    }

    private fun onLogIn() {
        session.saveSession()
        val action = LoginFragmentDirections.actionLoginToWelcome()
        findNavController().navigate(action)
    }

    private fun navigateToListScreen() {
        val action = LoginFragmentDirections.actionLoginToList()
        findNavController().navigate(action)
    }

    private fun setUpStatusBar() {
        val window = requireActivity().window
        window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.colorPrimary)
    }
}
