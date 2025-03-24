package com.crux.screens.home.di

import com.crux.screens.home.data.repository.HomeRepositoryImpl
import com.crux.screens.home.domain.repository.HomeRepository
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
