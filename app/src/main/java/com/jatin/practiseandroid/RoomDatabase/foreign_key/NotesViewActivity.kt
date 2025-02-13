package com.jatin.practiseandroid.RoomDatabase.foreign_key

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jatin.practiseandroid.R
import com.jatin.practiseandroid.RoomDatabase.EmployeeDatabase
import com.jatin.practiseandroid.databinding.ActivityNotesViewBinding
import kotlinx.coroutines.launch

class NotesViewActivity : AppCompatActivity() {

    private lateinit var employeeDatabase: EmployeeDatabase
    private lateinit var binding: ActivityNotesViewBinding
    private var notesList: ArrayList<Department> = ArrayList()
    private var employeeId: String? = null
    private lateinit var subNotesAdapter: NotesRecycler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityNotesViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        employeeId = intent?.getStringExtra("employeeId")
        employeeDatabase = EmployeeDatabase.getInstance(this)

        getList()

        subNotesAdapter = NotesRecycler(notesList)
        binding.notesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.notesRecyclerView.adapter = subNotesAdapter


    }

    private fun getList() {

            notesList.clear()
            notesList.addAll(employeeDatabase.departmentDao().getAllDepartments(employeeId!!.toInt()))
            subNotesAdapter.notifyDataSetChanged()
    }
}