package io.github.noeppi_noeppi.tools.nativereflector.internal;

public class JniUtil {

    public static String getJniSig(Class<?> cls) {
        if (cls == void.class) {
            return "V";
        } else if (cls == boolean.class) {
            return "Z";
        } else if (cls == byte.class) {
            return "B";
        } else if (cls == char.class) {
            return "C";
        } else if (cls == short.class) {
            return "S";
        } else if (cls == int.class) {
            return "I";
        } else if (cls == long.class) {
            return "L";
        } else if (cls == float.class) {
            return "F";
        } else if (cls == double.class) {
            return "D";
        } else if (cls.isArray()) {
            return "[" + getJniSig(cls.getComponentType());
        } else {
            return "L" + cls.getName().replace('.', '/') + ";";
        }
    }
    
    public static boolean isPrimitiveOrWrapper(Class<?> cls) {
        return cls == void.class || cls == boolean.class || cls == byte.class || cls == char.class
                || cls == short.class || cls == int.class || cls == long.class || cls == float.class
                || cls == double.class || cls == Void.class || cls == Boolean.class || cls == Byte.class
                || cls == Character.class || cls == Short.class || cls == Integer.class || cls == Long.class
                || cls == Float.class || cls == Double.class || cls.isPrimitive();
    }
}
