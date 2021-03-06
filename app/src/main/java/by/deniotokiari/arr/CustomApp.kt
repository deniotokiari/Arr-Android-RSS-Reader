package by.deniotokiari.arr

import android.app.Application
import by.deniotokiari.arr.di.appModule
import by.deniotokiari.arr.di.viewModelModule
import org.koin.android.ext.android.startKoin

class CustomApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(
            this, listOf(
                appModule(),
                viewModelModule()
            )
        )
    }

}