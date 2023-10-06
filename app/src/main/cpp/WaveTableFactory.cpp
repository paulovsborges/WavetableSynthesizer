#include "include/WaveTable.h"
#include <cmath>
#include "MathConstants.h"
#include "WaveTableFactory.h"

namespace {
    constexpr auto WAVE_TABLE_LENGTH = 256;

    std::vector<float> generateSineWaveTable() {
        auto sineWaveTable = std::vector<float>(WAVE_TABLE_LENGTH);
        for (int i = 0; i < WAVE_TABLE_LENGTH; ++i) {
            sineWaveTable[i] = std::sin(
                    2 * wavetablesynthesizer::PI * static_cast<float >(i) / WAVE_TABLE_LENGTH
            );
        }

        return sineWaveTable;
    }

    std::vector<float> generateTriangleWaveTable() {
        auto triangleWaveTable = std::vector<float>(WAVE_TABLE_LENGTH, 0.f);

        constexpr auto HARMONICS_COUNT = 13;

        for (auto k = 0; k <= HARMONICS_COUNT; ++k) {
            for (auto j = 0; j < WAVE_TABLE_LENGTH; ++j) {
                const auto phase = 2.f * wavetablesynthesizer::PI * j / WAVE_TABLE_LENGTH;

                // Calculating the amplitude of the desired harmonics for this wave table
                // and adding it to the vector

                triangleWaveTable[j] += 8.f / std::pow(wavetablesynthesizer::PI, 2.f)
                                        * std::pow(-1.f, k) * std::pow(2 * k - 1, -2.f)
                                        * std::sin((2.f * k - 1.f) * phase);
            }
        }

        return triangleWaveTable;
    }

    std::vector<float> generateSquareWaveTable() {
        auto squareWaveTable = std::vector<float>(WAVE_TABLE_LENGTH, 0.f);

        constexpr auto HARMONICS_COUNT = 7;

        for (auto k = 0; k <= HARMONICS_COUNT; ++k) {
            for (auto j = 0; j < WAVE_TABLE_LENGTH; ++j) {
                const auto phase = 2.f * wavetablesynthesizer::PI * j / WAVE_TABLE_LENGTH;
                squareWaveTable[j] += 4.f / wavetablesynthesizer::PI * std::pow(2.f * k - 1.f, -1.f)
                                      * std::sin((2.f * k - 1.f) * phase);
            }
        }

        return squareWaveTable;
    }


    std::vector<float> generateSawWaveTable() {

        auto sawWaveTable = std::vector<float>(WAVE_TABLE_LENGTH, 0.f);

        constexpr auto HARMONICS_COUNT = 26;


        for (auto k = 1; k <= HARMONICS_COUNT; ++k) {
            for (auto j = 0; j < WAVE_TABLE_LENGTH; ++j) {
                const auto phase = 2.f * wavetablesynthesizer::PI * 1.f * j / WAVE_TABLE_LENGTH;

                sawWaveTable[j] += 2.f / wavetablesynthesizer::PI * std::pow(-1.f, k) * std::pow(k, -1.f)
                        * std::sin(k * phase);
            }
        }

        return sawWaveTable;
    }

    template<typename F>
    std::vector<float> generateWaveTableOnce(std::vector<float> &waveTable, F &&generator) {
        if (waveTable.empty()) {
            waveTable = generator();
        }

        return waveTable;
    }
}

namespace wavetablesynthesizer {
    std::vector<float> WaveTableFactory::getWaveTable(WaveTable waveTable) {
        switch (waveTable) {
            case WaveTable::SINE:
                return sineWaveTable();
            case WaveTable::TRIANGLE:
                return triangleWaveTable();
            case WaveTable::SQUARE:
                return squareWaveTable();
            case WaveTable::SAW:
                return sawWaveTable();
            default:
                return std::vector<float>(WAVE_TABLE_LENGTH, 0.F);

        }
    }

    std::vector<float> WaveTableFactory::sineWaveTable() {
        return generateWaveTableOnce(_sineWaveTable, &generateSineWaveTable);
    }

    std::vector<float> WaveTableFactory::triangleWaveTable() {
        return generateWaveTableOnce(_triangleWaveTable, &generateTriangleWaveTable);
    }

    std::vector<float> WaveTableFactory::squareWaveTable() {
        return generateWaveTableOnce(_squareWaveTable, &generateSquareWaveTable);
    }

    std::vector<float> WaveTableFactory::sawWaveTable() {
        return generateWaveTableOnce(_sawWaveTable, &generateSawWaveTable);
    }
}