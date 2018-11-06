package by.deniotokiari.arr.di

import by.deniotokiari.arr.viewmodel.ImportRssFeedViewModel
import by.deniotokiari.arr.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

fun viewModelModule(): Module = module {

    viewModel { MainActivityViewModel(get()) }

    viewModel { ImportRssFeedViewModel(get(), get()) }

}