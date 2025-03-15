package com.crux.screens.main.di

import com.crux.screens.main.data.repository.MainRepositoryImpl
import com.crux.screens.main.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object MainModule {

    @Module
    @InstallIn(ViewModelComponent::class)
    internal abstract class RepositoryModule {
        @Binds
        @ViewModelScoped
        abstract fun bindMainRepository(
            impl: MainRepositoryImpl
        ): MainRepository
    }
}
