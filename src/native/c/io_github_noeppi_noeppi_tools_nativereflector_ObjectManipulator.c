#include "NativeReflector.h"

#include "io_github_noeppi_noeppi_tools_nativereflector_ObjectManipulator.h"

#define LOAD_F const char *fnameC = (*env)->GetStringUTFChars(env, fname, NULL); \
               const char *fsigC = (*env)->GetStringUTFChars(env, fsig, NULL)

#define GET_F jfieldID fid = (*env)->GetFieldID(env, fclass, fnameC, fsigC); \
              if (fid == NULL) { \
                  throwJ(env, "Failed to set field: Field not found."); \
                  return; \
              }

#define GET_SF jfieldID fid = (*env)->GetStaticFieldID(env, fclass, fnameC, fsigC); \
               if (fid == NULL) { \
                   throwJ(env, "Failed to set static field: Field not found."); \
                   return; \
               }

#define FINALIZE_F (*env)->ReleaseStringUTFChars(env, fname, fnameC); \
                   (*env)->ReleaseStringUTFChars(env, fsig, fsigC)

#define SET_F(actual) LOAD_F; GET_F; actual; FINALIZE_F
#define SET_SF(actual) LOAD_F; GET_SF; actual; FINALIZE_F

JNIEXPORT jobject JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_createEmptyObject0(JNIEnv *env, jclass class, jclass cls) {
    jobject obj = (*env)->AllocObject(env, cls);
    if (obj == NULL) {
        throwJ(env, "Failed to allocate object.");
    }
    return obj;
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_setFieldL0(JNIEnv *env, jclass class, jobject instance, jclass fclass, jstring fname, jstring fsig, jobject value) {
    SET_F((*env)->SetObjectField(env, instance, fid, value));
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_setFieldZ0(JNIEnv *env, jclass class, jobject instance, jclass fclass, jstring fname, jstring fsig, jboolean value) {
    SET_F((*env)->SetBooleanField(env, instance, fid, value));
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_setFieldB0(JNIEnv *env, jclass class, jobject instance, jclass fclass, jstring fname, jstring fsig, jbyte value) {
    SET_F((*env)->SetByteField(env, instance, fid, value));
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_setFieldC0(JNIEnv *env, jclass class, jobject instance, jclass fclass, jstring fname, jstring fsig, jchar value) {
    SET_F((*env)->SetCharField(env, instance, fid, value));
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_setFieldS0(JNIEnv *env, jclass class, jobject instance, jclass fclass, jstring fname, jstring fsig, jshort value) {
    SET_F((*env)->SetShortField(env, instance, fid, value));
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_setFieldI0(JNIEnv *env, jclass class, jobject instance, jclass fclass, jstring fname, jstring fsig, jint value) {
    SET_F((*env)->SetIntField(env, instance, fid, value));
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_setFieldJ0(JNIEnv *env, jclass class, jobject instance, jclass fclass, jstring fname, jstring fsig, jlong value) {
    SET_F((*env)->SetLongField(env, instance, fid, value));
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_setFieldF0(JNIEnv *env, jclass class, jobject instance, jclass fclass, jstring fname, jstring fsig, jfloat value) {
    SET_F((*env)->SetFloatField(env, instance, fid, value));
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_setFieldD0(JNIEnv *env, jclass class, jobject instance, jclass fclass, jstring fname, jstring fsig, jdouble value) {
    SET_F((*env)->SetDoubleField(env, instance, fid, value));
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_setStaticFieldL0(JNIEnv *env, jclass class, jclass fclass, jstring fname, jstring fsig, jobject value) {
    SET_SF((*env)->SetStaticObjectField(env, fclass, fid, value));
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_setStaticFieldZ0(JNIEnv *env, jclass class, jclass fclass, jstring fname, jstring fsig, jboolean value) {
    SET_SF((*env)->SetStaticBooleanField(env, fclass, fid, value));
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_setStaticFieldB0(JNIEnv *env, jclass class, jclass fclass, jstring fname, jstring fsig, jbyte value) {
    SET_SF((*env)->SetStaticByteField(env, fclass, fid, value));
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_setStaticFieldC0(JNIEnv *env, jclass class, jclass fclass, jstring fname, jstring fsig, jchar value) {
    SET_SF((*env)->SetStaticCharField(env, fclass, fid, value));
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_setStaticFieldS0(JNIEnv *env, jclass class, jclass fclass, jstring fname, jstring fsig, jshort value) {
    SET_SF((*env)->SetStaticShortField(env, fclass, fid, value));
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_setStaticFieldI0(JNIEnv *env, jclass class, jclass fclass, jstring fname, jstring fsig, jint value) {
    SET_SF((*env)->SetStaticIntField(env, fclass, fid, value));
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_setStaticFieldJ0(JNIEnv *env, jclass class , jclass fclass, jstring fname , jstring fsig, jlong value) {
    SET_SF((*env)->SetStaticLongField(env, fclass, fid, value));
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_setStaticFieldF0(JNIEnv *env, jclass class, jclass fclass, jstring fname, jstring fsig, jfloat value) {
    SET_SF((*env)->SetStaticFloatField(env, fclass, fid, value));
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_setStaticFieldD0(JNIEnv *env, jclass class, jclass fclass, jstring fname, jstring fsig, jdouble value) {
    SET_SF((*env)->SetStaticDoubleField(env, fclass, fid, value));
}

JNIEXPORT jobject JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ObjectManipulator_createEnumConstant0(JNIEnv *env, jclass class, jclass cls, jstring clsSig, jstring clsSigArray, jstring name, jint javaVM) {
    const char *arr_sig = (*env)->GetStringUTFChars(env, clsSigArray, NULL);
    jobject instance = NULL;
    jfieldID valuesField = (*env)->GetStaticFieldID(env, cls, "$VALUES", arr_sig);
    if (valuesField == NULL) {
        throwJ(env, "Can't create enum constant: Value array field not found.");
    } else {
        jarray values = (*env)->GetStaticObjectField(env, cls, valuesField);
        if (values == NULL) {
            throwJ(env, "Can't create enum constant: Value array field is null.");
        } else {
            jint ordinal = (*env)->GetArrayLength(env, values);
            // Before creating the instance, clear the directory
            // (Map that maps names to enums in the class object)
            // Setting it to `null` will recalculate it the next time
            // Also clear the shared constants array
            jclass classClass = (*env)->FindClass(env, "java/lang/Class");
            if (classClass == NULL) {
                throwJ(env, "Can't create enum constant: class java/lang/Class not found.");
            } else {
                jboolean notFailed = JNI_TRUE;
                if (javaVM == JVM_HOTSPOT) {
                    jfieldID sharedField = (*env)->GetFieldID(env, classClass, "enumConstants", "[Ljava/lang/Object;");
                    jfieldID directoryField = (*env)->GetFieldID(env, classClass, "enumConstantDirectory", "Ljava/util/Map;");
                    if (sharedField == NULL) {
                        throwJ(env, "Can't create enum constant: field enumConstants [Ljava/lang/Object; in class java/lang/Class not found.");
                        notFailed = JNI_FALSE;
                    } else if (directoryField == NULL) {
                        throwJ(env, "Can't create enum constant: field enumConstantDirectory Ljava/util/Map; in class java/lang/Class not found.");
                        notFailed = JNI_FALSE;
                    } else {
                        (*env)->SetObjectField(env, cls, sharedField, NULL);
                        (*env)->SetObjectField(env, cls, directoryField, NULL);
                    }
                } else if (javaVM == JVM_OPENJ9) {
                    jfieldID varsField = (*env)->GetFieldID(env, classClass, "enumVars", "Ljava/lang/Class$EnumVars;");
                    if (varsField == NULL) {
                        throwJ(env,"Can't create enum constant: field enumVars Ljava/lang/Class$EnumVars; in class java/lang/Class not found.");
                        notFailed = JNI_FALSE;
                    } else {
                        (*env)->SetObjectField(env, cls, varsField, NULL);
                    }
                } else {
                    throwJ(env,"Can't create enum constant: Unknown Java VM.");
                    notFailed = JNI_FALSE;
                }
                if (notFailed == JNI_TRUE) {
                    instance = (*env)->AllocObject(env, cls);
                    jclass classEnum = (*env)->FindClass(env, "java/lang/Enum");
                    if (classEnum == NULL) {
                        throwJ(env, "Can't create enum constant: class java/lang/Enum not found.");
                    } else {
                        jfieldID ordinalField = (*env)->GetFieldID(env, classEnum, "ordinal", "I");
                        jfieldID nameField = (*env)->GetFieldID(env, classEnum, "name", "Ljava/lang/String;");
                        if (ordinalField == NULL || nameField == NULL) {
                            throwJ(env, "Can't create enum constant: name or ordinal field not found.");
                        } else {
                            (*env)->SetIntField(env, instance, ordinalField, ordinal);
                            (*env)->SetObjectField(env, instance, nameField, name);
                            jarray newValues = (*env)->NewObjectArray(env, ordinal + 1, cls, NULL);
                            for (int i = 0; i < ordinal; i++) {
                                (*env)->SetObjectArrayElement(env, newValues, i, (*env)->GetObjectArrayElement(env, values, i));
                            }
                            (*env)->SetObjectArrayElement(env, newValues, ordinal, instance);
                            (*env)->SetStaticObjectField(env, cls, valuesField, newValues);
                            return instance;
                        }
                    }
                }
            }
        }
    }
    (*env)->ReleaseStringUTFChars(env, clsSigArray, arr_sig);
    if (instance == NULL) {
        // Will not replace exception so it can be used here
        throwJ(env, "Failed to create enum constant: Unknown error");
        return NULL;
    } else {
        return instance;
    }
}
