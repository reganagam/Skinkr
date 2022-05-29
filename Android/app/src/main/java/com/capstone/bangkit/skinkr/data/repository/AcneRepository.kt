package com.capstone.bangkit.skinkr.data.repository

class AcneRepository {
    companion object {
        @Volatile
        private var instance: AcneRepository? = null
        fun getInstance(): AcneRepository =
            instance ?: synchronized(this) {
                instance ?: AcneRepository()
            }.also { instance = it }
    }
}