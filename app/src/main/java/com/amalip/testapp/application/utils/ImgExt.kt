package com.amalip.testapp.application.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:loadSrc")
fun ImageView.loadSrc(resource: Int) = this.setImageResource(resource)