#pragma once

namespace wavetablesynthesizer {
    enum class WaveTable {
        SINE, TRIANGLE, SQUARE, SAW
    };

    class WaveTableSynthesizer {
    public:
        void play();

        void stop();

        bool isPlaying();

        void setFrequency(float frequencyInHz);

        void setVolume(float volumeInDb);

        void setWaveTable(WaveTable waveTable);

    private:
        bool _isPlaying = false;
    };
}