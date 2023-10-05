package com.pvsb.wavetablesythesizer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.exp
import kotlin.math.ln

class MainViewModel : ViewModel() {

    var synthesizer: WaveTableSynthesizer? = null
        set(value) {
            field = value
            applyParameters()
        }

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    fun applyParameters() {
        viewModelScope.launch {
            synthesizer?.setFrequency(_state.value.frequency)
            synthesizer?.setVolume(_state.value.volume)
            synthesizer?.setWaveTable(_state.value.waveTable)
            updateButtonLabel()
            updateSliderPos()
        }
    }

    private fun updateSliderPos() {
        _state.update {
            it.copy(
                slidePos = sliderPositionFromFrequencyInHz(_state.value.frequency)
            )
        }
    }

    fun setWaveTable(table: WaveTable) {
        viewModelScope.launch {
            synthesizer?.setWaveTable(table)
            _state.update { it.copy(waveTable = table) }
        }
    }

    fun setFrequencySliderPos(pos: Float) {
        viewModelScope.launch {
            val frequencyInHz = frequencyInHzFromSliderPos(pos)

            synthesizer?.setFrequency(frequencyInHz)
            _state.update {
                it.copy(
                    frequency = frequencyInHz
                )
            }
            updateSliderPos()
        }
    }

    fun setVolume(volumeInDb: Float) {
        viewModelScope.launch {
//            val frequencyInHz = frequencyInHzFromSliderPos(pos)

            synthesizer?.setVolume(volumeInDb)
            _state.update { it.copy(volume = volumeInDb) }
        }
    }

    private fun frequencyInHzFromSliderPos(pos: Float): Float {
        val rangePos = linearToExponential(pos)
        return valueFromRangePos(_state.value.frequencyRange, rangePos)
    }

    fun sliderPositionFromFrequencyInHz(frequencyInHz: Float): Float {
        val rangePos = rangePositionFromValue(_state.value.frequencyRange, frequencyInHz)
        return exponentialToLinear(rangePos)
    }

    fun playClicked() {
        viewModelScope.launch {
            val isPlaying = synthesizer?.isPlaying() == true

            if (isPlaying) {
                synthesizer?.stop()
            } else {
                synthesizer?.play()
            }

            updateButtonLabel()
        }
    }

    private suspend fun updateButtonLabel() {
        val isPlaying = synthesizer?.isPlaying() == true

        if (isPlaying) {
            _state.update { it.copy(playBtnLabel = R.string.button_label_stop) }
        } else {
            _state.update { it.copy(playBtnLabel = R.string.button_label_play) }
        }
    }

    companion object {
        private const val MIN_VALUE = 0.001f

        fun linearToExponential(value: Float): Float {
            assert(value in 0f..1f)

            if (value < MIN_VALUE) {
                return 0f
            }

            return exp(ln(MIN_VALUE) - ln(MIN_VALUE) * value)
        }

        fun valueFromRangePos(range: ClosedFloatingPointRange<Float>, rangePos: Float): Float {
            return range.start + (range.endInclusive - range.start) * rangePos
        }

        fun rangePositionFromValue(range: ClosedFloatingPointRange<Float>, value: Float): Float {
            assert(value in range)

            return (value - range.start) / (range.endInclusive - range.start)
        }

        fun exponentialToLinear(rangePos: Float): Float {
            assert(rangePos in 0f..1f)

            if (rangePos < MIN_VALUE) {
                return rangePos
            }

            return (ln(rangePos) - ln(MIN_VALUE)) / (-ln(MIN_VALUE))
        }
    }
}