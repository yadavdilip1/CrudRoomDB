package com.example.crudroom.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "students")
data class Student(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "mobileNum") var mobileNum: String,
    @ColumnInfo(name = "bookName") var bookName: String,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0
) : Parcelable