package com.jatin.practiseandroid.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.jatin.practiseandroid.R
import com.jatin.practiseandroid.databinding.FragmentSpinnerBinding
import com.jatin.practiseandroid.databinding.SpinnerLayoutBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SpinnerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpinnerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentSpinnerBinding
    private var userList = arrayListOf("Jatin", "Maninder", "Badal")
    private lateinit var dynamicSpinnerAdapter: ArrayAdapter<String>

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
        binding = FragmentSpinnerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//         Static spinner
        val languages = resources.getStringArray(R.array.Languages)
        val staticSpinnerAdapter = ArrayAdapter(
            requireActivity(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            languages
        )
        binding.staticSpinner.adapter = staticSpinnerAdapter
        binding.staticSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                p1: View?,
                position: Int,
                p3: Long
            ) {
                Toast.makeText(requireContext(), languages[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


//        Dynamic Spinner
        dynamicSpinnerAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            userList
        )
        binding.dynamicSpinner.adapter = dynamicSpinnerAdapter

        binding.dynamicSpinner.onItemSelectedListener = object : OnItemSelectedListener {

            private var isFirstSelection = true
            override fun onItemSelected(
                parent: AdapterView<*>?,
                p1: View?,
                position: Int,
                p3: Long
            ) {
                val selectedItem = userList[position]



                if (userList.isEmpty()) {
                    Toast.makeText(requireContext(), "No items are there", Toast.LENGTH_SHORT)
                        .show()
                    return
                }
                if (isFirstSelection) {
                    isFirstSelection = false
                    return
                }


                alertDialog(selectedItem,position)


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(
                    requireContext(),
                    "Spinner is empty please add some items",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.addFab.setOnClickListener { dialogShow("0", "0") }
        binding.deleteFab.setOnClickListener { dialogShow("1", "0") }
        binding.updateFab.setOnClickListener { dialogShow("2", "0") }

        binding.viewLayoutBtn.setOnClickListener {
            findNavController().navigate(R.id.nav_gallery)
        }


    }

    private fun alertDialog(selectedItem: String,position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Please enter your choice to perform operation")
            .setMessage("You selected: $selectedItem")
            .setPositiveButton("Delete") { dialog, _ ->
                userList.removeAt(position)
                dynamicSpinnerAdapter.notifyDataSetChanged()
                Toast.makeText(
                    requireContext(),
                    "Changes done successfully",
                    Toast.LENGTH_SHORT
                ).show()


            }
            .setNegativeButton("Update") { dialog, _ ->
                dialogShow("5", position.toString())
                dialog.dismiss()
            }
            .show()

    }

    private fun dialogShow(choice: String, enteredPosition: String) {

        val dialog = Dialog(requireContext())
        val dialogBinding = SpinnerLayoutBinding.inflate(layoutInflater)

        dialog.setContentView(
            dialogBinding.root,
        )
        dialog.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        val size = userList.size

        when (choice) {
            "0" -> {
                dialogBinding.headingET.text = "Enter item name to add in spinner"
                dialogBinding.itemPositionET.visibility = View.GONE

                dialogBinding.doneBtn.setOnClickListener {
                    if (dialogBinding.itemNameET.text.isNotEmpty()) {
                        val newUser = dialogBinding.itemNameET.text.trim()
                        userList.add(newUser.toString())
                        dynamicSpinnerAdapter.notifyDataSetChanged()

                        Toast.makeText(
                            requireContext(),
                            "Changes done successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        dialog.dismiss()
                    }

                }

            }

            "1" -> {
                dialogBinding.headingET.text = "Enter item position to delete in spinner"


                dialogBinding.itemNameET.visibility = View.GONE

                dialogBinding.doneBtn.setOnClickListener {

                    if (dialogBinding.itemPositionET.text.toString().isNotEmpty()) {


                        val position = dialogBinding.itemPositionET.text.toString().toInt()

                        if (position in 1..size) {
                            userList.removeAt(position - 1)
                            dynamicSpinnerAdapter.notifyDataSetChanged()
                            Toast.makeText(
                                requireContext(),
                                "Changes done successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            dialog.dismiss()
                        } else {
                            dialogBinding.itemPositionET.error = "Please enter valid position"
                        }
                    }

                }
            }

            "2" -> {
                dialogBinding.headingET.text = "Enter item name and position to update in spinner"


                dialogBinding.doneBtn.setOnClickListener {

                    if (dialogBinding.itemNameET.text.isNotEmpty() && dialogBinding.itemPositionET.text.isNotEmpty()) {
                        val position = dialogBinding.itemPositionET.text.toString().toInt()
                        if (position in 1..size) {
                            userList[position - 1] = dialogBinding.itemNameET.text.toString()

                            dynamicSpinnerAdapter.notifyDataSetChanged()
                            Toast.makeText(
                                requireContext(),
                                "Changes done successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            dialog.dismiss()
                        } else {
                            dialogBinding.itemPositionET.error = "Please enter valid position"
                        }

                    }
                }
            }

            "5" -> {
                dialogBinding.headingET.text = "Enter item name to update in spinner"


                dialogBinding.itemPositionET.visibility = View.GONE
                dialogBinding.doneBtn.setOnClickListener {
                    userList[enteredPosition.toInt()] = dialogBinding.itemNameET.text.toString()
                    dynamicSpinnerAdapter.notifyDataSetChanged()
                    dialogBinding.itemNameET.text.clear()
                    Toast.makeText(
                        requireContext(),
                        "Changes done successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    dialog.dismiss()
                }

            }
        }

        dialog.show()

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SpinnerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SpinnerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}