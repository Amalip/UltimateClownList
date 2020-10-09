package com.amalip.testapp.application.di

import com.amalip.testapp.application.viewModels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {

    viewModel { UserFormViewModel() }

    viewModel { UserListViewModel() }

    viewModel { UserRowViewModel() }

}