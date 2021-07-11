package com.evgeniykim.mvvmdaggerretrofitrxandroid.injection.component

import com.evgeniykim.mvvmdaggerretrofitrxandroid.injection.module.NetworkModule
import com.evgeniykim.mvvmdaggerretrofitrxandroid.ui.post.PostListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(postListViewModel: PostListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}