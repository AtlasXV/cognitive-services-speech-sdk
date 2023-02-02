package com.microsoft.cognitiveservices.speech.samples.quickstart.compose.voicelist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.microsoft.cognitiveservices.speech.samples.quickstart.viewmodel.VoiceListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.microsoft.cognitiveservices.speech.samples.quickstart.data.VoiceInfoData

/**
 * weiping@atlasv.com
 * 2023/2/2
 */

@Composable
fun VoiceListScreen(viewModel: VoiceListViewModel = viewModel()) {
    if (viewModel.loading.collectAsState().value) {
        LoadingScreen()
    } else {
        VoiceListScreen(viewModel.voiceList.collectAsState())
    }
}

@Composable
fun VoiceListScreen(dataList: State<List<VoiceInfoData>>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(1)) {
            items(count = dataList.value.size) { index ->
                VoiceItem(dataList.value[index])
            }
        }
    }
}

@Composable
fun VoiceItem(item: VoiceInfoData) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Divider(
            color = Color.Gray, thickness = 0.5.dp
        )
        Text(
            text = item.desc, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator()
    }
}