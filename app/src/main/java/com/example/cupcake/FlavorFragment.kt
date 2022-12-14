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
import com.example.cupcake.databinding.FragmentFlavorBinding
// Remember to import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.cupcake.model.OrderViewModel

/**
 * [FlavorFragment] allows a user to choose a cupcake flavor for the order.
 */
class FlavorFragment : Fragment() {

    // Binding object instance corresponding to the fragment_flavor.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentFlavorBinding? = null

    // 2. In CURRENT class, get a reference to the shared view model as a class variable. Use the by
    // activityViewModels() Kotlin property delegate from the fragment-ktx library.
    private val sharedViewModel: OrderViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentFlavorBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    // 3. In the FlavorFragment class, inside onViewCreated(), bind the view model instance with the
    // shared view model instance in the layout. Add the following code inside the binding?.apply
    // block.

    // Calc Price fr Order Details - Set Lifecycle owner to observe LiveData -
    // 1. In the FlavorFragment, PickupFragment, SummaryFragment classes, inside the onViewCreated()
    // method, add the following in the binding?.apply block. This will set the lifecycle owner on
    // the binding object. By setting the lifecycle owner, the app will be able to observe LiveData
    // objects. lifecycleOwner = viewLifecycleOwner
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//    In the rest of the fragment classes, in onViewCreated() methods, delete the code that manually
//    sets the click listener on the buttons.
//    In the onViewCreated() methods bind the fragment data variable with the fragment instance. You
//    will use this keyword differently here, because inside the binding?.apply block, the keyword
//    this refers to the binding instance, not the fragment instance. Use @ and explicitly specify
//    the fragment class name, for example this@FlavorFragment. The completed onViewCreated()
//    methods should look as follows:

        binding?.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            flavorFragment = this@FlavorFragment
//            nextButton.setOnClickListener { goToNextScreen() }
        }
    }

    /**
     * Navigate to the next screen to choose pickup date.
     */
    fun goToNextScreen() {
        // In FlavorFragment.kt, inside the goToNextScreen() method, replace the code displaying the
        // toast to navigate to the pickup fragment. Use the action ID,
        // R.id.action_flavorFragment_to_pickupFragment and make sure this ID matches the action
        // declared in your nav_graph.xml.
        findNavController().navigate(R.id.action_flavorFragment_to_pickupFragment)
    }


    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    // Add this cancelOrder() method to FlavorFragment. When presented with the flavor options, if
    // the user decides to cancel their order, then clear out the view model by calling
    // sharedViewModel.resetOrder(). Then navigate back to the StartFragment using the navigation
    // action with ID R.id.action_flavorFragment_to_startFragment.

    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_flavorFragment_to_startFragment)
    }

}