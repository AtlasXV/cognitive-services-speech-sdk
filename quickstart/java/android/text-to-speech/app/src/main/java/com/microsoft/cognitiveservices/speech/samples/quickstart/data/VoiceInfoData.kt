package com.microsoft.cognitiveservices.speech.samples.quickstart.data

import androidx.recyclerview.widget.DiffUtil
import com.microsoft.cognitiveservices.speech.SynthesisVoiceGender
import com.microsoft.cognitiveservices.speech.SynthesisVoiceType
import com.microsoft.cognitiveservices.speech.VoiceInfo

/**
 * weiping@atlasv.com
 * 2023/1/31
 */
data class VoiceInfoData(
    val name: String,
    val locale: String,
    val shortName: String,
    val localName: String,
    val voiceType: SynthesisVoiceType,
    val gender: SynthesisVoiceGender,
) {

    val desc
        get() = "name: $name\n" +
                "localName: $localName, locale: $locale\n" +
                "shortName:$shortName, gender: $gender\n" +
                "voiceType: $voiceType"

    companion object {
        fun fromInfo(info: VoiceInfo): VoiceInfoData {
            return info.use {
                VoiceInfoData(
                    name = it.name,
                    locale = it.locale,
                    shortName = it.shortName,
                    localName = it.localName,
                    voiceType = it.voiceType,
                    gender = it.gender
                )
            }
        }
    }
}

object VoiceInfoDataDiff : DiffUtil.ItemCallback<VoiceInfoData>() {
    override fun areItemsTheSame(oldItem: VoiceInfoData, newItem: VoiceInfoData): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: VoiceInfoData, newItem: VoiceInfoData): Boolean {
        return oldItem == newItem
    }
}