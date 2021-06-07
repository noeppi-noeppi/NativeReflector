package io.github.noeppi_noeppi.tools.nativereflector;

import io.github.noeppi_noeppi.tools.nativereflector.internal.JavaVM;
import io.github.noeppi_noeppi.tools.nativereflector.internal.JniUtil;
import io.github.noeppi_noeppi.tools.nativereflector.internal.NativeLoader;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ObjectManipulator {
    
    /**
     * Creates a new object of a given class without calling a constructor. This will initialise
     * all fields to their default value. You can later set them via reflection.
     * 
     * This can not create primitives and their wrappers, arrays, {@code Class} objects, enums or
     * objects of abstract classes.
     */
    public static <T> T createEmptyObject(Class<T> cls) {
        if (JniUtil.isPrimitiveOrWrapper(cls)) {
            throw new IllegalArgumentException("Can't create empty primitive objects: " + cls);
        } else if (cls.isArray()) {
            throw new IllegalArgumentException("Can't create empty array objects: " + cls);
        } else if (cls == Class.class) {
            throw new IllegalArgumentException("Can't create new class objects: " + cls);
        } else if (cls == Enum.class || cls.isEnum()) {
            throw new IllegalArgumentException("Can't create new enum constants: " + cls);
        } else if (Modifier.isAbstract(cls.getModifiers()) || cls.isInterface()) {
            throw new IllegalArgumentException("Can't create empty instances of abstract classes or interfaces:" + cls);
        } else {
            NativeLoader.loadNative();
            //noinspection unchecked
            return (T) createEmptyObject0(cls);
        }
    }
    
    /**
     * Sets the value to a field. This also works when the field is final.
     * 
     * @param field The field that should get its value set.
     * @param instance The instance that should get its field value set. Can be null for static fields.
     * @param value The new value for the field.
     */
    public static void setFinalField(Field field, Object instance, Object value) {
        NativeLoader.loadNative();
        if (Modifier.isStatic(field.getModifiers())) {
            if (field.getType() == String.class || field.getType().isPrimitive()) {
                // Display a warning about setting constant values as this normally has no effect.
                System.out.println("Setting static final constant " + field.getName() + " " + JniUtil.getJniSig(field.getType()) + " in class " + field.getDeclaringClass().getName() + " via NativeReflector. Setting a constant value usually has no effect.");
            }
            if (field.getType() == boolean.class) {
                setStaticFieldZ0(field.getDeclaringClass(), field.getName(), "Z", (Boolean) value);
            } else if (field.getType() == byte.class) {
                setStaticFieldB0(field.getDeclaringClass(), field.getName(), "B", (Byte) value);
            } else if (field.getType() == char.class) {
                setStaticFieldC0(field.getDeclaringClass(), field.getName(), "C", (Character) value);
            } else if (field.getType() == short.class) {
                setStaticFieldS0(field.getDeclaringClass(), field.getName(), "S", (Short) value);
            } else if (field.getType() == int.class) {
                setStaticFieldI0(field.getDeclaringClass(), field.getName(), "I", (Integer) value);
            } else if (field.getType() == long.class) {
                setStaticFieldJ0(field.getDeclaringClass(), field.getName(), "J", (Long) value);
            } else if (field.getType() == float.class) {
                setStaticFieldF0(field.getDeclaringClass(), field.getName(), "F", (Float) value);
            } else if (field.getType() == double.class) {
                setStaticFieldD0(field.getDeclaringClass(), field.getName(), "D", (Double) value);
            } else {
                setStaticFieldL0(field.getDeclaringClass(), field.getName(), JniUtil.getJniSig(field.getType()), value);
            }
        } else {
            if (field.getType() == boolean.class) {
                setFieldZ0(instance, field.getDeclaringClass(), field.getName(), "Z", (Boolean) value);
            } else if (field.getType() == byte.class) {
                setFieldB0(instance, field.getDeclaringClass(), field.getName(), "B", (Byte) value);
            } else if (field.getType() == char.class) {
                setFieldC0(instance, field.getDeclaringClass(), field.getName(), "C", (Character) value);
            } else if (field.getType() == short.class) {
                setFieldS0(instance, field.getDeclaringClass(), field.getName(), "S", (Short) value);
            } else if (field.getType() == int.class) {
                setFieldI0(instance, field.getDeclaringClass(), field.getName(), "I", (Integer) value);
            } else if (field.getType() == long.class) {
                setFieldJ0(instance, field.getDeclaringClass(), field.getName(), "J", (Long) value);
            } else if (field.getType() == float.class) {
                setFieldF0(instance, field.getDeclaringClass(), field.getName(), "F", (Float) value);
            } else if (field.getType() == double.class) {
                setFieldD0(instance, field.getDeclaringClass(), field.getName(), "D", (Double) value);
            } else {
                setFieldL0(instance, field.getDeclaringClass(), field.getName(), JniUtil.getJniSig(field.getType()), value);
            }
        }
    }

    /**
     * Creates a new enum constant for a given enum using a given name. The enum constant will be created
     * without a constructor call. You'll need to initialise it via reflection. Duplicate enum names are
     * not supported, so keep your enum name unique.
     * If the enum value array is cached somewhere, you must hand updating this yourself. (Exception to this
     * is the cache in the {@code Class} object for the enum. This is handles by the implementation itself.
     * <b>switches on that enum that have no default branch will throw an {@code IncompatibleClassChangeError}
     * if used with the newly created enum constant. Also if a class containing an enum switch is loaded
     * before the new enum constant was created, the switch will fail with an {@code ArrayIndexOutOfBoundsException}.
     * </b>
     * 
     * @param cls The enum class that should have a constant added.
     * @param name The name of the new enum constant.
     * 
     * @return the new enum constant.
     */
    public static <T extends Enum<T>> T createEnumConstant(Class<T> cls, String name) {
        if (!cls.isEnum()) {
            throw new IllegalArgumentException("Enum constants can only be created for enum classes.");
        } else {
            NativeLoader.loadNative();
            //noinspection SynchronizationOnLocalVariableOrMethodParameter
            synchronized (cls) {
                for (T t : cls.getEnumConstants()) {
                    if (t.name().equals(name)) {
                        throw new IllegalStateException("Can't create enum constant: Duplicate enum name: " + name);
                    }
                }
                //noinspection unchecked
                return (T) createEnumConstant0(cls, JniUtil.getJniSig(cls), JniUtil.getJniSig(cls.arrayType()), name, JavaVM.getVM().ordinal());
            }
        }
    }

    private static native Object createEmptyObject0(Class<?> cls);
    private static native void setFieldL0(Object instance, Class<?> definingClass, String fname, String fsig, Object value);
    private static native void setFieldZ0(Object instance, Class<?> definingClass, String fname, String fsig, boolean value);
    private static native void setFieldB0(Object instance, Class<?> definingClass, String fname, String fsig, byte value);
    private static native void setFieldC0(Object instance, Class<?> definingClass, String fname, String fsig, char value);
    private static native void setFieldS0(Object instance, Class<?> definingClass, String fname, String fsig, short value);
    private static native void setFieldI0(Object instance, Class<?> definingClass, String fname, String fsig, int value);
    private static native void setFieldJ0(Object instance, Class<?> definingClass, String fname, String fsig, long value);
    private static native void setFieldF0(Object instance, Class<?> definingClass, String fname, String fsig, float value);
    private static native void setFieldD0(Object instance, Class<?> definingClass, String fname, String fsig, double value);
    private static native void setStaticFieldL0(Class<?> definingClass, String fname, String fsig, Object value);
    private static native void setStaticFieldZ0(Class<?> definingClass, String fname, String fsig, boolean value);
    private static native void setStaticFieldB0(Class<?> definingClass, String fname, String fsig, byte value);
    private static native void setStaticFieldC0(Class<?> definingClass, String fname, String fsig, char value);
    private static native void setStaticFieldS0(Class<?> definingClass, String fname, String fsig, short value);
    private static native void setStaticFieldI0(Class<?> definingClass, String fname, String fsig, int value);
    private static native void setStaticFieldJ0(Class<?> definingClass, String fname, String fsig, long value);
    private static native void setStaticFieldF0(Class<?> definingClass, String fname, String fsig, float value);
    private static native void setStaticFieldD0(Class<?> definingClass, String fname, String fsig, double value);
    private static native Object createEnumConstant0(Class<?> cls, String clsSig, String claSigArray, String name, int javaVM);
}
