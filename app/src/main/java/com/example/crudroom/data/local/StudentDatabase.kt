package com.example.crudroom.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.crudroom.data.entities.Student

@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
}
