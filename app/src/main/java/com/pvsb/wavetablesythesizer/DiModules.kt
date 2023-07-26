package com.pvsb.wavetablesythesizer

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SynthesizerModule {

    @Provides
    @ViewModelScoped
    fun provides(): WaveTableSynthesizer {
        return LoggingWaveTableSynthesizer()
    }
}