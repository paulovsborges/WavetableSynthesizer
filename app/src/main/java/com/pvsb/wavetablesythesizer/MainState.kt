package com.pvsb.wavetablesythesizer

data class MainState(
    val frequency: Float = 0f,
    val volume: Float = 0f,
    val frequencyRange: ClosedFloatingPointRange<Float> = 40f..3000f,
    val volumeRange: ClosedFloatingPointRange<Float> = (-60f)..0f,
    val waveTable: WaveTable = WaveTable.SINE,
    val playBtnLabel: Int = R.string.button_label_play
)
