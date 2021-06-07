#include "NativeReflector.h"

#include "io_github_noeppi_noeppi_tools_nativereflector_ModuleManager.h"

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ModuleManager_setModuleFlags0(JNIEnv *env, jclass class, jobject descriptor, jobject modifiers, jboolean open, jboolean automatic) {
    jclass classModuleDescriptor = (*env)->FindClass(env, "java/lang/module/ModuleDescriptor");
    if (classModuleDescriptor == NULL) {
        throwJ(env, "java/lang/module/ModuleDescriptor class not found.");
        return;
    }
    jfieldID modifierField = (*env)->GetFieldID(env, classModuleDescriptor, "modifiers", "Ljava/util/Set;");
    if (modifierField == NULL) {
        throwJ(env, "modifiers Ljava/util/Set; field of java/lang/module/ModuleDescriptor class not found.");
        return;
    }
    jfieldID openField = (*env)->GetFieldID(env, classModuleDescriptor, "open", "Z");
    if (openField == NULL) {
        throwJ(env, "open Z field of java/lang/module/ModuleDescriptor class not found.");
        return;
    }
    jfieldID automaticField = (*env)->GetFieldID(env, classModuleDescriptor, "automatic", "Z");
    if (automaticField == NULL) {
        throwJ(env, "automatic Z field of java/lang/module/ModuleDescriptor class not found.");
        return;
    }
    (*env)->SetObjectField(env, descriptor, modifierField, modifiers);
    (*env)->SetBooleanField(env, descriptor, openField, open);
    (*env)->SetBooleanField(env, descriptor, automaticField, automatic);
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ModuleManager_forceOpen0(JNIEnv *env, jclass class, jobject from, jstring pkg, jobject to) {
    jclass classModule = (*env)->FindClass(env, "java/lang/Module");
    if (classModule == NULL) {
        throwJ(env, "java/lang/Module class not found.");
        return;
    }
    jmethodID jmid = (*env)->GetMethodID(env, classModule, "implAddExportsOrOpens", "(Ljava/lang/String;Ljava/lang/Module;ZZ)V");
    if (jmid == NULL) {
        throwJ(env, "implAddExportsOrOpens(Ljava/lang/String;Ljava/lang/Module;ZZ)V method of java/lang/Module class not found.");
    }
    (*env)->CallVoidMethod(env, from, jmid, pkg, to, JNI_TRUE, JNI_TRUE);
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ModuleManager_forceExport0(JNIEnv *env, jclass class, jobject from, jstring pkg, jobject to) {
    jclass classModule = (*env)->FindClass(env, "java/lang/Module");
    if (classModule == NULL) {
        throwJ(env, "java/lang/Module class not found.");
        return;
    }
    jmethodID jmid = (*env)->GetMethodID(env, classModule, "implAddExportsOrOpens", "(Ljava/lang/String;Ljava/lang/Module;ZZ)V");
    if (jmid == NULL) {
        throwJ(env, "implAddExportsOrOpens (Ljava/lang/String;Ljava/lang/Module;ZZ)V method of java/lang/Module class not found.");
    }
    (*env)->CallVoidMethod(env, from, jmid, pkg, to, JNI_FALSE, JNI_TRUE);
}

JNIEXPORT void JNICALL Java_io_github_noeppi_1noeppi_tools_nativereflector_ModuleManager_forceRead0(JNIEnv *env, jclass class, jobject from, jobject to) {
    jclass classModule = (*env)->FindClass(env, "java/lang/Module");
    if (classModule == NULL) {
        throwJ(env, "java/lang/Module class not found.");
        return;
    }
    jmethodID jmid = (*env)->GetMethodID(env, classModule, "implAddReads", "(Ljava/lang/Module;Z)V");
    if (jmid == NULL) {
        throwJ(env, "implAddReads (Ljava/lang/Module;Z)V method of java/lang/Module class not found.");
    }
    (*env)->CallVoidMethod(env, from, jmid, to, JNI_TRUE);
}