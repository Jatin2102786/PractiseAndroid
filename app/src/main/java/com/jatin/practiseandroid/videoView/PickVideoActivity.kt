package com.jatin.practiseandroid.videoView

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jatin.practiseandroid.R
import com.jatin.practiseandroid.databinding.ActivityPickVideoBinding

class PickVideoActivity : AppCompatActivity() {


//    binding by using lazy
    private val binding: ActivityPickVideoBinding by lazy {
        ActivityPickVideoBinding.inflate(layoutInflater)
    }


//    picking video from gallery by using register for activity result
    private val pickVideo = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

//        here result is lambda function
        if (result.resultCode == Activity.RESULT_OK) {
            val videoUri: Uri? = result.data?.data
            if (videoUri != null) {

//                Function to play video idf the video yrl is not null
                playVideo(videoUri)
            }else{
                Toast.makeText(this, "Video Uri is null", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            btnPickVideo.setOnClickListener {

//                creating intent to visit gallery
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                pickVideo.launch(intent)
            }
        }
    }

    private fun playVideo(videoUri: Uri) {
        try{


//            accessing media controller
            val mediaController = MediaController(this)

//            setting media controller for video view
            binding.videoView.setMediaController(mediaController)
            mediaController.setAnchorView(binding.videoView)


//            loading video uri
            binding.videoView.setVideoURI(videoUri)
            binding.videoView.requestFocus()
            binding.videoView.start()


//            mp is instance of media player,
//            what provides information about error,
//            extra provide more detailed information
//            error  handling
            binding.videoView.setOnErrorListener { mp, what, extra ->
                Toast.makeText(this, "Video playback error.", Toast.LENGTH_SHORT).show()
                true
            }

        } catch (e: Exception) {
            Toast.makeText(this, "Invalid video URI.", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }


    }

}