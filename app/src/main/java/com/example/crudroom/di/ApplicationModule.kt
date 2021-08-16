package com.example.crudroom.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.crudroom.data.local.StudentDao
import com.example.crudroom.data.local.StudentDatabase
import com.example.crudroom.data.repository.LocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideCustomRepository(studentDao: StudentDao): LocalRepository {
        return LocalRepository(studentDao)
    }

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext



    @Provides
    @Singleton
    fun provideDataBase(
        application: Application,
        roomCallback: RoomDatabase.Callback
    ): StudentDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            StudentDatabase::class.java,
            "student_database"
        )
            .fallbackToDestructiveMigration()
            .addCallback(roomCallback)
            .build()
    }

    @Provides
    fun provideRoomDatabaseCallback(): RoomDatabase.Callback {
        return object : RoomDatabase.Callback() {

        }
    }

    @Provides
    fun provideCustomDAO(studentDatabase: StudentDatabase): StudentDao {
        return studentDatabase.studentDao()
    }


}