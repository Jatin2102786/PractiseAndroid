package com.jatin.practiseandroid.ui.slideshow

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jatin.practiseandroid.databinding.AlertDialogBinding
import com.jatin.practiseandroid.databinding.FragmentSlideshowBinding

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!


    private var list = arrayOf("one", "two", "three", "four")
    private var checkedArray = booleanArrayOf(false, false, false, false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this)[SlideshowViewModel::class.java]

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.alertDialogBtn.setOnClickListener {
//            alertDialog()
            multipleCheckedItemAlertDialog()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun multipleCheckedItemAlertDialog() {


        AlertDialog.Builder(requireContext())
            .apply {
                setTitle("Select Items")
//                setMessage("Choose multiple items from the list")

                setMultiChoiceItems(list, checkedArray) { _, which, isChecked ->
                    checkedArray.set(which,isChecked)
//                    val currentItem = list[which]
                    Toast.makeText(requireContext(), "${list[which]}", Toast.LENGTH_SHORT).show()
                }

                setPositiveButton("Okk") { dialog, _ ->
                    dialog.dismiss()
                }

                setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                show()
            }
    }

    private fun alertDialog() {
        val dialogBinding = AlertDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())

        val width = (300 * requireActivity().resources.displayMetrics.density).toInt()
        val height = (400 * requireActivity().resources.displayMetrics.density).toInt()
        dialog.setContentView(dialogBinding.root, ViewGroup.LayoutParams(width, height))
//        dialog.setContentView(dialogBinding.root)

        dialogBinding.meeting.setOnClickListener {
            dialogBinding.messageTV.setText("Meeting in 10 minutes")
        }

        dialogBinding.reportOffice.setOnClickListener {
            dialogBinding.messageTV.setText("Report to office")
        }

        dialogBinding.emailTemplate.setOnClickListener {
            dialogBinding.messageTV.setText("Check your email")
        }

        dialogBinding.sendBTN.setOnClickListener {
            Toast.makeText(
                requireContext(),
                dialogBinding.messageTV.text.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }

        dialog.show()

    }
}