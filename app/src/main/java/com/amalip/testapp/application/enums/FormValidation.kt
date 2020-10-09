package com.amalip.testapp.application.enums

import com.amalip.testapp.R

enum class FormValidation(val message: Int) {

    SUCCESS_ADDED(R.string.success),
    SUCCESS_UPDATED(R.string.update),
    ERROR_NAME(R.string.errorName),
    ERROR_AGE(R.string.errorAge),
    ERROR_PHONE(R.string.errorPhone),
    ERROR_EMAIL(R.string.errorEmail),
    ERROR_FORM(R.string.errorForm)

}