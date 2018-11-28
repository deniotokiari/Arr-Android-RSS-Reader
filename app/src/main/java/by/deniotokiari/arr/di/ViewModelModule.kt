package by.deniotokiari.arr.di

import android.content.Context
import by.deniotokiari.arr.R
import by.deniotokiari.arr.viewmodel.MainActivityViewModel
import by.deniotokiari.arr.viewmodel.MenuStateViewModel
import by.deniotokiari.arr.viewmodel.MenuViewModel
import by.deniotokiari.arr.viewmodel.OpmlImportRssFeedViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

fun viewModelModule(): Module = module {

    viewModel { MainActivityViewModel(get()) }

    viewModel { OpmlImportRssFeedViewModel(get(), get()) }

    viewModel { MenuViewModel(get(), get<Context>().getString(R.string.UNCATEGORIZED)) }

    viewModel { MenuStateViewModel() }

}