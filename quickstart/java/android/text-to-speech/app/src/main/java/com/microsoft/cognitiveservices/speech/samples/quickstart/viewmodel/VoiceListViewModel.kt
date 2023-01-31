package com.microsoft.cognitiveservices.speech.samples.quickstart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atlasv.android.tts.SpeechSynthesizerFactory
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer
import com.microsoft.cognitiveservices.speech.samples.quickstart.data.VoiceInfoData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * weiping@atlasv.com
 * 2023/1/31
 */
class VoiceListViewModel : ViewModel() {

    val loading = MutableStateFlow(false)

    private val synthesizer: SpeechSynthesizer by lazy {
        SpeechSynthesizerFactory.createSimpleSpeechSynthesizer()
    }

    val voiceList = MutableStateFlow<List<VoiceInfoData>>(emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loading.value = true
            synthesizer.use {
                voiceList.value = kotlin.runCatching {
                    it.voicesAsync.get().voices
                }.getOrNull().orEmpty().map { info ->
                    VoiceInfoData.fromInfo(info)
                }
            }
        }.invokeOnCompletion {
            loading.value = false
        }
    }

}