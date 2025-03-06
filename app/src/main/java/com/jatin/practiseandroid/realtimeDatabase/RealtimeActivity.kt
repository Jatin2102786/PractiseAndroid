package com.jatin.practiseandroid.realtimeDatabase

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase
import com.jatin.practiseandroid.R
import com.jatin.practiseandroid.databinding.ActivityRealtimeBinding

class RealtimeActivity : AppCompatActivity() {


    private lateinit var database: FirebaseDatabase
    private lateinit var binding: ActivityRealtimeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRealtimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        database = FirebaseDatabase.getInstance()


        binding.submitBtn.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val email = binding.email.text.toString().trim()

            val newUser = User(name,email)

            Log.d("name",name.toString())
            database.getReference("users").child("1").setValue(newUser)
                .addOnSuccessListener {
                    Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show()
                }

        }
    }


}