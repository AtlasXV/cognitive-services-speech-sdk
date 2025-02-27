//
// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE.md file in the project root for full license information.
//
// <code>
package com.microsoft.cognitiveservices.speech.samples.quickstart

import android.Manifest.permission
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.atlasv.android.tts.TtsConfig
import com.atlasv.android.tts.TtsInitializer
import com.microsoft.cognitiveservices.speech.*
import com.microsoft.cognitiveservices.speech.audio.AudioConfig
import com.microsoft.cognitiveservices.speech.samples.quickstart.config.ConfigConstants.SERVICE_REGION
import com.microsoft.cognitiveservices.speech.samples.quickstart.config.ConfigConstants.SPEECH_SUBSCRIPTION_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.Future


/**
 * [如何基于文本合成语音](https://learn.microsoft.com/zh-cn/azure/cognitive-services/speech-service/how-to-speech-synthesis?tabs=browserjs%2Cterminal&pivots=programming-language-java)
 */
class MainActivity : AppCompatActivity() {
    private var speechConfig: SpeechConfig? = null
    private var synthesizer: SpeechSynthesizer? = null
    private var audioFile: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TtsInitializer.initSdk(
            config = TtsConfig(BuildConfig.SUBSCRIPTION_KEY, "eastus")
        )

        setContentView(R.layout.activity_main)

        // Note: we need to request the permissions
        val requestCode = 5 // unique code for the permission request
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(permission.INTERNET),
            requestCode
        )

        // Initialize speech synthesizer and its dependencies
        speechConfig =
            SpeechConfig.fromSubscription(SPEECH_SUBSCRIPTION_KEY, SERVICE_REGION).apply {
                speechSynthesisVoiceName = "zh-TW-HsiaoYuNeural"
                // 以mp3格式输出。
//                setSpeechSynthesisOutputFormat(SpeechSynthesisOutputFormat.Audio16Khz128KBitRateMonoMp3)
            }
        assert(speechConfig != null)
        audioFile =
            File(
                getExternalFilesDir("text-to-speech"),
                "${speechConfig?.speechSynthesisVoiceName}-${System.currentTimeMillis()}.wav"
            )
        synthesizer =
            SpeechSynthesizer(speechConfig, AudioConfig.fromWavFileInput(audioFile!!.absolutePath))

        findViewById<View>(R.id.btn_voice_list).setOnClickListener {
            startActivity(Intent(this, VoiceListActivity::class.java))
        }
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
                outputMessage.text =
                    "Speech synthesis succeeded: $audioFile(${audioFile?.length()})"
            } else if (result.reason == ResultReason.Canceled) {
                val cancellationDetails =
                    SpeechSynthesisCancellationDetails.fromResult(result).toString()
                outputMessage.text = "Error synthesizing. Error detail: " +
                        System.lineSeparator() + cancellationDetails +
                        System.lineSeparator() + "Did you update the subscription info?"
            }
            result.close()
            audioToText()
        } catch (ex: Exception) {
            Log.e("SpeechSDKDemo", "unexpected " + ex.message)
            assert(false)
        }
    }

    /**
     * [从文件中识别语音](https://learn.microsoft.com/zh-cn/azure/cognitive-services/speech-service/how-to-recognize-speech?pivots=programming-language-java#recognize-speech-from-a-file)
     */
    private fun audioToText() {
        GlobalScope.launch(Dispatchers.IO) {
            Log.d(
                "SpeechSDKDemo",
                "Recognizing Text from file: $audioFile(${audioFile!!.length()})"
            )
            val audioConfig = AudioConfig.fromWavFileInput(audioFile!!.absolutePath)
            val config = SpeechConfig.fromSubscription(SPEECH_SUBSCRIPTION_KEY, SERVICE_REGION)
            config.speechRecognitionLanguage = "zh-CN"
            val recognizer = kotlin.runCatching {
                // 仅支持wav格式
                SpeechRecognizer(config, audioConfig)
            }.getOrElse {
                Log.e("SpeechSDKDemo", "Can not create SpeechRecognizer", it)
                null
            } ?: return@launch
            val task: Future<SpeechRecognitionResult> = recognizer.recognizeOnceAsync()
            val result: SpeechRecognitionResult = task.get()
            Log.d("SpeechSDKDemo", "RECOGNIZED: Text=" + result.text)
        }

    }
}