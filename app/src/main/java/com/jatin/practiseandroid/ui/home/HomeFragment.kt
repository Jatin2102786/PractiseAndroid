package com.jatin.practiseandroid.ui.home

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jatin.practiseandroid.databinding.CalculatorBinding
import com.jatin.practiseandroid.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {

    //    Accessing HomeViewModel class
    private val homeViewModel: HomeViewModel by viewModels()


    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calculatorBtn.setOnClickListener {
            showCalculator()
        }

        binding.pickDate.setOnClickListener {
            showDatePicker()
        }

        binding.pickTime.setOnClickListener {
            showTimePicker()
        }
    }

    private fun showCalculator() {

        val dialog = Dialog(requireContext())
        val dialogBinding = CalculatorBinding.inflate(layoutInflater)

        dialog.setContentView(dialogBinding.root)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(true)

        dialogBinding.addBtn.setOnClickListener {
            if (dialogBinding.firstNumber.text.isNotEmpty() && dialogBinding.secondNumber.text.isNotEmpty()) {
                val firstNumber = dialogBinding.firstNumber.text.toString().toInt()
                val secondNumber = dialogBinding.secondNumber.text.toString().toInt()

                val result = firstNumber + secondNumber
                dialogBinding.ansTextView.text = "Answer = $result"
            } else {
                dialogBinding.ansTextView.text = "Please enter both numbers"
            }
        }

        dialogBinding.subBtn.setOnClickListener {
            if (dialogBinding.firstNumber.text.isNotEmpty() && dialogBinding.secondNumber.text.isNotEmpty()) {
                val firstNumber = dialogBinding.firstNumber.text.toString().toInt()
                val secondNumber = dialogBinding.secondNumber.text.toString().toInt()

                val result = firstNumber - secondNumber
                dialogBinding.ansTextView.text = "Answer = $result"
            } else {
                dialogBinding.ansTextView.text = "Please enter both numbers"
            }
        }

        dialogBinding.productBtn.setOnClickListener {
            if (dialogBinding.firstNumber.text.isNotEmpty() && dialogBinding.secondNumber.text.isNotEmpty()) {
                val firstNumber = dialogBinding.firstNumber.text.toString().toInt()
                val secondNumber = dialogBinding.secondNumber.text.toString().toInt()

                val result = firstNumber * secondNumber
                dialogBinding.ansTextView.text = "Answer = $result"
            } else {
                dialogBinding.ansTextView.text = "Please enter both numbers"
            }
        }

        dialogBinding.divideBtn.setOnClickListener {
            if (dialogBinding.firstNumber.text.isNotEmpty() && dialogBinding.secondNumber.text.isNotEmpty()) {
                val firstNumber = dialogBinding.firstNumber.text.toString().toInt()
                val secondNumber = dialogBinding.secondNumber.text.toString().toInt()

                if (secondNumber != 0) {
                    val result = firstNumber.toDouble() / secondNumber.toDouble()
                    dialogBinding.ansTextView.text = "Answer = $result"
                } else {
                    dialogBinding.ansTextView.text = "Cannot divide by zero"
                }
            } else {
                dialogBinding.ansTextView.text = "Please enter both numbers"
            }
        }

        dialogBinding.clearBtn.setOnClickListener {
            dialogBinding.ansTextView.text = " "
            dialogBinding.firstNumber.text.clear()
            dialogBinding.secondNumber.text.clear()
        }

        dialog.show()
    }

    private fun showDatePicker() {

        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                calendar.set(year, month, day)

                val formats = listOf(
                    "dd-MM-yyyy",
                    "MM/dd/yyyy",
                    "yyyy/MM/dd",
                    "E, MMM dd yyyy",
                    "dd MMMM yyyy"
                )

                val stringBuilder = StringBuilder()
                for (format in formats) {


                    val formattedDate = SimpleDateFormat(format, Locale("fr","FR")).format(calendar.time)
                    stringBuilder.append(formattedDate).append("\n")
                }

                binding.dateFormats.text = stringBuilder.toString().trim()

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
            .show()
    }


    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        TimePickerDialog(
            requireContext(),
            { _, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)


                val formats = listOf(
                    "hh:mm a",
                    "HH:mm",
                    "hh:mm:ss a",
                    "HH:mm:ss"
                )

                val stringBuilder = StringBuilder()
                for (format in formats) {
                    val formattedTime = SimpleDateFormat(format, Locale("fr","FR")).format(calendar.time)
                    stringBuilder.append(formattedTime).append("\n")
                }

                binding.timeFormats.text = stringBuilder.toString().trim()


            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
            .show()
    }

}

