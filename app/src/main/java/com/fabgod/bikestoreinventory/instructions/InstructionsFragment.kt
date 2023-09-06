package com.fabgod.bikestoreinventory.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fabgod.bikestoreinventory.R

/**
 * Fragment where the information needed to understand each screen functionality is shown
 */
class InstructionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.instructions_fragment, container, false)
    }
}
