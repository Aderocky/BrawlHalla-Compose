package com.example.brawlhallahero.di

import com.example.brawlhallahero.data.BrawlRepository


object Injection {
    fun provideRepository(): BrawlRepository {
        return BrawlRepository.getInstance()
    }
}