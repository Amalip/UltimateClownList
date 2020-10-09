package com.amalip.testapp.application.viewModels

import androidx.lifecycle.MutableLiveData
import com.amalip.testapp.application.utils.RecyclerViewViewModel
import com.amalip.testapp.domain.models.User

class UserRowViewModel: RecyclerViewViewModel() {

    val user = MutableLiveData<User>()

    fun setData(user: User) = this.user.postValue(user)

}