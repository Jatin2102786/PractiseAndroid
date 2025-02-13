package com.jatin.practiseandroid.RoomDatabase.foreign_key

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.jatin.practiseandroid.RoomDatabase.Employee


@Entity(
    tableName = "department",
    foreignKeys = [ForeignKey(
        entity = Employee::class,
        parentColumns = ["id"],
        childColumns = ["employeeId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Department(
    @PrimaryKey(autoGenerate = true)
    val deptId: Int = 0,

    @ColumnInfo
    val departmentName: String,
    val employeeName: String,
    val employeeId: Int,
    )
