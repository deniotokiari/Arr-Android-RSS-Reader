package by.deniotokiari.arr.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.robolectric.RuntimeEnvironment

open class RoomDbBaseTest {

    lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context: Context = RuntimeEnvironment.application

        db = Room
            .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        db.close()
    }

}