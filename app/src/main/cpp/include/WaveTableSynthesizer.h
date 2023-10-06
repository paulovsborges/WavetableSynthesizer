#pragma once

#include <memory>
#include "WaveTable.h"
#include <mutex>
#include "WaveTableFactory.h"

namespace wavetablesynthesizer {


    class WaveTableOscillator;

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
        std::atomic<bool> _isPlaying = false;
        std::mutex _mutex;
        std::shared_ptr<WaveTableOscillator> _oscillator;
        std::unique_ptr<AudioPlayer> _audioPlayer;
        WaveTableFactory _waveTableFactory;
        WaveTable _currentWaveTable{WaveTable::SINE};
    };
}