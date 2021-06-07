package io.github.noeppi_noeppi.tools.nativereflector.internal;

import java.util.Locale;

public class JavaVM {

    private static Type vm = null;
    private static final Object lock = new Object();
    
    public static Type getVM() {
        if (vm == null) {
            synchronized (lock) {
                if (vm == null) {
                    if (System.getProperty("java.vm.name") != null && System.getProperty("java.vm.name").toLowerCase(Locale.ROOT).contains("openj9")) {
                        vm = Type.OPENJ9;
                    } else {
                        vm = Type.HOTSPOT;
                    }
                }
            }
        }
        return vm;
    }
    
    public enum Type {
        HOTSPOT,
        OPENJ9
    }
}
