package com.amalip.testapp.application.views

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amalip.testapp.R
import com.amalip.testapp.application.enums.FormValidation.*
import com.amalip.testapp.application.utils.BaseFragment
import com.amalip.testapp.application.utils.coloredShortToast
import com.amalip.testapp.application.viewInterfaces.IUserForm
import com.amalip.testapp.application.viewModels.UserFormViewModel
import com.amalip.testapp.application.viewModels.UserListViewModel
import com.amalip.testapp.databinding.UserFormFragmentBinding
import com.amalip.testapp.domain.models.User
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFormFragment : BaseFragment(), IUserForm {

    //region Injections
    private val vm: UserFormViewModel by viewModel()
    private var listVM: UserListViewModel? = null
    //endregion

    //region Lifecycle
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layout = R.layout.user_form_fragment

        listVM = requireArguments().getParcelable("listVM")

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        setAgeListener()
        setPhoneListener()
        checkFirstRun()

        (binding as UserFormFragmentBinding).vm = vm
        (binding as UserFormFragmentBinding).eh = this

        val selectedUser = requireArguments().getParcelable<User>("selectedUser")
        if (selectedUser != null)
            vm.setSelectedUser(selectedUser)
    }

    override fun onDestroyView() {
        vm.firstRun.removeObservers(this)
        listVM = null
        super.onDestroyView()
    }
    //endregion

    override fun generateRandom() = vm.getRandom()

    override fun addUser() {
        performUpdateAdd { listVM?.addUser(vm.user.value!!) }
    }

    override fun updateUser() {
        performUpdateAdd(true) { listVM?.updateUser(vm.user.value!!) }
    }

    private fun checkFirstRun() {
        vm.firstRun.observe(this, {
            if (it) {
                (binding as UserFormFragmentBinding).edtAge.setText("")
                (binding as UserFormFragmentBinding).edtPhone.setText("")
                vm.setFirstRun()
            }
        })
    }

    private fun setAgeListener() {
        (binding as UserFormFragmentBinding).edtAge.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                vm.changeRelocationAutomatically()
            }
        })
    }

    private fun setPhoneListener() {
        val form = (binding as UserFormFragmentBinding)
        form.edtPhone.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                vm.setPhone(form.edtPhone.getText(true).toString())
            }
        })
    }

    private fun performUpdateAdd(update: Boolean = false, function: () -> Unit) {
        val form = (binding as UserFormFragmentBinding)
        if (form.edtName.text.isEmpty() || form.edtAge.text.isEmpty() || form.edtEmail.text.isEmpty() || form.edtPhone.text.isEmpty()) {
            requireContext().coloredShortToast(ERROR_FORM.message, R.color.red, R.color.white)
            return
        }

        vm.setPhone(form.edtPhone.getText(true).toString())

        when(val res = vm.validateForm(update)) {
            SUCCESS_ADDED, SUCCESS_UPDATED -> {
                function.invoke()
                requireContext().coloredShortToast(res.message, R.color.green, R.color.white)
                navController!!.popBackStack()
            }
            else -> requireContext().coloredShortToast(res.message, R.color.red, R.color.white)
        }
    }
}