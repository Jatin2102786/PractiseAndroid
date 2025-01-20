package com.jatin.practiseandroid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jatin.practiseandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


//        binding.sendSms.setOnClickListener {
//
//            val phoneNumber = "9878248135"
//            val message = "Hi mummy"
//
//            val intent = Intent(Intent.ACTION_SENDTO)
//                intent.data = Uri.parse("smsto:$phoneNumber")
//            intent.putExtra(Intent.EXTRA_TEXT,message)
//
//            if (intent.resolveActivity(packageManager) != null) {
//                startActivity(intent)
//            } else {
//                Toast.makeText(this, "No SMS app found", Toast.LENGTH_SHORT).show()
//            }
//        }

//        binding.sendSms.setOnClickListener {
//            val recipient = "jatin1234@gmail.com"
//            val subject = "Subject of the Email"
//            val body = "Hello, this is the body of the email."
//
//            val intent = Intent(Intent.ACTION_SENDTO).apply {
//                data = Uri.parse("mailto:")
//                putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
//                putExtra(Intent.EXTRA_SUBJECT, subject)
//                putExtra(Intent.EXTRA_TEXT, body)
//            }
//
//            if (intent.resolveActivity(packageManager) != null) {
//                startActivity(intent)
//            } else {
//                Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show()
//            }
//        }

    }
}