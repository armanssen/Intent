package com.intent.screens.completed_tasks.di

import com.intent.screens.completed_tasks.data.repository.CompletedTasksRepositoryImpl
import com.intent.screens.completed_tasks.domain.repository.CompletedTasksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object CompletedTasksModule {

    @Module
    @InstallIn(ViewModelComponent::class)
    internal abstract class RepositoryModule {
        @Binds
        @ViewModelScoped
        abstract fun bindCompletedTasksRepository(
            repository: CompletedTasksRepositoryImpl
        ): CompletedTasksRepository
    }
}
