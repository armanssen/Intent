package com.crux.data.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.crux.R
import com.crux.data.database.AppDatabase
import com.crux.util.DEFAULT_TASK_LIST_ID
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room
            .databaseBuilder(
                context = context,
                klass = AppDatabase::class.java,
                name = AppDatabase.DATABASE_NAME
            )
            .addCallback(
                object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val defaultTableName = context.getString(R.string.common_default_table_name)
                        db.execSQL("INSERT INTO TaskListEntity (id, name) VALUES ($DEFAULT_TASK_LIST_ID, '$defaultTableName')")
                    }
                }
            )
            .build()
    }
}
