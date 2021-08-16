package com.example.crudroom.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.crudroom.data.entities.Student

@Dao
interface StudentDao {

    @Query("SELECT * from students")
    fun getAll(): LiveData<List<Student>>

    @Query("DELETE FROM students")
    fun deleteAll()

    @Query("SELECT * FROM students WHERE id = :id ")
    fun getById(id: String): LiveData<Student>

    @Insert(onConflict = REPLACE)
    fun insert(student: Student)

    @Delete
    fun delete(student: Student)

    @Update
    fun update(student: Student)

}
