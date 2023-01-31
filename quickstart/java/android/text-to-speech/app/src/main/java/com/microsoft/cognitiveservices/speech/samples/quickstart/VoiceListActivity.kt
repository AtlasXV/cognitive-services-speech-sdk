package com.microsoft.cognitiveservices.speech.samples.quickstart

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.microsoft.cognitiveservices.speech.samples.quickstart.adapter.VoiceListAdapter
import com.microsoft.cognitiveservices.speech.samples.quickstart.databinding.ActivityVoiceListBinding
import com.microsoft.cognitiveservices.speech.samples.quickstart.viewmodel.VoiceListViewModel

/**
 * weiping@atlasv.com
 * 2023/1/31
 */
class VoiceListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVoiceListBinding
    private val viewModel by viewModels<VoiceListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_voice_list)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.rvVoiceList.layoutManager = LinearLayoutManager(this)
        binding.rvVoiceList.adapter = VoiceListAdapter()
    }
}