package com.microsoft.cognitiveservices.speech.samples.quickstart

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import com.microsoft.cognitiveservices.speech.samples.quickstart.compose.voicelist.VoiceListScreen

/**
 * weiping@atlasv.com
 * 2023/1/31
 */
class VoiceListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                VoiceListScreen()
            }
        }
    }
}