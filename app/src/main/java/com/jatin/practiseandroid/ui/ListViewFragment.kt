package com.jatin.practiseandroid.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.jatin.practiseandroid.R
import com.jatin.practiseandroid.classes.Employee
import com.jatin.practiseandroid.classes.EmployeeAdapter
import com.jatin.practiseandroid.classes.OnItemClick
import com.jatin.practiseandroid.databinding.FragmentHomeBinding
import com.jatin.practiseandroid.databinding.FragmentListViewBinding
import com.jatin.practiseandroid.databinding.ListviewAddItemDialogBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListViewFragment : Fragment(),OnItemClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private val employeeItems = arrayListOf<Employee>()
    private val items = mutableListOf<String>()
    private lateinit var binding: FragmentListViewBinding
//    private lateinit var adapter: ArrayAdapter<String>

    private lateinit var adapter: EmployeeAdapter
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
       binding = FragmentListViewBinding.inflate(layoutInflater)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
//        binding.listView.adapter = adapter
//
//        binding.fab.setOnClickListener { addItem() }
//
//        binding.listView.setOnItemClickListener { _, _, position, _ -> updateItem(position) }
//
//        binding.listView.setOnItemLongClickListener { _, _, position, _ ->
//            deleteItem(position)
//            true
//        }

        employeeItems.add(Employee("Jatin","Mehmi"))
        employeeItems.add(Employee("Maninder","Singh"))
        employeeItems.add(Employee("Badal","Singh"))



        adapter = EmployeeAdapter(employeeItems,this@ListViewFragment)
        binding.listView.adapter = adapter


        binding.fab.setOnClickListener {

            showAddEmployeeDialog()
        }

    }



    private fun addItem() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Item")

        val input = EditText(requireContext())
        builder.setView(input)

        builder.setPositiveButton("Add") { _, _ ->
            val newItem = input.text.toString()
            if (newItem.isNotEmpty()) {
                items.add(newItem)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "Item cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    private fun updateItem(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Update Item")

        val input = EditText(requireContext())
        input.setText(items[position])
        builder.setView(input)

        builder.setPositiveButton("Update") { _, _ ->
            val updatedItem = input.text.toString()
            if (updatedItem.isNotEmpty()) {
                items[position] = updatedItem
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(requireContext(), "Item cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    private fun deleteItem(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Item")
        builder.setMessage("Are you sure you want to delete this item?")

        builder.setPositiveButton("Delete") { _, _ ->
            items.removeAt(position)
            adapter.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Item deleted", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListViewFragment().apply {
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
                employeeItems.add(Employee(name, surname))
                adapter.notifyDataSetChanged()
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
            setText(employeeItems[position].name)
        }

        val surnameInput = EditText(requireContext()).apply {
            setText(employeeItems[position].surName)
        }

        layout.addView(nameInput)
        layout.addView(surnameInput)
        builder.setView(layout)

        builder.setPositiveButton("Update") { _, _ ->
            val updatedName = nameInput.text.toString().trim()
            val updatedSurname = surnameInput.text.toString().trim()
            if (updatedName.isNotEmpty() && updatedSurname.isNotEmpty()) {
                employeeItems[position] = Employee(updatedName, updatedSurname)
                adapter.notifyDataSetChanged()
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
            employeeItems.removeAt(position)
            adapter.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Employee deleted", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Cancel", null)
        builder.show()
    }
}