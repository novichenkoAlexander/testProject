package com.example.testapp

import android.app.Application
import com.example.testapp.database.DataBaseConstructor
import com.example.testapp.database.MyTestAppDatabase
import com.example.testapp.repositories.LocationRepository
import com.example.testapp.screens.viewModels.LocationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestApplication)
            modules(listOf(viewModels, dataBaseModule, repositories))
        }
    }

    private val viewModels = module {
        viewModel { LocationViewModel(get()) }
    }

    private val dataBaseModule = module {
        single { DataBaseConstructor.create(get()) }
        factory { get<MyTestAppDatabase>().locationsDao() }
        factory { get<MyTestAppDatabase>().imagesDao() }
    }

    private val repositories = module {
        factory { LocationRepository(get(), get()) }
    }
}