package com.flowpreviewapplication.di

import androidx.room.Room
import com.flowpreviewapplication.data.datasource.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object AppModule {

    val module = module {

        single(createdAtStart = true) {
            Room.databaseBuilder(
                    androidContext(),
                    AppDatabase::class.java,
                    "app_database"
                )
                .fallbackToDestructiveMigration()
                .addCallback(AppDatabase.dbCallback)
                .build()
        }

    }

}