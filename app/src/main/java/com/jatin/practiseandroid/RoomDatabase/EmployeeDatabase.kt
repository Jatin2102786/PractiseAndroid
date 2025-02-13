package com.jatin.practiseandroid.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jatin.practiseandroid.RoomDatabase.foreign_key.Department
import com.jatin.practiseandroid.RoomDatabase.foreign_key.DepartmentDao

@Database(entities = [Employee::class, Department::class], version = 2)
abstract class EmployeeDatabase : RoomDatabase() {

    // Declaring interfaces for DAO access
    abstract fun roomInterface(): RoomInterface
    abstract fun departmentDao(): DepartmentDao

    companion object {
        @Volatile
        private var employeeDatabase: EmployeeDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS department (" +
                            "deptId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "employeeId INTEGER NOT NULL, " +
                            "departmentName TEXT NOT NULL, " +
                            "employeeName TEXT NOT NULL, " +
                            "FOREIGN KEY(employeeId) REFERENCES Employee(id) ON DELETE CASCADE)"
                )
            }
        }

        fun getInstance(context: Context): EmployeeDatabase {

            if (employeeDatabase == null) {
                employeeDatabase = Room.databaseBuilder(
                    context,
                    EmployeeDatabase::class.java,
                    "employeeDatabase"
                )
                    .addMigrations(MIGRATION_1_2) // Here i am adding migrations
                    .allowMainThreadQueries()
                    .build()
            }

            return employeeDatabase!!
        }
    }
}
