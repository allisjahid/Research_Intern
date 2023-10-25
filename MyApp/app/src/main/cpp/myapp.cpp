#include <jni.h>
#include <android/sensor.h>
#include <android/log.h>

extern "C" {

ASensorManager* sensorManager;
ASensorEventQueue* sensorEventQueue;

JNIEXPORT void JNICALL Java_com_example_yourpackage_MainActivity_startSensors(JNIEnv *env, jobject instance) {
    sensorManager = ASensorManager_getInstance();
    const ASensor* proximitySensor = ASensorManager_getDefaultSensor(sensorManager, ASENSOR_TYPE_PROXIMITY);
    const ASensor* accelerometerSensor = ASensorManager_getDefaultSensor(sensorManager, ASENSOR_TYPE_ACCELEROMETER);

    if (proximitySensor) {
        sensorEventQueue = ASensorManager_createEventQueue(sensorManager, ALooper_prepare(ALOOPER_PREPARE_ALLOW_NON_CALLBACKS), 0, nullptr, nullptr);
        ASensorEventQueue_enableSensor(sensorEventQueue, proximitySensor);
        ASensorEventQueue_setEventRate(sensorEventQueue, proximitySensor, 100000);  // Set proximity sensor refresh rate to 100 ms (10 Hz)
    }

    if (accelerometerSensor) {
        ASensorEventQueue_enableSensor(sensorEventQueue, accelerometerSensor);
        ASensorEventQueue_setEventRate(sensorEventQueue, accelerometerSensor, 100000); // Set accelerometer sensor refresh rate to 100 ms (10 Hz)
    }
}

JNIEXPORT void JNICALL Java_com_example_yourpackage_MainActivity_stopSensors(JNIEnv *env, jobject instance) {
    if (sensorEventQueue != nullptr) {
        ASensorEventQueue_disableSensor(sensorEventQueue, proximitySensor);
        ASensorEventQueue_disableSensor(sensorEventQueue, accelerometerSensor);
        ASensorManager_destroyEventQueue(sensorManager, sensorEventQueue);
        sensorEventQueue = nullptr;
    }
}

JNIEXPORT void JNICALL Java_com_example_yourpackage_MainActivity_handleSensorData(JNIEnv *env, jobject instance, jint sensorType, jfloat value) {
    // Handle sensor data here
    // You can use the sensorType to distinguish between proximity and accelerometer data
    // You can use the value variable to access the sensor data
}

}  // extern "C"
