package com.jatin.practiseandroid.RoomDatabase

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.jatin.practiseandroid.R
import com.jatin.practiseandroid.classes.RecyclerAdapter
import com.jatin.practiseandroid.databinding.ActivityRoomDbactivityBinding

class RoomDBActivity : AppCompatActivity(),RoomActivityAdapter.OnClick {



    private lateinit var binding: ActivityRoomDbactivityBinding
    private var employeeList = arrayListOf<Employee>()
    private lateinit var employeeDatabase: EmployeeDatabase
    private lateinit var recyclerAdapter: RoomActivityAdapter
    private var list: ArrayList<Employee> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRoomDbactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        employeeDatabase = EmployeeDatabase.getInstance(this)
//        employeeDatabase.roomInterface().insertTodo(Employee(name = "Jatin", surName = "Mehmi"))
//        employeeDatabase.roomInterface().insertTodo(Employee(name = "Maninder", surName = "Singh"))
//        employeeDatabase.roomInterface().insertTodo(Employee(name = "Badal", surName = "Singh"))


        recyclerAdapter = RoomActivityAdapter(list,this)
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL,false
        )
        binding.recyclerView.adapter = recyclerAdapter
        binding.fab.setOnClickListener {
            showAddEmployeeDialog()
        }

        getList()
    }

    private fun showAddEmployeeDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Employee")

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 20, 40, 20)
        }

        val nameInput = EditText(this).apply {
            hint = "Enter Name"
        }

        val surnameInput = EditText(this).apply {
            hint = "Enter Surname"
        }

        layout.addView(nameInput)
        layout.addView(surnameInput)
        builder.setView(layout)

        builder.setPositiveButton("Add") { _, _ ->
            val name = nameInput.text.toString().trim()
            val surname = surnameInput.text.toString().trim()
            if (name.isNotEmpty() && surname.isNotEmpty()) {
                employeeDatabase.roomInterface().insertTodo(Employee(name = name, surName = surname))
                recyclerAdapter.notifyDataSetChanged()
                getList()
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancel", null)
        builder.show()
    }


    override fun delete(position: Int) {
        Toast.makeText(this,"Delete Clicked", Toast.LENGTH_SHORT).show()
        employeeDatabase.roomInterface().deleteTodoEntity(list[position])
        getList()
        recyclerAdapter.notifyDataSetChanged()    }

    override fun update(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Update Employee")

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 20, 40, 20)
        }

        val nameInput = EditText(this).apply {
            setText(list[position].name)
        }

        val surnameInput = EditText(this).apply {
            setText(list[position].surName)
        }

        layout.addView(nameInput)
        layout.addView(surnameInput)
        builder.setView(layout)

        builder.setPositiveButton("Update") { _, _ ->
            val updatedName = nameInput.text.toString().trim()
            val id = list[position].id
            val updatedSurname = surnameInput.text.toString().trim()
            if (updatedName.isNotEmpty() && updatedSurname.isNotEmpty()) {

                val updateEmployee = Employee(id,updatedName,updatedSurname)
                employeeDatabase.roomInterface().updateTodoEntity(updateEmployee)
                getList()
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancel", null)
        builder.show()
    }



    fun getList() {
        list.clear()
        list.addAll(employeeDatabase.roomInterface().getEmployees())
    }
}