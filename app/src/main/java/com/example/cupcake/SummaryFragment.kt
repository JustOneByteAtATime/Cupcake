/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cupcake.databinding.FragmentSummaryBinding
import com.example.cupcake.model.OrderViewModel
import androidx.navigation.fragment.findNavController

/**
 * [SummaryFragment] contains a summary of the order details with a button to share the order
 * via another app.
 */
class SummaryFragment : Fragment() {

    // Binding object instance corresponding to the fragment_summary.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentSummaryBinding? = null

    // 2. In CURRENT class, get a reference to the shared view model as a class variable. Use the by
    // activityViewModels() Kotlin property delegate from the fragment-ktx library.
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentSummaryBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }
// In the CURRENT class, inside onViewCreated(), bind the view model instance with the shared view
// model instance in the layout. Add the following code inside the binding?.apply block

    // 1. In the FlavorFragment, PickupFragment, SummaryFragment classes, inside the onViewCreated()
    // method, add the following in the binding?.apply block. This will set the lifecycle owner on
    // the binding object. By setting the lifecycle owner, the app will be able to observe LiveData
    // objects. lifecycleOwner = viewLifecycleOwner
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            // In SummaryFragment, in onViewCreated(), make sure binding.viewModel is initialized.
            // CHECK!
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            summaryFragment = this@SummaryFragment
//            sendButton.setOnClickListener { sendOrder() }
        }
    }

    /**
     * Submit the order by sharing out the order details to another app via an implicit intent.
     */
    fun sendOrder() {
        Toast.makeText(activity, "Send Order", Toast.LENGTH_SHORT).show()
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_summaryFragment_to_startFragment)
    }

}