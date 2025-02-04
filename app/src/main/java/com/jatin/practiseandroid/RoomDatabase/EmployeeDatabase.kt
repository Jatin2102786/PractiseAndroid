package com.jatin.practiseandroid.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Employee::class], version = 1)
abstract class EmployeeDatabase : RoomDatabase() {


    //    declaring our interface which contains method to implement crud
    abstract fun roomInterface(): RoomInterface


    //    Making our room database instance sharable
    companion object {

        // initializing our database
        private var employeeDatabase: EmployeeDatabase? = null


        fun getInstance(context: Context): EmployeeDatabase {

            if (employeeDatabase == null) {
                employeeDatabase = Room.databaseBuilder(
                    context,
                    EmployeeDatabase::class.java,
                    "employeeDatabase"
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return employeeDatabase!!
        }
    }
}