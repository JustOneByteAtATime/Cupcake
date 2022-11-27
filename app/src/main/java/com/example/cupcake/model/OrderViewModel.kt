package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

// 5. Inside the OrderViewModel class, add the properties that were discussed above as private val.
// 6. Change the property types to LiveData and add backing fields to the properties, so that these
// properties can be observable and UI can be updated when the source data in the view model changes.


class OrderViewModel : ViewModel()
// Remove the initial values from the declaration of the properties in the class. Now you are using
// the init block to initialize the properties when an instance of OrderViewModel is created.
{
    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _price = MutableLiveData<Double>()
    val price: LiveData<Double> = _price

    // In OrderViewModel class, add a class property called dateOptions that's a val. Initialize it
    // using the getPickupOptions() method you just created.
    val dateOptions = getPickupOptions()

    // Add an init block to the class, and call the new method resetOrder() from it.
    init{
        resetOrder()
    }

    // 7. In OrderViewModel class, add the methods that were discussed above. Inside the methods,
// assign the argument passed in to the mutable properties.
    //8. Since these setter methods need to be called from outside the view model, leave them as
// public methods (meaning no private or other visibility modifier needed before the fun keyword).
// The default visibility modifier in Kotlin is public.

    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
    }

    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
    }

    // 4. Within the OrderViewModel class, add the following method to check if the flavor for the
// order has been set or not. You will use this method in the StartFragment class in a later step.
    fun hasNoFlavorSet(): Boolean{
        return _flavor.value.isNullOrEmpty()
    }

    // Shared ViewModel across fragments - In OrderViewModel class, add the following function called
// getPickupOptions() to create and return the list of pickup dates. Within the method, create a val
// variable called options and initialize it to mutableListOf<String>().
    private fun getPickupOptions(): List<String>
    {
        val options = mutableListOf<String>()
        // Create a formatter string using SimpleDateFormat passing pattern string "E MMM d", and
        // the locale. In the pattern string, E stands for day name in week and it parses to
        // "Tue Dec 10".
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        // Get a Calendar instance and assign it to a new variable. Make it a val. This variable
    // will contain the current date and time. Also, import java.util.Calendar.
        val calendar = Calendar.getInstance()
        // Build up a list of dates starting with the current date and the following three dates.
        // Because you'll need 4 date options, repeat this block of code 4 times. This repeat block
        // will format a date, add it to the list of date options, and then increment the calendar
        // by 1 day.
        repeat(4){
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        // Return the updated options at the end of the method.
        return options
    }

    // Within the OrderViewModel class, create a function called resetOrder(), to reset the
// MutableLiveData properties in the view model. Assign the current date value from the dateOptions
// list to _date.value.
    fun resetOrder(){
        _quantity.value = 0
        _flavor.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
    }
}