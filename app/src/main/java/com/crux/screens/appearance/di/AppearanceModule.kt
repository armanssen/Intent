package com.crux.screens.appearance.di

import com.crux.screens.appearance.data.repository.AppearanceRepositoryImpl
import com.crux.screens.appearance.domain.repository.AppearanceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object AppearanceModule {

    @Module
    @InstallIn(ViewModelComponent::class)
    internal abstract class RepositoryModule {
        @Binds
        @ViewModelScoped
        abstract fun bindAppearanceRepository(
            impl: AppearanceRepositoryImpl
        ): AppearanceRepository
    }
}
