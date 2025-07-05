package com.jatin.practiseandroid.videoView

import android.media.browse.MediaBrowser
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.jatin.practiseandroid.R
import com.jatin.practiseandroid.databinding.ActivityExoPlayerBinding

class ExoPlayerActivity : AppCompatActivity() {

    private val binding: ActivityExoPlayerBinding by lazy {
        ActivityExoPlayerBinding.inflate(layoutInflater)
    }
    private var player: ExoPlayer? = null
    private lateinit var playerView: PlayerView
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
            playerView = playerViewVideo

            player = ExoPlayer.Builder(this@ExoPlayerActivity).build()
            playerView.player = player

            val videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
            val uri = Uri.parse(videoUrl)
            val mediaItem = MediaItem.fromUri(uri)

            player?.apply {
                setMediaItem(mediaItem)
                prepare()
                playWhenReady = true
            }
        }
    }


    override fun onStop() {
        super.onStop()
        player?.release()
        player = null
    }
}