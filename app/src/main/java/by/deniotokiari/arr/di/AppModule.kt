package by.deniotokiari.arr.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import by.deniotokiari.arr.db.AppDatabase
import by.deniotokiari.arr.thirdparty.GlideImageLoader
import okhttp3.OkHttpClient
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import java.text.SimpleDateFormat

fun appModule(): Module = module {

    factory<SharedPreferences> { get<Context>().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE) }

    single { Room.databaseBuilder(get(), AppDatabase::class.java, APP_DATABASE).build() }

    single { OkHttpClient() }

    single { GlideImageLoader().init(get()) }

    single { SimpleDateFormat() }

}

private const val SHARED_PREFS_NAME = "app_prefs"
private const val APP_DATABASE = "app_database"

private const val ARTICLE_PUB