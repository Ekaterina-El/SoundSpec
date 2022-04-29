package el.ka.soundspec

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import el.ka.soundspec.helpers.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()

        val sm = SoundMeter(this)
        sm.start()

        val handler = Handler()
        val r = object: Runnable {
            override fun run() {
                val amplitude = sm.getAmplitude()
                val level = mapAmplitudeToLevel(amplitude, MIN_SOUND_AMPLITUDE, MAX_SOUND_AMPLITUDE, MIN_LEVELS, MAX_LEVELS)
                Log.d("SoundAmp", "Amplitude: $amplitude Level: $level")
                handler.postDelayed(this, SOUND_LISTENER_DELAY)
            }
        }
        handler.postDelayed(r, SOUND_LISTENER_DELAY)
    }
}