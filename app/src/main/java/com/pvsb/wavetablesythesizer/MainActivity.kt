package com.pvsb.wavetablesythesizer

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pvsb.wavetablesythesizer.ui.theme.WavetableSythesizerTheme
import com.pvsb.wavetablesythesizer.ui.theme.background
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.synthesizer = LoggingWaveTableSynthesizer()

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        setContent {
            WavetableSythesizerTheme(darkTheme = true) {

                val state = viewModel.state.collectAsState()

                Content(state.value)
            }
        }
    }

    @Composable
    private fun Content(
        state: MainState = MainState()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(background),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SelectionPanels(
                modifier = Modifier
            )
            ControlPanels(
                modifier = Modifier,
                state = state
            )
        }
    }

    @Composable
    private fun SelectionPanels(modifier: Modifier = Modifier) {

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {

            Text(text = stringResource(id = R.string.wavetable), color = Color.White)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                WaveTable.values().map {
                    SelectionButton(
                        label = it.resId,
                        onClick = {
                            viewModel.setWaveTable(it)
                        }
                    )

                }.forEach { model ->
                    SelectionButtons(model = model)
                }
            }
        }
    }

    @Composable
    private fun ControlPanels(
        modifier: Modifier = Modifier,
        state: MainState
    ) {
        Row(
            modifier = modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.7f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                PitchControl(
                    btnLabel = state.playBtnLabel,
                    sliderPos = state.slidePos,
                    frequency = state.frequency,
                    onValueChanged = { pos ->
                        viewModel.setFrequencySliderPos(pos)
                    }
                )
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                VolumeControl(
                    volume = state.volume,
                    volumeRange = state.volumeRange,
                    onVolumeChanged = {
                        viewModel.setVolume(it)
                    }
                )
            }
        }
    }

    @Composable
    private fun PitchControl(
        modifier: Modifier = Modifier,
        btnLabel: Int,
        sliderPos: Float,
        frequency: Float,
        onValueChanged: (Float) -> Unit
    ) {
        Text(text = stringResource(id = R.string.frequency_label), color = Color.White)

        Slider(
            modifier = modifier.fillMaxWidth(0.8f),
            value = sliderPos,
            onValueChange = onValueChanged,
            valueRange = 0f..1f
        )

        Text(text = stringResource(id = R.string.frequency_value, frequency), color = Color.White)

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            viewModel.playClicked()
        }) {
            Text(text = stringResource(id = btnLabel))
        }
    }

    @Composable
    private fun VolumeControl(
        modifier: Modifier = Modifier,
        volume: Float,
        volumeRange: ClosedFloatingPointRange<Float>,
        onVolumeChanged: (Float) -> Unit
    ) {

        val screenHeight = LocalConfiguration.current.screenHeightDp
        val sliderHeight = screenHeight / 4

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                tint = Color.White,
                contentDescription = ""
            )

            Slider(
                value = volume,
                onValueChange = onVolumeChanged,
                modifier = Modifier
                    .height(100.dp)
                    .width(sliderHeight.dp)
                    .rotate(270f),
                valueRange = volumeRange
            )

            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                tint = Color.White,
                contentDescription = ""
            )
        }
    }

    @Composable
    private fun SelectionButtons(
        modifier: Modifier = Modifier,
        model: SelectionButton
    ) {

        Button(
            modifier = modifier,
            onClick = model.onClick
        ) {
            Text(text = stringResource(id = model.label))
        }
    }

    @Preview(widthDp = 1280, heightDp = 720)
    @Composable
    private fun ContentPreview() {
        Content()
    }

    data class SelectionButton(
        val label: Int,
        val onClick: () -> Unit
    )
}