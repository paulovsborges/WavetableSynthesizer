#include <jni.h>
#include <memory>
#include "Log.h"
#include "WaveTableSynthesizer.h"

extern "C" {

JNIEXPORT jlong JNICALL
Java_com_pvsb_wavetablesythesizer_wavetableSynthesizer_NativeWaveTableSynthesizer_create(
        JNIEnv *env, jobject thiz) {
    auto synthesizer = std::make_unique<wavetablesynthesizer::WaveTableSynthesizer>();

    if (not synthesizer) {
        LOGD("Failed to create synthesizer");
        synthesizer.reset(nullptr);
    }

    return reinterpret_cast<jlong>(synthesizer.release());
}

JNIEXPORT void JNICALL
Java_com_pvsb_wavetablesythesizer_wavetableSynthesizer_NativeWaveTableSynthesizer_delete(
        JNIEnv *env, jobject thiz, jlong synthesizerHandle) {

    auto *synthesizer = reinterpret_cast<wavetablesynthesizer::WaveTableSynthesizer*>(synthesizerHandle);

    if (not synthesizer) {
        LOGD("Failed to destroy an uninitialized synthesizer");
    }

    delete synthesizer;
}

JNIEXPORT void JNICALL
Java_com_pvsb_wavetablesythesizer_wavetableSynthesizer_NativeWaveTableSynthesizer_play(
        JNIEnv *env,
        jobject thiz,
        jlong synthesizerHandle) {
    auto *synthesizer = reinterpret_cast<wavetablesynthesizer::WaveTableSynthesizer*>(synthesizerHandle);

    if (synthesizer) {
        synthesizer->play();
    } else {
        LOGD("Synthesizer not created on play method");
    }

}

JNIEXPORT void JNICALL
Java_com_pvsb_wavetablesythesizer_wavetableSynthesizer_NativeWaveTableSynthesizer_stop(JNIEnv *env,
                                                                                       jobject thiz,
                                                                                       jlong synthesizerHandle) {
    auto *synthesizer = reinterpret_cast<wavetablesynthesizer::WaveTableSynthesizer*>(synthesizerHandle);

    if (synthesizer) {
        synthesizer->stop();
    } else {
        LOGD("Synthesizer not created on stop method");
    }
}

JNIEXPORT jboolean JNICALL
Java_com_pvsb_wavetablesythesizer_wavetableSynthesizer_NativeWaveTableSynthesizer_isPlaying(
        JNIEnv *env, jobject thiz, jlong synthesizerHandle) {

    auto *synthesizer = reinterpret_cast<wavetablesynthesizer::WaveTableSynthesizer*>(synthesizerHandle);

    if (synthesizer) {
        return synthesizer->isPlaying();
    } else {
        LOGD("Synthesizer not created on isPlaying method");
    }

    return false;
}

JNIEXPORT void JNICALL
Java_com_pvsb_wavetablesythesizer_wavetableSynthesizer_NativeWaveTableSynthesizer_setFrequency(
        JNIEnv *env, jobject thiz, jlong synthesizerHandle, jfloat frequencyInHz) {
    auto *synthesizer = reinterpret_cast<wavetablesynthesizer::WaveTableSynthesizer*>(synthesizerHandle);

    if (synthesizer) {
        synthesizer->setFrequency(static_cast<float>(frequencyInHz));
    } else {
        LOGD("Synthesizer not created on setFrequency method");
    }
}

JNIEXPORT void JNICALL
Java_com_pvsb_wavetablesythesizer_wavetableSynthesizer_NativeWaveTableSynthesizer_setVolume(
        JNIEnv *env, jobject thiz, jlong synthesizerHandle, jfloat volumeInDb) {
    auto *synthesizer = reinterpret_cast<wavetablesynthesizer::WaveTableSynthesizer*>(synthesizerHandle);

    if (synthesizer) {
        synthesizer->setVolume(static_cast<float>(volumeInDb));
    } else {
        LOGD("Synthesizer not created on setVolume method");
    }
}

JNIEXPORT void JNICALL
Java_com_pvsb_wavetablesythesizer_wavetableSynthesizer_NativeWaveTableSynthesizer_setWaveTable(
        JNIEnv *env, jobject thiz, jlong synthesizerHandle, jint waveTable) {
    auto *synthesizer = reinterpret_cast<wavetablesynthesizer::WaveTableSynthesizer*>(synthesizerHandle);

    const auto nativeWaveTable = static_cast<wavetablesynthesizer::WaveTable>(waveTable);

    if (synthesizer) {
        synthesizer->setWaveTable(nativeWaveTable);
    } else {
        LOGD("Synthesizer not created on setWaveTable method");
    }
}
}

//extern "C"
//JNIEXPORT jlong JNICALL
//Java_com_pvsb_wavetablesythesizer_wavetableSynthesizer_NativeWaveTableSynthesizer_create(
//        JNIEnv *env, jobject thiz) {
//
//    auto synthesizer = std::make_unique<wavetablesynthesizer::WaveTableSynthesizer>();
//
//    if (not synthesizer) {
//        LOGD("Failed to create synthesizer");
//        synthesizer.reset(nullptr);
//    }
//
//    return reinterpret_cast<jlong>(synthesizer.release());
//}