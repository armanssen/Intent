package com.crux.ui.add_or_edit_task.di

import com.crux.ui.add_or_edit_task.data.repository.AddOrEditTaskRepositoryImpl
import com.crux.ui.add_or_edit_task.domain.repository.AddOrEditTaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object AddOrEditTaskModule {

    @Module
    @InstallIn(ViewModelComponent::class)
    internal abstract class RepositoryModule {
        @Binds
        abstract fun bindAddOrEditTaskRepository(
            impl: AddOrEditTaskRepositoryImpl
        ): AddOrEditTaskRepository
    }
}
