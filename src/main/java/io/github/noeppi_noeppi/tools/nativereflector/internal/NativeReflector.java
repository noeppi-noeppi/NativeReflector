package io.github.noeppi_noeppi.tools.nativereflector.internal;

import io.github.noeppi_noeppi.tools.nativereflector.ModuleManager;
import io.github.noeppi_noeppi.tools.nativereflector.ObjectManipulator;

import java.lang.module.ModuleDescriptor;
import java.util.UUID;

public class NativeReflector {

    private static final UUID THE_UUID = UUID.randomUUID();
    private static final UUID COPY_UUID = THE_UUID;
    
    public static void main(String[] args) {
        System.out.println("Running NativeReflector Tests.");
        System.out.println("Loading native library.");
        NativeLoader.loadNative();
        System.out.println("Running tests.");
        
        Module base = String.class.getModule();
        Module current = NativeReflector.class.getModule();
        
        if (!current.isNamed()) {
            System.out.println("Failed: NativeReflector cannot be tested in the unnamed module..");
            return;
        }
        
        if (base.isOpen("java.lang", current)) {
            System.out.println("Can't run test for ModuleManager.forceOpen: " + base + "/java.lang is already opened to " + current + ".");
        } else {
            try {
                ModuleManager.forceOpen(base, "java.lang", current);
                if (!base.isOpen("java.lang", current)) {
                    System.out.println("ModuleManager.forceOpen is not working.");
                }
            } catch (Throwable t) {
                System.out.println("ModuleManager.forceOpen fails: " + t.getClass().getSimpleName() + t.getMessage());
            }
        }
        
        if (base.isExported("jdk.internal", current)) {
            System.out.println("Can't run test for ModuleManager.forceExport: " + base + "/jdk.internal is already exported to " + current + ".");
        } else {
            try {
                ModuleManager.forceExport(base, "jdk.internal", current);
                if (!base.isExported("jdk.internal", current)) {
                    System.out.println("ModuleManager.forceExport is not working.");
                }
            } catch (Throwable t) {
                System.out.println("ModuleManager.forceExport fails: " + t.getClass().getSimpleName() + t.getMessage());
            }
        }
        
        if (base.canRead(current)) {
            System.out.println("Can't run test for ModuleManager.forceRead: " + base + " can already read " + current + ".");
        } else {
            try {
                ModuleManager.forceRead(base, current);
                if (!base.canRead(current)) {
                    System.out.println("ModuleManager.forceRead is not working.");
                }
            } catch (Throwable t) {
                System.out.println("ModuleManager.forceRead fails: " + t.getClass().getSimpleName() + t.getMessage());
            }
        }
        
        boolean canRunRemoveTest = true;
        if (base.getDescriptor() == null) {
            System.out.println("Can't run test for ModuleManager.addModuleFlags: " + base + " is not named.");
        } else if (base.getDescriptor().isAutomatic()) {
            System.out.println("Can't run test for ModuleManager.addModuleFlags: " + base + " is already an automatic module.");
        } else {
            try {
                ModuleManager.addModuleFlags(base, ModuleDescriptor.Modifier.AUTOMATIC);
                if (!base.getDescriptor().isAutomatic()) {
                    System.out.println("ModuleManager.addModuleFlag is not working.");
                    canRunRemoveTest = false;
                }
            } catch (Throwable t) {
                System.out.println("ModuleManager.addModuleFlag fails: " + t.getClass().getSimpleName() + t.getMessage());
                canRunRemoveTest = false;
            }
        }
        
        if (!canRunRemoveTest) {
            System.out.println("Can't run test for ModuleManager.removeModuleFlags: Test for ModuleManager.addModuleFlags failed.");
        } else {
            try {
                ModuleManager.removeModuleFlags(base, ModuleDescriptor.Modifier.AUTOMATIC);
                if (base.getDescriptor().isAutomatic()) {
                    System.out.println("ModuleManager.removeModuleFlags is not working.");
                }
            } catch (Throwable t) {
                System.out.println("ModuleManager.removeModuleFlags fails: " + t.getClass().getSimpleName() + t.getMessage());
            }
        }
        
        try {
            UUID uid = ObjectManipulator.createEmptyObject(UUID.class);
            if (uid == null || uid.getLeastSignificantBits() != 0 || uid.getMostSignificantBits() != 0) {
                System.out.println("ObjectManipulator.createEmptyObject is not working: " + uid);
            }
        } catch (Throwable t) {
            System.out.println("ObjectManipulator.createEmptyObject fails: " + t.getClass().getSimpleName() + t.getMessage());
        }
        
        try {
            UUID uid;
            do {
                uid = UUID.randomUUID();
            } while (THE_UUID.equals(uid));
            ObjectManipulator.setFinalField(NativeReflector.class.getDeclaredField("COPY_UUID"), null, uid);
            if (THE_UUID.equals(COPY_UUID)) {
                System.out.println("ObjectManipulator.setFinalField is not working.");
            }
        } catch (Throwable t) {
            System.out.println("ObjectManipulator.setFinalField fails: " + t.getClass().getSimpleName() + t.getMessage());
        }
        
        try {
            TestEnum[] oldValues = TestEnum.values();
            TestEnum four = ObjectManipulator.createEnumConstant(TestEnum.class, "FOUR");
            if (four.ordinal() != 4) {
                System.out.println("ObjectManipulator.createEnumConstant is not working: Invalid ordinal " + four.ordinal() + ", expected 4.");
            } else {
                TestEnum[] newValues = TestEnum.values();
                if (oldValues.length + 1 != newValues.length || newValues.length != 5) {
                    System.out.println("ObjectManipulator.createEnumConstant is not working: Invalid array length " + oldValues.length + " / " + newValues.length + ", expected 4 / 5.");
                } else {
                    if (newValues[4] != four) {
                        System.out.println("ObjectManipulator.createEnumConstant is not working: Enum was not correctly injected.");
                    } else if (TestEnum.valueOf("FOUR") != four || Enum.valueOf(TestEnum.class, "FOUR") != four) {
                        System.out.println("ObjectManipulator.createEnumConstant is not working: Enum lookup does not work.");
                    }
                }
            }
        } catch (Throwable t) {
            System.out.println("ObjectManipulator.createEnumConstant fails: " + t.getClass().getSimpleName() + t.getMessage());
        }

        System.out.println("All tests completed.");
    }
    
    private enum TestEnum {
        ZERO, ONE, TWO, THREE
    }
}
