package com.amalip.testapp.application.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.amalip.testapp.R
import com.amalip.testapp.application.utils.BaseAdapter
import com.amalip.testapp.application.utils.RecyclerViewViewModel

class UserListAdapter : BaseAdapter<RecyclerViewViewModel>(diffCallback = object :
    DiffUtil.ItemCallback<RecyclerViewViewModel>() {
    override fun areItemsTheSame(oldItem: RecyclerViewViewModel, newItem: RecyclerViewViewModel) =
        oldItem == newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: RecyclerViewViewModel, newItem: RecyclerViewViewModel) =
        oldItem == newItem
}) {
    override fun getLayoutIdForPosition(position: Int) = R.layout.user_row
}