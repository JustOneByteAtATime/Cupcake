package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*
// You'll need to import androidx.lifecycle.Transformations and java.text.NumberFormat.
import java.text.NumberFormat

// 5. Inside the OrderViewModel class, add the properties that were discussed above as private val.
// 6. Change the property types to LiveData and add backing fields to the properties, so that these
// properties can be observable and UI can be updated when the source data in the view model changes.

// Calculate Price from order details - 1. Open OrderViewModel.kt, and store the price per cupcake
// in a variable. Declare it as a top-level private constant at the top of the file, outside the
// class definition (but after the import statements). Use the const modifier and to make it
// read-only use val.
private const val PRICE_PER_CUPCAKE = 2.00

// Calculate Price from order details - Charge Extra for SD pickup - 1. In OrderViewModel class,
// define a new top-level private constant for the same day pickup cost.
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

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

    // Calc price from order details - Format Price with LiveData Transformation
    // 1. In OrderViewModel class, change the backing property type to LiveData<String> instead of
    // LiveData<Double>. The formatted price will be a string with a currency symbol such as a ‘$'.
    // You will fix the initialization error in the next step.
    // 2. Use Transformations.map() to initialize the new variable, pass in the _price and a lambda
    // function. Use getCurrencyInstance() method in the NumberFormat class to convert the price to
    // local currency format. The transformation code will look like this.
    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance().format(it)
    }


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
        // In the same OrderViewModel class, update the price variable when the quantity is set.
    // Make a call to the new function in the setQuantity() function.
        updatePrice()
    }

    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    // Charge Extra for SD pickup - 4. Call updatePrice() helper method from setDate() method to add
    // the same day pickup charges.
    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
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

    // Now that you have defined a price per cupcake, create a helper method to calculate the price.
// This method can be private because it's only used within this class. You will change the price
// logic to include same day pickup charges in the next task.

    // Calculate Price from order details -
    // Charge Extra for SD pickup - 2. In updatePrice(),
    // check if the user selected the same day pickup. Check if the date in the view model
    // (_date.value) is the same as the first item in the dateOptions list which is always the
    // current day.

    // Charge Extra for SD pickup - 3. To make these calculations simpler, introduce a temporary
    // variable, calculatedPrice. Calculate the updated price and assign it back to _price.value.
    private fun updatePrice() {
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_CUPCAKE
                // If the user selected the first option (today) for pickup, add the surcharge
        if (dateOptions[0] == _date.value)
        {
        calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }
    // ?: is an ELVIS OPERATOR: since the value of quantity.value could be null, use an elvis
// operator (?:) . The elvis operator (?:) means that if the expression on the left is not null,
// then use it. Otherwise if the expression on the left is null, then use the expression to the
// right of the elvis operator (which is 0 in this case). Fun fact: Elvis operator (?:) is named
// after the rock star, Elvis Presley, because when you view it sideways, it resembles an emoticon
// of Elvis Presley with his quiff.
}