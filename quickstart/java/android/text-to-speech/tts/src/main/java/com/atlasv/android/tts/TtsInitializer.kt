package com.atlasv.android.tts

/**
 * weiping@atlasv.com
 * 2023/1/31
 */
object TtsInitializer {
    var config: TtsConfig = TtsConfig()

    fun initSdk(config: TtsConfig) {
        this.config = config
    }
}