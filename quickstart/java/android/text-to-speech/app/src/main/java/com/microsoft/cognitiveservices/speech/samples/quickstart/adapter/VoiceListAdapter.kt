package com.microsoft.cognitiveservices.speech.samples.quickstart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.atlasv.android.mediaeditor.component.base.ui.adapter.DataBoundListAdapter
import com.microsoft.cognitiveservices.speech.samples.quickstart.data.VoiceInfoData
import com.microsoft.cognitiveservices.speech.samples.quickstart.data.VoiceInfoDataDiff
import com.microsoft.cognitiveservices.speech.samples.quickstart.databinding.ItemVoiceInfoBinding

/**
 * weiping@atlasv.com
 * 2023/1/31
 */
class VoiceListAdapter :
    DataBoundListAdapter<VoiceInfoData, ItemVoiceInfoBinding>(VoiceInfoDataDiff) {
    override fun bind(binding: ItemVoiceInfoBinding, item: VoiceInfoData) {
        binding.item = item
    }

    override fun createBinding(parent: ViewGroup, viewType: Int): ItemVoiceInfoBinding {
        return ItemVoiceInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }
}