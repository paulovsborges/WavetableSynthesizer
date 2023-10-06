#pragma once

#include "AudioSource.h"
#include <vector>

namespace wavetablesynthesizer {

    class WaveTableOscillator : public AudioSource {
    public:
        WaveTableOscillator() = default;

        WaveTableOscillator(std::vector<float> waveTable, float sampleRate);

        float getSample() override;

        void onPlaybackStopped() override;

        virtual void setFrequency(float frequency);

        virtual void setAmplitude(float newAmplitude);

        virtual void setWaveTable(const std::vector<float> &waveTable);

    private:
        float interpolateLinearly() const;

        void swapWaveTableIfNecessary();

        float index = 0.f;
        std::atomic<float> indexIncrement{0.f};
        std::vector<float> waveTable;
        float sampleRate;
        std::atomic<float> amplitude{1.f};
        std::atomic<bool> swapWaveTable{false};
        std::vector<float> waveTableToSwap;
        std::atomic<bool> waveTableIsBeingSwapped{false};
    };

    class A4Oscillator : public WaveTableOscillator {
    public:
        explicit A4Oscillator(float sampleRate);

        float getSample() override;

        void setFrequency(float frequency) override {};

        void setAmplitude(float newAmplitude) override {};

        void onPlaybackStopped() override;

        void setWaveTable(const std::vector<float> &wavetable) override {};

    private:
        float _phase{0.f};
        float _phaseIncrement{0.f};
    };
}