package by.deniotokiari.arr.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

fun appModule(): Module =
    module {

        factory<SharedPreferences> { get<Context>().getSharedPreferences("app_prefs", Context.MODE_PRIVATE) }

    }