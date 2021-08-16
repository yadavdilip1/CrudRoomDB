package com.example.crudroom.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.crudroom.data.entities.Student
import com.example.crudroom.data.local.StudentDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalRepository @Inject constructor(private val studentDao: StudentDao) {


    @WorkerThread
    suspend fun insert(studentEntity: Student) = withContext(Dispatchers.IO) {
        studentDao.insert(
            studentEntity
        )
    }

    @WorkerThread
    suspend fun update(studentEntity: Student) = withContext(Dispatchers.IO){
        studentDao.update(
            studentEntity
        )
    }

    @WorkerThread
    suspend fun delete(studentEntity: Student)= withContext(Dispatchers.IO) {
        studentDao.delete(studentEntity)
    }

    @WorkerThread
    suspend fun deleteAll() = withContext(Dispatchers.IO){
        studentDao.deleteAll()
    }

    @WorkerThread
    fun getAll(): LiveData<List<Student>> {
        return studentDao.getAll()
    }

}