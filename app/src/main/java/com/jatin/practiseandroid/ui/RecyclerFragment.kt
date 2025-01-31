package com.jatin.practiseandroid.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.jatin.practiseandroid.classes.Employee
import com.jatin.practiseandroid.classes.RecyclerAdapter
import com.jatin.practiseandroid.classes.onClick
import com.jatin.practiseandroid.databinding.FragmentReccylerBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecyclerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecyclerFragment : Fragment(),onClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentReccylerBinding
    private lateinit var recyclerAdapter: RecyclerAdapter
    private var list: ArrayList<Employee> = ArrayList()

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

        binding = FragmentReccylerBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerAdapter = RecyclerAdapter(list,this@RecyclerFragment)
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),LinearLayoutManager.VERTICAL,false
        )
        binding.recyclerView.adapter = recyclerAdapter


        list.add(Employee("Jatin","Mehmi"))
        list.add(Employee("Maninder","Singh"))
        list.add(Employee("Badal","Singh"))

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
         * @return A new instance of fragment ReccylerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecyclerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun showAddEmployeeDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Employee")

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
            if (name.isNotEmpty() && surname.isNotEmpty()) {
                list.add(Employee(name, surname))
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancel", null)
        builder.show()
    }
    override fun update(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Update Employee")

        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 20, 40, 20)
        }

        val nameInput = EditText(requireContext()).apply {
            setText(list[position].name)
        }

        val surnameInput = EditText(requireContext()).apply {
            setText(list[position].surName)
        }

        layout.addView(nameInput)
        layout.addView(surnameInput)
        builder.setView(layout)

        builder.setPositiveButton("Update") { _, _ ->
            val updatedName = nameInput.text.toString().trim()
            val updatedSurname = surnameInput.text.toString().trim()
            if (updatedName.isNotEmpty() && updatedSurname.isNotEmpty()) {
                list[position] = Employee(updatedName, updatedSurname)
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    override fun delete(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Employee")
        builder.setMessage("Are you sure you want to delete this employee?")

        builder.setPositiveButton("Delete") { _, _ ->
            list.removeAt(position)
            recyclerAdapter.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Employee deleted", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

}