package com.amalip.testapp.application.viewModels

import android.os.Parcelable
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amalip.testapp.domain.models.User
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserListViewModel : ViewModel(), Parcelable {

    @IgnoredOnParcel
    val userList = MutableLiveData(mutableListOf<UserRowViewModel>())
    @IgnoredOnParcel
    val emptyViewVisibility = MutableLiveData(View.VISIBLE)

    fun showEmpty() = emptyViewVisibility.postValue(View.VISIBLE)
    fun hideEmpty() = emptyViewVisibility.postValue(View.GONE)

    fun addUser(user: User) {
        val vm = UserRowViewModel()
        vm.setData(user)

        userList.value!!.add(vm)
    }

    fun updateUser(user: User) {
        userList.value!!.find { it.user.value!!.id == user.id }?.user?.postValue(user)
    }

    fun deleteUser(id: Int) {
        val newList = userList.value!!
        newList.removeIf { it.user.value!!.id == id }
        userList.postValue(newList)
    }

    fun deleteAll() {
        userList.postValue(mutableListOf())
    }

}