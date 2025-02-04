package com.jatin.practiseandroid.RoomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface RoomInterface {

    @Insert
    fun insertTodo(employeeEntity: Employee)


    @Query("SELECT * FROM Employee")
    fun getEmployees(): List<Employee>

    @Update
    fun updateTodoEntity(employeeEntity: Employee)

    @Delete
    fun deleteTodoEntity(employeeEntity: Employee)
}