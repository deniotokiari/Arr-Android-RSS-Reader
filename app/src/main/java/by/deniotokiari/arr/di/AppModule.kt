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
import java.util.*

fun appModule(): Module = module {

    factory<SharedPreferences> { get<Context>().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE) }

    single { Room.databaseBuilder(get(), AppDatabase::class.java, APP_DATABASE).build() }

    single { OkHttpClient() }

    single { GlideImageLoader().init(get()) }

    single(name = "article") { SimpleDateFormat(ARTICLE_PUB_DATE_TEMPLATE, Locale.US) }

    single(name = "article_adapter") { SimpleDateFormat(ARTICLE_PUB_DATE_ADAPTER_TEMPLATE, Locale.US) }

}

private const val SHARED_PREFS_NAME = "app_prefs"
private const val APP_DATABASE = "app_database"

private const val ARTICLE_PUB_DATE_TEMPLATE = "EEE, d MMM yyyy HH:mm:ss z"
private const val ARTICLE_PUB_DATE_ADAPTER_TEMPLATE = "EEE, d MMM HH:mm:ss"