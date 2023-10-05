package com.pvsb.wavetablesythesizer

data class MainState(
    val slidePos : Float = 0F,
    val frequency: Float = 300F,
    val volume: Float = -24F,
    val frequencyRange: ClosedFloatingPointRange<Float> = 40f..3000f,
    val volumeRange: ClosedFloatingPointRange<Float> = (-60f)..0f,
    val waveTable: WaveTable = WaveTable.SINE,
    val playBtnLabel: Int = R.string.button_label_play
)
