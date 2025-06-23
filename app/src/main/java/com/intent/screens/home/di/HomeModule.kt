package com.intent.screens.home.di

import com.intent.screens.home.data.repository.HomeRepositoryImpl
import com.intent.screens.home.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object HomeModule {

    @Module
    @InstallIn(ViewModelComponent::class)
    internal abstract class RepositoryModule {
        @Binds
        @ViewModelScoped
        abstract fun bindHomeRepository(
            impl: HomeRepositoryImpl
        ): HomeRepository
    }
}
