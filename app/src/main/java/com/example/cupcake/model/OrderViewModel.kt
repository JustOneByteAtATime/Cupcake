package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// 5. Inside the OrderViewModel class, add the properties that were discussed above as private val.
// 6. Change the property types to LiveData and add backing fields to the properties, so that these
// properties can be observable and UI can be updated when the source data in the view model changes.

class OrderViewModel : ViewModel()
{
    private val _quantity = MutableLiveData<Int>(0)
    val quantity: LiveData<Int> = _quantity

    private val _flavor = MutableLiveData<String>("")
    val flavor: LiveData<String> = _flavor

    private val _date = MutableLiveData<String>("")
    val date: LiveData<String> = _date

    private val _price = MutableLiveData<Double>(0.0)
    val price: LiveData<Double> = _price

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

}