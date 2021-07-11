package com.evgeniykim.mvvmdaggerretrofitrxandroid.base

import androidx.lifecycle.ViewModel
import com.evgeniykim.mvvmdaggerretrofitrxandroid.injection.component.DaggerViewModelInjector
import com.evgeniykim.mvvmdaggerretrofitrxandroid.injection.component.ViewModelInjector
import com.evgeniykim.mvvmdaggerretrofitrxandroid.injection.module.NetworkModule
import com.evgeniykim.mvvmdaggerretrofitrxandroid.ui.post.PostListViewModel

abstract class BaseViewModel: ViewModel() {

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is PostListViewModel -> injector.inject(this)
        }
    }
}