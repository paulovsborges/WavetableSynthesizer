package com.pvsb.wavetablesythesizer

interface WaveTableSynthesizer {
    suspend fun play()
    suspend fun stop()
    suspend fun isPlaying() : Boolean
    suspend fun setFrequency(frequencyInHz: Float)
    suspend fun setVolume(volumeInDb: Float)
    suspend fun setWaveTable(waveTable: WaveTable)
}