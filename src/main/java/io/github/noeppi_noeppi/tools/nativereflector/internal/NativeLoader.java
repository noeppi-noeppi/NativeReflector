package io.github.noeppi_noeppi.tools.nativereflector.internal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;

public class NativeLoader {

    private static boolean nativesLoaded;
    private static final Object lock = new Object();

    public static void loadNative() {
        if (!nativesLoaded) {
            synchronized (lock) {
                if (!nativesLoaded) {
                    Path path = null;
                    try {
                        String os = "lin";
                        String suffix = "so";
                        if (System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("win")) {
                            os = "win";
                            suffix = "dll";
                        } else if (System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("osx")
                                || System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("os x")
                                || System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("mac")) {
                            os = "mac";
                            suffix = "dylib";
                        }
                        String arch = "64";
                        if (!System.getProperty("os.arch").toLowerCase(Locale.ROOT).contains("64")) {
                            if (System.getProperty("os.arch").toLowerCase(Locale.ROOT).contains("32")
                                    || System.getProperty("os.arch").toLowerCase(Locale.ROOT).contains("86")) {
                                arch = "32";
                            }
                        }
                        String fname = os + arch + "nativereflector." + suffix;
                        InputStream in = NativeLoader.class.getResourceAsStream("/" + fname);
                        if (in == null && Boolean.parseBoolean(System.getProperty("nativereflector.load_natives_local"))) {
                            System.out.println("Loading NativeReflector library from development location.");
                            Path inPath = Paths.get("").resolve("build").resolve("resources").resolve("main").resolve(fname);
                            if (Files.isRegularFile(inPath)) {
                                in = Files.newInputStream(inPath);
                            }
                        }
                        if (in == null) {
                            throw new FileNotFoundException("Native library '" + fname + "' not found.");
                        }
                        path = Files.createTempFile("nativereflector", "." + suffix);
                        Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
                        in.close();
                        System.load(path.toAbsolutePath().normalize().toString());
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to load NativeReflector library: " + e.getMessage(), e);
                    } finally {
                        try {
                            if (path != null) {
                                Files.deleteIfExists(path);
                            }
                        } catch (Exception e) {
                            //
                        }
                    }
                    nativesLoaded = true;
                }
            }
        }
    }
}
