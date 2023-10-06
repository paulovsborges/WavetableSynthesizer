#include <cmath>
#include "include/WaveTableSynthesizer.h"
#include "include/Log.h"
#include "OboeAudioPlayer.h"
#include "WaveTableOscilator.h"

namespace wavetablesynthesizer {

    WaveTableSynthesizer::WaveTableSynthesizer()
            : _oscillator{std::make_shared<WaveTableOscillator>(
            _waveTableFactory.getWaveTable(_currentWaveTable),
            sampleRate)},
              _audioPlayer{std::make_unique<OboeAudioPlayer>(_oscillator, sampleRate)} {

    }

    WaveTableSynthesizer::~WaveTableSynthesizer() = default;

    void WaveTableSynthesizer::play() {
        LOGD("play() called");

        std::lock_guard<std::mutex> lock(_mutex);


        const auto result = _audioPlayer->play();
        if (result == 0) {
            _isPlaying = true;
        } else {
            LOGD("Could not start playback");
        }
    };

    void WaveTableSynthesizer::stop() {
        LOGD("stop() called");

        std::lock_guard<std::mutex> lock(_mutex);

        _audioPlayer->stop();
        _isPlaying = false;
    };

    bool WaveTableSynthesizer::isPlaying() const {
        LOGD("isPlaying() called");
        return _isPlaying;
    };

    void WaveTableSynthesizer::setFrequency(float frequencyInHz) {
        LOGD("setFrequency() called with %.f Hz argument", frequencyInHz);

        _oscillator->setFrequency(frequencyInHz);
    }

    float dbToAmplitude(float db) {
        return std::pow(10.f, db / 20.f);
    }

    void WaveTableSynthesizer::setVolume(float volumeInDb) {
        LOGD("setVolume() called with %.f Db argument", volumeInDb);

        const auto amplitude = dbToAmplitude(volumeInDb);

        _oscillator->setAmplitude(amplitude);
    }

    void WaveTableSynthesizer::setWaveTable(WaveTable waveTable) {
        LOGD("setWaveTable() called with %.d argument", static_cast<int>(waveTable));

        if (_currentWaveTable != waveTable) {
            _currentWaveTable = waveTable;
            _oscillator->setWaveTable(_waveTableFactory.getWaveTable(waveTable));
        }
    }
}