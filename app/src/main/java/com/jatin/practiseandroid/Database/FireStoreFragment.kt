package com.jatin.practiseandroid.Database

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import com.google.firebase.firestore.DocumentChange


import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.privacysandbox.tools.core.model.Type
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.firestore
import com.jatin.practiseandroid.R
import com.jatin.practiseandroid.classes.Employee
import com.jatin.practiseandroid.classes.RecyclerAdapter
import com.jatin.practiseandroid.classes.onClick
import com.jatin.practiseandroid.databinding.FragmentFireStoreBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FireStoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FireStoreFragment : Fragment(), onClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentFireStoreBinding
    private lateinit var adapter: RecyclerAdapter
    private val collectionName = "user"
    private var userList: ArrayList<Employee> = ArrayList()

    //    Initializing firestore
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFireStoreBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getStudents()
        adapter = RecyclerAdapter(userList, this)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter


        binding.fab.setOnClickListener {
            showAddEmployeeDialog()
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FireStoreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FireStoreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getStudents() {
        db.collection(collectionName).addSnapshotListener { snapshots, e ->
            if (e != null) {
                return@addSnapshotListener
            }
            for (snapshot in snapshots!!.documentChanges) {
                val student = convertObject(snapshot.document)

                when (snapshot.type) {
                    DocumentChange.Type.ADDED -> {
                        student?.let { userList.add(it) }
                        Log.e("", "Student List ${userList.size}")
                        Log.e("", "Student List ${userList}")

                    }
                    DocumentChange.Type.MODIFIED -> {
                        student.let {
                            var index = getIndex(student!!)
                            if (index > -1)
                                userList.set(index, it!!)
                        }
                    }

                    DocumentChange.Type.REMOVED -> {
                        student?.let {
                            var index = getIndex(student)
                            if (index > -1)
                                userList.removeAt(index)
                        }
                    }

                    else -> {}
                }

            }
            adapter.notifyDataSetChanged()

        }
    }

    private fun convertObject(snapshot: QueryDocumentSnapshot): Employee? {
        val employee: Employee? =
            snapshot.toObject(Employee::class.java)
        employee?.studentID = snapshot.id ?: ""
        return employee
    }

    private fun showAddEmployeeDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Student")

        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 20, 40, 20)
        }

        val nameInput = EditText(requireContext()).apply {
            hint = "Enter Name"
        }

        val surnameInput = EditText(requireContext()).apply {
            hint = "Enter Surname"
        }

        layout.addView(nameInput)
        layout.addView(surnameInput)
        builder.setView(layout)

        builder.setPositiveButton("Add") { _, _ ->
            val name = nameInput.text.toString().trim()
            val surname = surnameInput.text.toString().trim()

            try {

                val student = Employee(name = name, surName = surname)
                db.collection("user").document().set(student)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Add Failed", Toast.LENGTH_SHORT).show()

                    }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
            }


        }

        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    private fun getIndex(student: Employee): Int {
        var index = -1
        index = userList.indexOfFirst { element ->
            element.studentID?.equals(student.studentID) == true
        }
        return index
    }

    override fun update(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Update Student Data")

        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 20, 40, 20)
        }

        val nameInput = EditText(requireContext()).apply {
            setText(userList[position].name)
        }

        val surnameInput = EditText(requireContext()).apply {
            setText(userList[position].surName)
        }

        layout.addView(nameInput)
        layout.addView(surnameInput)
        builder.setView(layout)

        builder.setPositiveButton("Update") { _, _ ->
            val updatedName = nameInput.text.toString().trim()
            val updatedSurname = surnameInput.text.toString().trim()
            if (updatedName.isNotEmpty() && updatedSurname.isNotEmpty()) {

                userList[position].studentID?.let {
                    db.collection(collectionName).document(it)
                        .set(Employee(name =updatedName, surName = updatedSurname))
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(), "Update Failed ", Toast.LENGTH_SHORT).show()

                        }

                }
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "Fields cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    override fun delete(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Student")
        builder.setMessage("Are you sure you want to delete this student?")

        builder.setPositiveButton("Delete") { _, _ ->

            userList[position].studentID?.let {
                db.collection(collectionName).document(it).delete()
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()

                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Delete Failed", Toast.LENGTH_SHORT).show()

                    }

            }
            adapter.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Employee deleted", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancel", null)
        builder.show()
    }
}