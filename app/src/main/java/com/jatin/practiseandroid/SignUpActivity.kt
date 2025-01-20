package com.jatin.practiseandroid

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.jatin.practiseandroid.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val name = binding.editTextName.text?.trim()
        val email = binding.editTextEmail.text?.trim()
        val age = binding.age.text?.trim()
        val bloodGroup = binding.bloodGroup.text?.trim()


        binding.signUpBTN.setOnClickListener {

            val intent = Intent(this,DrawerActivity::class.java).apply {
                putExtra("name",name)
                putExtra("age",age)
                putExtra("email",email)
                putExtra("blood",bloodGroup)
            }

            startActivity(intent)

        }
    }
}