#include "include/WaveTableSynthesizer.h"
#include "include/Log.h"
#include "OboeAudioPlayer.h"
#include "WaveTableOscilator.h"

namespace wavetablesynthesizer {

    WaveTableSynthesizer::WaveTableSynthesizer()
            : _oscillator{std::make_shared<A4Oscillator>(sampleRate)},
              _audioPlayer{std::make_unique<OboeAudioPlayer>(_oscillator, sampleRate)} {

    }

    WaveTableSynthesizer::~WaveTableSynthesizer() = default;

    void WaveTableSynthesizer::play() {
        LOGD("play() called");

        const auto result = _audioPlayer->play();
        if (result == 0) {
            _isPlaying = true;
        } else {
            LOGD("Could not start playback");
        }
    };

    void WaveTableSynthesizer::stop() {
        LOGD("stop() called");
        _audioPlayer->stop();
        _isPlaying = false;
    };

    bool WaveTableSynthesizer::isPlaying() const {
        LOGD("isPlaying() called");
        return _isPlaying;
    };

    void WaveTableSynthesizer::setFrequency(float frequencyInHz) {
        LOGD("setFrequency() called with %.f Hz argument", frequencyInHz);
    };

    void WaveTableSynthesizer::setVolume(float volumeInDb) {
        LOGD("setVolume() called with %.f Db argument", volumeInDb);
    };

    void WaveTableSynthesizer::setWaveTable(WaveTable waveTable) {
        LOGD("setWaveTable() called with %.d argument", static_cast<int>(waveTable));
    };
}