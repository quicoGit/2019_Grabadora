package com.plumbaria.e_6_10_grabadora

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import java.io.IOException
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private companion object {
        var LOG_TAG:String = "Grabadora"
        var fichero:String = Environment.getExternalStorageDirectory().absolutePath+"/audio.3gp"
    }
    private lateinit var mediaRecorder:MediaRecorder
    private lateinit var mediaPlayer:MediaPlayer
    private var MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 29
    private lateinit var bGrabar:Button
    private lateinit var bDetener:Button
    private lateinit var bReproducir:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bGrabar = findViewById(R.id.bGrabar)
        bDetener = findViewById(R.id.bDetener)
        bReproducir = findViewById(R.id.bReproducir)
        bDetener.isEnabled = false
        bReproducir.isEnabled = false
    }

    fun grabar(view:View){
        mediaRecorder = MediaRecorder()
        mediaRecorder.setOutputFile(fichero)
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        try {
            mediaRecorder.prepare()
        }catch (e:IOException){
            Log.e(LOG_TAG, "FALLO EN GRABACIÓN")
        }
        mediaRecorder.start()
        bGrabar.isEnabled = false
        bDetener.isEnabled = true
        bReproducir.isEnabled = false
    }

    fun detenerGrabacion(view:View){
        mediaRecorder.stop()
        mediaRecorder.release()
        bGrabar.isEnabled = true
        bDetener.isEnabled = false
        bReproducir.isEnabled = true
    }

    fun reproducir(view:View){
        mediaPlayer = MediaPlayer()
        try {
            mediaPlayer.setDataSource(fichero)
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e:IOException){
            Log.e(LOG_TAG, "Fallo en reproducción")
        }
    }
}
