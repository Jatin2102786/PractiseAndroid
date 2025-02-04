package com.jatin.practiseandroid.RoomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Employee(
    @PrimaryKey(autoGenerate = true)


    var id : Int = 0,
    var name: String,
    var surName: String

) {

}