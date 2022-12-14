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
import com.example.cupcake.databinding.FragmentStartBinding
import androidx.navigation.fragment.findNavController
import com.example.cupcake.model.OrderViewModel

/**
 * This is the first screen of the Cupcake app. The user can choose how many cupcakes to order.
 */

// To use the shared view model in StartFragment you will initialize the OrderViewModel using
// activityViewModels() instead of viewModels() delegate class.
//
//    viewModels() gives you the ViewModel instance scoped to the current fragment. This will be
//    different for different fragments.
//    activityViewModels() gives you the ViewModel instance scoped to the current activity.
//    Therefore the instance will remain the same across multiple fragments in the same activity.

class StartFragment : Fragment() {

    // Binding object instance corresponding to the fragment_start.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentStartBinding? = null

    // 1. In StartFragment class, get a reference to the shared view model as a class variable. Use the
    // by activityViewModels() Kotlin property delegate from the fragment-ktx library.
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    // Setup click listeners..
    // 2. In StartFragment.kt, in onViewCreated() method, bind the new data variable to the fragment
    // instance. You can access the fragment instance inside the fragment using this keyword.
    // Remove the binding?.apply block and along with the code within. The completed method should
    // look like this.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.startFragment = this

//        binding?.apply {
//            // Set up the button click listeners
//            orderOneCupcake.setOnClickListener { orderCupcake(1) }
//            orderSixCupcakes.setOnClickListener { orderCupcake(6) }
//            orderTwelveCupcakes.setOnClickListener { orderCupcake(12) }
//        }
    }

    /**
     * Start an order with the desired quantity of cupcakes and navigate to the next screen.
     */
    // Going back to the StartFragment class, you can now use the view model. At the beginning of
    // the orderCupcake() method, call the setQuantity()method in the shared view model to update
    // quantity, before navigating to the flavor fragment.

    // 5. In StartFragment class, inside orderCupcake() method, after setting the quantity, set the
    // default flavor as Vanilla if no flavor is set, before navigating to the flavor fragment.
    // Your complete method will look like this:

    fun orderCupcake(quantity: Int)
    {
        sharedViewModel.setQuantity(quantity)
        if (sharedViewModel.hasNoFlavorSet())
        {
            sharedViewModel.setFlavor(getString(R.string.vanilla))
        }
        findNavController().navigate(R.id.action_startFragment_to_flavorFragment)
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}