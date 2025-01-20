package com.jatin.practiseandroid.Database

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.jatin.practiseandroid.R
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
class FireStoreFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentFireStoreBinding

//    Initializing firestore
    private  var firestoreDB = Firebase.firestore

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

        var name = binding.nameET.text
        var email = binding.emailET.text
        var description = binding.descriptionET.text

        binding.addBTN.setOnClickListener {
            addUser(name.toString(),email.toString(),description.toString())
        }

        getUserData()

    }

    private fun addUser(name: String,email: String,description: String) {

        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "description" to description
        )


        firestoreDB.collection("users").document("1").set(user)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Data Added Successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { error->
                Toast.makeText(requireContext(), "Unable to add user due to:$error", Toast.LENGTH_SHORT).show()
            }
    }

    @SuppressLint("SetTextI18n")
    private fun getUserData(){
       firestoreDB.collection("users")
            .document("1")
            .get()
            .addOnSuccessListener { userData->


                val user = userData.data.toString()
                binding.userData.text = "Name = ${user[0].toString()}"
                "\nEmail = ${user[1].toString()}"

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
}