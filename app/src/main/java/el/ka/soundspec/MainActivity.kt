package el.ka.soundspec

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }
    // 32767

    override fun onResume() {
        super.onResume()

        val sm = SoundMeter(this)
        sm.start()

        val handler = Handler()
        val r = object: Runnable {
            override fun run() {
                Log.d("SoundAmp", "Max: ${sm.getAmplitude()}")
                handler.postDelayed(this, 1000)
            }
        }
        handler.postDelayed(r, 1000)
    }
}