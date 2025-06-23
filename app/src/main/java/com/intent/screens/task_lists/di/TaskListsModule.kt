package com.intent.screens.task_lists.di

import com.intent.screens.task_lists.data.repository.TaskListsRepositoryImpl
import com.intent.screens.task_lists.domain.repository.TaskListsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object TaskListsModule {

    @Module
    @InstallIn(ViewModelComponent::class)
    internal abstract class RepositoryModule {
        @Binds
        @ViewModelScoped
        abstract fun bindTaskListsRepository(
            impl: TaskListsRepositoryImpl
        ): TaskListsRepository
    }
}
