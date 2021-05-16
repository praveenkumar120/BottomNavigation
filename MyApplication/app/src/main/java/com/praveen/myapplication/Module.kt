package com.praveen.myapplication

import com.praveen.myapplication.service.ApiService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel { MainRepoViewModel(get()) }
    viewModel { DetailedRepoViewModel(get()) }
}

val networkModule = module {
    single { ApiService.create() }
}

val repositoryModule = module {
    single { provideMainRepository(get()) }
    single { provideDetailedRepository(get()) }
}
