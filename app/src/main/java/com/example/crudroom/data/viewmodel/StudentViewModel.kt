package com.example.crudroom.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudroom.data.entities.Student
import com.example.crudroom.data.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StudentViewModel @Inject constructor(
    private val repository: LocalRepository
) : ViewModel() {


    val listItemsLiveData: LiveData<List<Student>>
        get() = repository.getAll()


    fun insertItem(item: Student) {

        viewModelScope.launch {
            repository.insert(item)
        }
    }

    fun updateItem(item: Student) {

        viewModelScope.launch {
            repository.update(item)
        }
    }

    fun deleteItem(item: Student) {

        viewModelScope.launch {
            repository.delete(item)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }


}