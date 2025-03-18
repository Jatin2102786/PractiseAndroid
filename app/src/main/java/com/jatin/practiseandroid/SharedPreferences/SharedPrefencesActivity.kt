package com.jatin.practiseandroid.SharedPreferences

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jatin.practiseandroid.R
import com.jatin.practiseandroid.databinding.ActivitySharedPrefencesBinding

class SharedPrefencesActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySharedPrefencesBinding

//    Declaring shared preferences
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySharedPrefencesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


//        Initializing shared preferences
        sharedPreferences = getSharedPreferences("Details", MODE_PRIVATE)

//        Editor
        val editor = sharedPreferences.edit()
        loadData()


        binding.mode.setOnClickListener {

           val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Change the mode")
            alertDialog.setPositiveButton("Dark") { dialog,_->
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("dark",true)
                editor.commit()
                editor.apply()
                dialog.dismiss()
            }
            alertDialog.setNegativeButton("Light") { dialog,_->
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("dark",false)
                editor.commit()
                editor.apply()
                dialog.dismiss()

            }
            alertDialog.show()

        }


        binding.save.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val email = binding.editTextEmail.text.toString()
            if (name.isNullOrEmpty()) {
                Toast.makeText(this, "Please fill name", Toast.LENGTH_SHORT).show()
            } else if (email.isNullOrEmpty()) {
                Toast.makeText(this, "Please fill email", Toast.LENGTH_SHORT).show()
            } else {
                editor.putString("name", name)
                editor.putString("email", email)

                editor.apply()
                Toast.makeText(
                    this,
                    "Value added ${binding.editTextName.text.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        binding.delete.setOnClickListener {
            editor.remove("name")
            editor.apply()
            Toast.makeText(this, "Value removed", Toast.LENGTH_SHORT).show()
            loadData()
        }
    }


    private fun loadData() {
        var name = sharedPreferences.getString("name","")
        var email = sharedPreferences.getString("email","")
        binding.editTextName.setText(name)
        binding.editTextName.setText(email)


        if (sharedPreferences.getBoolean("dark",true)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }
    }
}