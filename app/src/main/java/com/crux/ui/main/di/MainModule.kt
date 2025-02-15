package com.crux.ui.main.di

import com.crux.ui.main.data.repository.MainRepositoryImpl
import com.crux.ui.main.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object MainModule {

    @Module
    @InstallIn(ViewModelComponent::class)
    internal abstract class RepositoryModule {
        @Binds
        abstract fun bindMainRepository(
            impl: MainRepositoryImpl
        ): MainRepository
    }
}
