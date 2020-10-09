package com.amalip.testapp.application.viewInterfaces

import com.amalip.testapp.application.utils.RecyclerViewEventHandler

interface UserRowEventHandler: RecyclerViewEventHandler {

    fun onClickRow()

    fun onClickDelete()

}