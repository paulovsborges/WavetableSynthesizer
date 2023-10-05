package com.pvsb.wavetablesythesizer.wavetableSynthesizer

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.pvsb.wavetablesythesizer.WaveTable
import com.pvsb.wavetablesythesizer.WaveTableSynthesizer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NativeWaveTableSynthesizer : WaveTableSynthesizer, LifecycleEventObserver {

    private var synthesizerHandle: Long = 0
    private val synthesizerMutex = Object()
    private external fun create(): Long
    private external fun delete(synthesizerHandle: Long)
    private external fun play(synthesizerHandle: Long)
    private external fun stop(synthesizerHandle: Long)
    private external fun isPlaying(synthesizerHandle: Long): Boolean
    private external fun setFrequency(synthesizerHandle: Long, frequencyInHz: Float)
    private external fun setVolume(synthesizerHandle: Long, volumeInDb: Float)
    private external fun setWaveTable(synthesizerHandle: Long, waveTable: Int)

    companion object {
        init {
            System.loadLibrary("wavetablesynthesizer")
        }
    }


    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                synchronized(synthesizerMutex) {
                    createNativeHandleIfNotExists()
                }
            }

            Lifecycle.Event.ON_PAUSE -> {
                synchronized(synthesizerMutex) {
                    if (synthesizerHandle == 0L) {
                        return
                    }

                    delete(synthesizerHandle)
                    synthesizerHandle = 0L
                }
            }

            else -> Unit
        }
    }

    private fun createNativeHandleIfNotExists() {
        if (synthesizerHandle != 0L) {
            return
        }

        synthesizerHandle = create()
    }

    override suspend fun play() = withContext(Dispatchers.Default) {
        synchronized(synthesizerMutex) {
            createNativeHandleIfNotExists()
            play(synthesizerHandle)
        }
    }

    override suspend fun stop() = withContext(Dispatchers.Default) {
        synchronized(synthesizerMutex) {
            createNativeHandleIfNotExists()
            stop(synthesizerHandle)
        }
    }

    override suspend fun isPlaying(): Boolean = withContext(Dispatchers.Default) {
        synchronized(synthesizerMutex) {
            createNativeHandleIfNotExists()
            return@withContext isPlaying(synthesizerHandle)
        }
    }

    override suspend fun setFrequency(frequencyInHz: Float) = withContext(Dispatchers.Default) {
        synchronized(synthesizerMutex) {
            createNativeHandleIfNotExists()
            setFrequency(synthesizerHandle, frequencyInHz)
        }
    }

    override suspend fun setVolume(volumeInDb: Float) = withContext(Dispatchers.Default) {
        synchronized(synthesizerMutex) {
            createNativeHandleIfNotExists()
            setVolume(synthesizerHandle, volumeInDb)
        }
    }

    override suspend fun setWaveTable(waveTable: WaveTable) = withContext(Dispatchers.Default) {
        synchronized(synthesizerMutex) {
            createNativeHandleIfNotExists()
            setWaveTable(synthesizerHandle, waveTable.ordinal)
        }
    }
}