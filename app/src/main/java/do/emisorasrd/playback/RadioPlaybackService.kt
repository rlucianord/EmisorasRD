package do.emisorasrd.playback

import android.content.Intent
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import do.emisorasrd.data.Station

class RadioPlaybackService : MediaSessionService() {
    private var player: ExoPlayer? = null
    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .build()

        player = ExoPlayer.Builder(this).build().apply {
            setAudioAttributes(audioAttributes, true)
            setHandleAudioBecomingNoisy(true)
        }

        mediaSession = MediaSession.Builder(this, player!!).build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
        }
        player = null
        mediaSession = null
        super.onDestroy()
    }

    companion object {
        const val ACTION_PLAY = "do.emisorasrd.action.PLAY"
        const val EXTRA_NAME = "station_name"
        const val EXTRA_URL = "station_url"

        fun intent(context: android.content.Context, station: Station): Intent =
            Intent(context, RadioPlaybackService::class.java).apply {
                action = ACTION_PLAY
                putExtra(EXTRA_NAME, station.name)
                putExtra(EXTRA_URL, station.streamUrl)
            }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_PLAY) {
            val name = intent.getStringExtra(EXTRA_NAME) ?: "Emisora"
            val url = intent.getStringExtra(EXTRA_URL) ?: return START_NOT_STICKY
            val item = MediaItem.Builder()
                .setUri(url)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(name)
                        .setArtist("Emisoras RD")
                        .build()
                )
                .build()

            player?.setMediaItem(item)
            player?.prepare()
            player?.play()
        }
        return START_STICKY
    }
}
