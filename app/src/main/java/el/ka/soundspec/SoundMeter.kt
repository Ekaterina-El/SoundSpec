package el.ka.soundspec

import android.content.Context
import android.media.MediaRecorder
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class SoundMeter(var context: Context) {
    private var  mRecorder: MediaRecorder? = null

    fun start() {
        if (mRecorder == null) {
            mRecorder = MediaRecorder()
            mRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            val audioPath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File(audioPath, "recording1.3gp")

            mRecorder!!.setOutputFile(file.absolutePath)
            mRecorder!!.prepare()
            mRecorder!!.start()
        }
    }

    fun stop() {
        if (mRecorder != null) {
            mRecorder!!.stop()
            mRecorder!!.release()
            mRecorder = null
        }
    }

    fun getAmplitude(): Int {
        return if (mRecorder != null) {
            mRecorder!!.maxAmplitude
        } else 0
    }
}