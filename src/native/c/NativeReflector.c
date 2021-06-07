#include "NativeReflector.h"

void throwJ(JNIEnv *env, char *msg) {
    if (!(*env)->ExceptionOccurred(env)) {
        jthrowable exClass = (*env)->FindClass(env, "java/lang/InternalError");
        (*env)->ThrowNew(env, exClass, msg);
    }
}