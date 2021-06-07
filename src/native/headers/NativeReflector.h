#ifndef NATIVE_REFLECTOR_H
#define NATIVE_REFLECTOR_H

#include <jni.h>

void throwJ(JNIEnv *env, char *msg);

#define JVM_HOTSPOT 0
#define JVM_OPENJ9 1

#endif
