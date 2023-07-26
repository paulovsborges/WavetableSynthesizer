package com.pvsb.wavetablesythesizer

import android.util.Log

class LoggingWaveTableSynthesizer : WaveTableSynthesizer {

    private companion object {
        val TAG = this::class.java.simpleName
    }

    private var isPlaying = false

    override suspend fun play() {
        Log.d(TAG, "play")
    }

    override suspend fun stop() {
        Log.d(TAG, "stop: ")
    }

    override suspend fun isPlaying(): Boolean {
        Log.d(TAG, "isPlaying: $isPlaying")
        return isPlaying
    }

    override suspend fun setFrequency(frequencyInHz: Float) {
        Log.d(TAG, "setFrequency: $frequencyInHz")
    }

    override suspend fun setVolume(volumeInDb: Float) {
        Log.d(TAG, "setVolume: $volumeInDb")
    }

    override suspend fun setWaveTable(waveTable: WaveTable) {
        Log.d(TAG, "setWaveTable: ${waveTable.name}")
    }
}