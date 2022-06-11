package com.ridianputra.obatin.utils

import com.ridianputra.obatin.data.Repository
import com.ridianputra.obatin.data.network.ApiConfig

object Injection {
    fun provideRepository(): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(apiService)
    }
}