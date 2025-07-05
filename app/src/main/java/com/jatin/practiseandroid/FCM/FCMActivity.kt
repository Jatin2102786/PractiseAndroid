package com.jatin.practiseandroid.FCM

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.jatin.practiseandroid.R
import com.jatin.practiseandroid.databinding.ActivityFcmactivityBinding

class FCMActivity : AppCompatActivity() {

    private val TAG = "FCM Logs"
    private lateinit var binding: ActivityFcmactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFcmactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Request permission for API 33+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {

                requestPermission()
            }
        }




//        Token registeration
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            binding.msgToken.text = token.toString()

                Log.d(TAG, token)

        })

        binding.copyButton.setOnClickListener {
            // Get the text from the TextView
            val textToCopy = binding.msgToken.text.toString()

            // Check if the text is not empty before copying
            if (textToCopy.isEmpty()) {
                Toast.makeText(this, "No text to copy!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Exit the lambda if nothing to copy
            }

            // Get the system's clipboard service
            // 'as' is Kotlin's safe cast operator, similar to Java's (ClipboardManager)
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

            // Create a new ClipData object.
            val clip = ClipData.newPlainText("text_from_textview", textToCopy)

            // Set the clip to the primary clipboard
            clipboard.setPrimaryClip(clip)

            // Provide user feedback (a Toast message)
            // 'this' refers to the MainActivity context
            Toast.makeText(this, "Text copied to clipboard!", Toast.LENGTH_SHORT).show()
        }
    }





// Function for requesting permissions
    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
        }
    }
}