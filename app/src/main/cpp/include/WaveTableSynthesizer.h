#pragma once

#include <memory>

namespace wavetablesynthesizer {
    enum class WaveTable {
        SINE, TRIANGLE, SQUARE, SAW
    };

    class AudioSource;

    class AudioPlayer;

    constexpr auto sampleRate = 48000;

    class WaveTableSynthesizer {
    public:

        WaveTableSynthesizer();

        ~WaveTableSynthesizer();

        void play();

        void stop();

        bool isPlaying() const;

        void setFrequency(float frequencyInHz);

        void setVolume(float volumeInDb);

        void setWaveTable(WaveTable waveTable);

    private:
        bool _isPlaying = false;
        std::shared_ptr<AudioSource> _oscillator;
        std::unique_ptr<AudioPlayer> _audioPlayer;
    };
}