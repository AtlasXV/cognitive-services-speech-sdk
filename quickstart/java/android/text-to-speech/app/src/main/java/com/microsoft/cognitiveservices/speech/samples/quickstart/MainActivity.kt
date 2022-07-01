//
// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE.md file in the project root for full license information.
//
// <code>
package com.microsoft.cognitiveservices.speech.samples.quickstart

import android.Manifest.permission
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.microsoft.cognitiveservices.speech.ResultReason
import com.microsoft.cognitiveservices.speech.SpeechConfig
import com.microsoft.cognitiveservices.speech.SpeechSynthesisCancellationDetails
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer
import com.microsoft.cognitiveservices.speech.audio.AudioConfig
import java.io.File

class MainActivity : AppCompatActivity() {
    private var speechConfig: SpeechConfig? = null
    private var synthesizer: SpeechSynthesizer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Note: we need to request the permissions
        val requestCode = 5 // unique code for the permission request
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(permission.INTERNET),
            requestCode
        )

        // Initialize speech synthesizer and its dependencies
        speechConfig = SpeechConfig.fromSubscription(speechSubscriptionKey, serviceRegion).apply {
//            speechSynthesisVoiceName = "zh-CN-XiaomoNeural"
//            speechSynthesisVoiceName = "zh-CN-XiaoshuangNeural"
            speechSynthesisVoiceName = "zh-CN-YunxiNeural"
        }
        assert(speechConfig != null)
        val destFile =
            File(getExternalFilesDir("text-to-speech"), "test-${System.currentTimeMillis()}.wav")
        synthesizer =
            SpeechSynthesizer(speechConfig, AudioConfig.fromWavFileInput(destFile.absolutePath))
        assert(synthesizer != null)
    }

    override fun onDestroy() {
        super.onDestroy()

        // Release speech synthesizer and its dependencies
        synthesizer!!.close()
        speechConfig!!.close()
    }

    fun onSpeechButtonClicked(v: View?) {
        val outputMessage = findViewById<TextView>(R.id.outputMessage)
        val speakText = findViewById<EditText>(R.id.speakText)
        try {
            // Note: this will block the UI thread, so eventually, you want to register for the event
            val result = synthesizer!!.SpeakText(speakText.text.toString())!!
            if (result.reason == ResultReason.SynthesizingAudioCompleted) {
                outputMessage.text = "Speech synthesis succeeded."
            } else if (result.reason == ResultReason.Canceled) {
                val cancellationDetails =
                    SpeechSynthesisCancellationDetails.fromResult(result).toString()
                outputMessage.text = "Error synthesizing. Error detail: " +
                        System.lineSeparator() + cancellationDetails +
                        System.lineSeparator() + "Did you update the subscription info?"
            }
            result.close()
        } catch (ex: Exception) {
            Log.e("SpeechSDKDemo", "unexpected " + ex.message)
            assert(false)
        }
    }

    companion object {
        // Replace below with your own subscription key
        private const val speechSubscriptionKey = BuildConfig.SUBSCRIPTION_KEY

        // Replace below with your own service region (e.g., "westus").
        private const val serviceRegion = "eastus"
    }
}