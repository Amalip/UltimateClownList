package com.amalip.testapp.application.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amalip.testapp.application.enums.FormValidation
import com.amalip.testapp.domain.models.User

class UserFormViewModel : ViewModel() {

    val user = MutableLiveData(User())
    val canChangeRelocation = MutableLiveData(true)
    val firstRun = MutableLiveData(true)
    val addButtonsVisibility = MutableLiveData(View.VISIBLE)
    val updateButtonVisibility = MutableLiveData(View.GONE)

    init {
        user.value!!.setRandomImage()
    }

    fun setFirstRun() = firstRun.postValue(false)

    fun setPhone(number: String) {
        if (number.toIntOrNull() != null)
            user.value!!.phone = number.toInt()
    }

    fun setSelectedUser(user: User) {
        addButtonsVisibility.postValue(View.GONE)
        updateButtonVisibility.postValue(View.VISIBLE)
        this.user.postValue(user.copy())
    }

    fun getRandom() {
        user.postValue(User.getRandomUser())
    }

    fun changeRelocationAutomatically() {
        canChangeRelocation.postValue(user.value!!.changeRelocationAutomatically())
    }

    fun validateForm(update: Boolean): FormValidation {
        if (!user.value!!.validateName()) return FormValidation.ERROR_NAME
        if (!user.value!!.validateAge()) return FormValidation.ERROR_AGE
        if (!user.value!!.validateEmail()) return FormValidation.ERROR_EMAIL
        if (!user.value!!.validatePhone()) return FormValidation.ERROR_PHONE
        return if (update) FormValidation.SUCCESS_UPDATED else FormValidation.SUCCESS_ADDED
    }
}