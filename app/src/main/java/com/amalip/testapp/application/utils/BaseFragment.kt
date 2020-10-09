package com.amalip.testapp.application.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

open class BaseFragment: Fragment() {

    //region Variables
    protected var navController: NavController? = null
    protected var binding: ViewDataBinding? = null
    protected var layout: Int = 0
    //endregion

    //region LifeCycle functions
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = setBinding(inflater, container)

        setNavController()

        return binding
    }

    override fun onDestroyView() {
        binding!!.unbind()
        binding = null
        navController = null
        super.onDestroyView()
    }
    //endregion

    //region Private functions
    private fun setBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding =
            DataBindingUtil.inflate(inflater, layout, container, false)

        binding!!.lifecycleOwner = this

        return binding!!.root
    }

    private fun setNavController() {
        navController = findNavController()
    }

}