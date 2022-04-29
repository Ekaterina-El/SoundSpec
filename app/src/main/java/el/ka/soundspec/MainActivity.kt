package el.ka.soundspec

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import el.ka.soundspec.helpers.*

class MainActivity : AppCompatActivity() {
    private val soundState = mutableListOf<Int>(0, 1, 2, 3)

    private fun prepareSoundState() {
        soundState.clear()
        for (i in 0 until COUNT_OF_COLUMNS) soundState.add(i, i)
    }

    private fun shiftSoundStateAndAdd(value: Int) {
        var i = COUNT_OF_COLUMNS - 1
        while (i != 0) {
            soundState[i] = soundState[i - 1]
            i--
        }
        soundState[0] = value
        Log.d("SoundState", soundState.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        prepareSoundState()
    }

    override fun onResume() {
        super.onResume()

        val sm = SoundMeter(this)
        sm.start()

        val handler = Handler()
        val r = object : Runnable {
            override fun run() {
                var amplitude = sm.getAmplitude()
                if (amplitude > MAX_SOUND_AMPLITUDE) amplitude = MAX_SOUND_AMPLITUDE
                if (amplitude < MIN_SOUND_AMPLITUDE) amplitude = MIN_SOUND_AMPLITUDE

                val level = mapAmplitudeToLevel(
                    amplitude,
                    MIN_SOUND_AMPLITUDE,
                    MAX_SOUND_AMPLITUDE,
                    MIN_LEVELS,
                    MAX_LEVELS
                )
                Log.d("SoundAmp", "Amplitude: $amplitude Level: $level")
                shiftSoundStateAndAdd(level)
                handler.postDelayed(this, SOUND_LISTENER_DELAY)
            }
        }
        handler.postDelayed(r, SOUND_LISTENER_DELAY)
    }
}
