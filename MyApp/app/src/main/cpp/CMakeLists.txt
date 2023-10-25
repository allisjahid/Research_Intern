cmake_minimum_required(VERSION 3.10.2)

project(SensorNative)

add_library(sensornative SHARED SensorInterface.cpp)

# Add necessary sensor libraries
find_library(log-lib log)
find_library(android-lib android)
find_library(sensor-lib sensor)

target_link_libraries(sensornative ${log-lib} ${android-lib} ${sensor-lib})

# Set the Android sensor flag
target_compile_options(sensornative PRIVATE -DANDROID_SENSOR)
