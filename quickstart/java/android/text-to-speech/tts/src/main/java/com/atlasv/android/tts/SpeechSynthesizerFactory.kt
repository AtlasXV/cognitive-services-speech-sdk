package com.atlasv.android.tts

import com.microsoft.cognitiveservices.speech.SpeechConfig
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer

/**
 * weiping@atlasv.com
 * 2023/1/31
 */
object SpeechSynthesizerFactory {
    fun createSpeechConfig(): SpeechConfig {
        return SpeechConfig.fromSubscription(
            TtsInitializer.config.speechSubscriptionKey,
            TtsInitializer.config.serviceRegion
        )
    }

    fun createSimpleSpeechSynthesizer(): SpeechSynthesizer {
        return createSpeechConfig().use { speechConfig ->
            SpeechSynthesizer(speechConfig)
        }
    }
}