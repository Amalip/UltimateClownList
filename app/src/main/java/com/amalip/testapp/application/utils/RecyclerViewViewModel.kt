package com.amalip.testapp.application.utils

import androidx.lifecycle.ViewModel

abstract class RecyclerViewViewModel : ViewModel() {

    var eventHandler: RecyclerViewEventHandler? = null

}

interface RecyclerViewEventHandler