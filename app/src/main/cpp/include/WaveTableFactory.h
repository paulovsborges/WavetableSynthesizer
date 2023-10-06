#pragma once

#include <vector>

class WaveTable;

namespace wavetablesynthesizer {

    class WaveTableFactory {
    public:
        std::vector<float> getWaveTable(WaveTable waveTable);

    private:
        std::vector<float> sineWaveTable();
        std::vector<float> triangleWaveTable();
        std::vector<float> squareWaveTable();
        std::vector<float> sawWaveTable();

        std::vector<float> _sineWaveTable;
        std::vector<float> _triangleWaveTable;
        std::vector<float> _squareWaveTable;
        std::vector<float> _sawWaveTable;
    };

}
