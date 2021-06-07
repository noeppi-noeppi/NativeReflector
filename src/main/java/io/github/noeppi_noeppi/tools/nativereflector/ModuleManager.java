package io.github.noeppi_noeppi.tools.nativereflector;

import io.github.noeppi_noeppi.tools.nativereflector.internal.NativeLoader;

import java.lang.module.ModuleDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Some functions to alter modules at runtime even if you could not do normally. You can for example
 * mark {@code java.base} an automatic module or open a package from any module to your module.
 */
public class ModuleManager {
    
    private static final Object lock = new Object();

    /**
     * Adds the given module flags to a module. Does only work for named modules.
     * 
     * @throws IllegalArgumentException If the module is not named.
     */
    public static void addModuleFlags(Module module, ModuleDescriptor.Modifier... flags) {
        NativeLoader.loadNative();
        synchronized (lock) {
            ModuleDescriptor descriptor = module.getDescriptor();
            if (descriptor == null) {
                throw new IllegalArgumentException("Can't alter flags of unnamed modules.");
            }
            Set<ModuleDescriptor.Modifier> modifiers = new HashSet<>(descriptor.modifiers());
            modifiers.addAll(Arrays.asList(flags));
            setModuleFlags0(descriptor, Set.copyOf(modifiers), modifiers.contains(ModuleDescriptor.Modifier.OPEN), modifiers.contains(ModuleDescriptor.Modifier.AUTOMATIC));
        }
    }
    
    /**
     * Removes the given module flags from a module. Does only work for named modules.
     * 
     * @throws IllegalArgumentException If the module is not named.
     */
    public static void removeModuleFlags(Module module, ModuleDescriptor.Modifier... flags) {
        NativeLoader.loadNative();
        synchronized (lock) {
            ModuleDescriptor descriptor = module.getDescriptor();
            if (descriptor == null) {
                throw new IllegalArgumentException("Can't alter flags of unnamed modules.");
            }
            Set<ModuleDescriptor.Modifier> modifiers = new HashSet<>(descriptor.modifiers());
            Arrays.asList(flags).forEach(modifiers::remove);
            setModuleFlags0(descriptor, Set.copyOf(modifiers), modifiers.contains(ModuleDescriptor.Modifier.OPEN), modifiers.contains(ModuleDescriptor.Modifier.AUTOMATIC));
        }
    }

    /**
     * Opens a package from one module to another module without checks on whether this should
     * be allowed.
     * 
     * @param from The module that contains the package to be opened.
     * @param pkg The package that should be opened.
     * @param to The target module to which the package should be opened.
     */
    public static void forceOpen(Module from, String pkg, Module to) {
        NativeLoader.loadNative();
        synchronized (lock) {
            forceOpen0(from, pkg, to);
        }
    }
    
    /**
     * Exports a package from one module to another module without checks on whether this should
     * be allowed.
     * 
     * @param from The module that contains the package to be exported.
     * @param pkg The package that should be exported.
     * @param to The target module to which the package should be exported.
     */
    public static void forceExport(Module from, String pkg, Module to) {
        NativeLoader.loadNative();
        synchronized (lock) {
            forceExport0(from, pkg, to);
        }
    }

    /**
     * Makes it possible for a module to read another module without checks on whether this should
     * be allowed.
     * 
     * @param from The module that is reading the other module.
     * @param to The module that is read by the other module.
     */
    public static void forceRead(Module from, Module to) {
        if (from.isNamed()) {
            NativeLoader.loadNative();
            synchronized (lock) {
                forceRead0(from, to);
            }
        }
    }
    
    private static native void setModuleFlags0(ModuleDescriptor descriptor, Set<ModuleDescriptor.Modifier> modSet, boolean open, boolean automatic);
    private static native void forceOpen0(Module from, String pkg, Module to);
    private static native void forceExport0(Module from, String pkg, Module to);
    private static native void forceRead0(Module from, Module to);
}
