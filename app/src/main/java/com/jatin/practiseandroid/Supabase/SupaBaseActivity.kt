package com.jatin.practiseandroid.Supabase

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.jatin.practiseandroid.R
import com.jatin.practiseandroid.databinding.ActivitySupaBaseBinding
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class SupaBaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySupaBaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySupaBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

         val imagePickerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val uri: Uri? = result.data?.data
                    if (uri != null) {
                        Glide.with(this)
                            .load(uri)
                            .into(binding.imageView)

                        lifecycleScope.launch(Dispatchers.IO) {
                            Log.d("ImagePicker", "Selected URI: $uri")


                            val file = uriToFile(uri)
                            if (file != null) {
                                uploadImageToSupabase(file)
                            } else {
                                Log.e("ImagePicker", "Failed to convert URI to file")
                            }
                        }
                    } else {
                        Log.e("ImagePicker", "Failed to get image URI")
                    }
                }
            }


        binding.submitBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(intent)
        }
    }

    fun uploadImageToSupabase(file: File) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val filePath = "uploads/${System.currentTimeMillis()}.jpg"

               MyApplication.supabase.storage.from("Images")
                   .upload(filePath, file.readBytes()) {
                       upsert = true
                   }

                val publicUrl = MyApplication.supabase.storage.from("images").publicUrl(filePath)
                println("File uploaded successfully: $publicUrl")
            } catch (e: Exception) {
                println("Upload failed: ${e.message}")
            }
        }
    }

    private fun uriToFile(uri: Uri): File? {
        val fileName = "temp_image_${System.currentTimeMillis()}.jpg"
        val file = File(this.cacheDir, fileName)

        return try {
            val inputStream: InputStream? = this.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            file
        } catch (e: Exception) {
            Log.e("ImagePicker", "Error converting URI to file: ${e.message}")
            null
        }
    }
}