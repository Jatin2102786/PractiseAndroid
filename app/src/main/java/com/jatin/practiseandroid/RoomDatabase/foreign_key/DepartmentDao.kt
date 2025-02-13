package com.jatin.practiseandroid.RoomDatabase.foreign_key

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface DepartmentDao {

    @Insert
    fun insertDepartment(department: Department)

    @Query("SELECT * FROM department WHERE employeeId = :employeeId")
    fun getAllDepartments(employeeId: Int): List<Department>
}