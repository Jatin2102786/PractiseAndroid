package com.jatin.practiseandroid.videoView

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jatin.practiseandroid.R
import com.jatin.practiseandroid.databinding.ActivityVideoViewBinding

class VideoViewActivity : AppCompatActivity() {
    private val binding: ActivityVideoViewBinding by lazy {
        ActivityVideoViewBinding.inflate(layoutInflater)
    }
    private lateinit var mediaControls: MediaController
    val videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


//        Initializing video view if not initialized
        if (!::mediaControls.isInitialized) {

            mediaControls = MediaController(this)
            mediaControls.setAnchorView(this.binding.videoView)
        }

        binding.apply {

            pickVideoBtn.setOnClickListener { 
                val intent = Intent(this@VideoViewActivity,PickVideoActivity::class.java)
                startActivity(intent)
            }

            exoPlayer.setOnClickListener {
                val intent = Intent(this@VideoViewActivity,ExoPlayerActivity::class.java)
                startActivity(intent)
            }

            videoView.setMediaController(mediaControls)
            videoView.setVideoURI(
                Uri.parse(
                    "android.resource://"
                            + packageName + "/" + R.raw.test_video
                )
            )
            binding.videoView.requestFocus()
            binding.videoView.start()


//            handling situation when video completed
            binding.videoView.setOnCompletionListener {
                Toast.makeText(
                    applicationContext, "Video completed",
                    Toast.LENGTH_LONG
                ).show()
                true
            }

//            handling error
            binding.videoView.setOnErrorListener { mp, what, extra ->
                Toast.makeText(
                    applicationContext, "An Error Occurred " +
                            "While Playing Video !!!", Toast.LENGTH_LONG
                ).show()
                false
            }

        }
    }
}