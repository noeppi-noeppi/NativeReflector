# NativeReflector

![Services](https://img.shields.io/static/v1?label=Java&message=16&color=yellow&style=flat)

NativeReflector is a library / java9 module that allows for more reflection through the JNI. It works on the Hotspot and OpenJ9 VM.

It for example allows you to

  * Create objects without constructor call.
  * Modify final fields.
  * Create new enum instances. (However you must be very careful with this, it can break much.)
  * Mark modules open / automatic / synthetic or remove these flags from modules
  * Add opens and exports for any module, not just for your own. (Also works with JD modules like `java.base`)
  * Add reads for any module, not just your own module.

To check whether your java installation supports all features of NativeReflector, you can just run the module: `java -p NativeReflector.jar -m noeppi_noeppi.nativereflector`