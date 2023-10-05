#include "include/WaveTableSynthesizer.h"
#include "include/Log.h"

namespace wavetablesynthesizer {

    void WaveTableSynthesizer::play() {
        LOGD("play() called");
        _isPlaying = true;
    };

    void WaveTableSynthesizer::stop() {
        LOGD("stop() called");
        _isPlaying = false;
    };

    bool WaveTableSynthesizer::isPlaying() {
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
        LOGD("setWaveTable() called with argument", static_cast<int>(waveTable));
    };
}