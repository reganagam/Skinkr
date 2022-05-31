package com.capstone.bangkit.skinkr.di

import android.content.Context
import com.capstone.bangkit.skinkr.data.remote.services.ApiConfig
import com.capstone.bangkit.skinkr.data.repository.AcneRepository

object Injection {
    fun provideRepository(context: Context): AcneRepository {
        val apiService = ApiConfig.getApiServices()

        /*val database = FavoriteRoomDatabase.getDatabase(context)
        val dao = database.favDao()
        val appExecutors = AppExecutors()*/
        return AcneRepository.getInstance(apiService)
    }
}