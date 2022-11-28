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

import android.content.Intent
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
    // Send the order - In SummaryFragment.kt modify the sendOrder() method. Remove the existing
    // Toast message.
    // Within the sendOrder() method, construct the order summary text. Create the formatted
    // order_details string by getting the order quantity, flavor, date, and price from the shared
    // view model.

    // Send the order - 8. In the SummaryFragment class, update your sendOrder() method to use the
    // new quantity string. It would be easiest to first figure out the quantity from the view model
    // and store that in a variable. Since quantity in the view model is of type LiveData<Int>, it's
    // possible that sharedViewModel.quantity.value is null. If it is null, then use 0 as the
    // default value for numberOfCupcakes.

    fun sendOrder() {
        val numberOfCupcakes = sharedViewModel.quantity.value ?: 0

        val orderSummary = getString(
            // 9. Then format the order_details string as you did before. Instead of passing in
            // numberOfCupcakes as the quantity argument directly, create the formatted cupcakes
            // string with resources.getQuantityString(R.plurals.cupcakes, numberOfCupcakes,
            // numberOfCupcakes).

            R.string.order_details,
            resources.getQuantityString(R.plurals.cupcakes, numberOfCupcakes, numberOfCupcakes),
            sharedViewModel.flavor.value.toString(),
            sharedViewModel.date.value.toString(),
            sharedViewModel.price.value.toString()
        )


        // Still within the sendOrder() method, create an implicit intent for sharing the order to
    // another app. See the documentation for how to create an email intent.
    // Specify Intent.ACTION_SEND for the intent action, set type to "text/plain" and include intent
    // extras for the email subject (Intent.EXTRA_SUBJECT) and email body (Intent.EXTRA_TEXT).
    // Import android.content.Intent if needed.
        val intent = Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.new_cupcake_order))
            .putExtra(Intent.EXTRA_TEXT, orderSummary)
// As a bonus tip, if you adapt this app to your own use case, you could pre-populate the recipient
    // of the email to be the email address of the cupcake shop. In the intent, you would specify
    // the email recipient with intent extra Intent.EXTRA_EMAIL.

        // Since this is an implicit intent, you don't need to know ahead of time which specific
    // component or app will handle this intent. The user will decide which app they want to use to
    // fulfill the intent. However, before launching an activity with this intent, check to see if
    // there's an app that could even handle it. This check will prevent the Cupcake app from
    // crashing if there's no app to handle the intent, making your code safer.

        // Perform this check by accessing the PackageManager, which has information about what app
        // packages are installed on the device. The PackageManager can be accessed via the fragment's
        // activity, as long as the activity and packageManager are not null. Call the PackageManager's
        // resolveActivity() method with the intent you created. If the result is not null, then it is
        // safe to call startActivity() with your intent.
        if (activity?.packageManager?.resolveActivity(intent, 0) != null) {
            // Start a new activity with the given intent (this may open the share dialog on a
            // device if multiple apps can handle this intent)
            startActivity(intent)
        }
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