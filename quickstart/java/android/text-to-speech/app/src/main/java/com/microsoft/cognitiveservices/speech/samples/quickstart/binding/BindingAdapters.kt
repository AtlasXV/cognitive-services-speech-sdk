package com.microsoft.cognitiveservices.speech.samples.quickstart.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.cognitiveservices.speech.samples.quickstart.adapter.VoiceListAdapter
import com.microsoft.cognitiveservices.speech.samples.quickstart.data.VoiceInfoData

/**
 * weiping@atlasv.com
 * 2023/1/31
 */
object BindingAdapters {

    @JvmStatic
    @BindingAdapter("voiceList")
    fun setVoiceList(view: RecyclerView, data: List<VoiceInfoData>) {
        (view.adapter as? VoiceListAdapter)?.submitList(data)
    }
}