package uk.co.electronstudio;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Util {

    public static final String OS_ARCH = System.getProperty("os.arch");
    public static final String OS_NAME = System.getProperty("os.name");
    public static final boolean IS_OS_LINUX = OS_NAME.startsWith("Linux") || OS_NAME.startsWith("LINUX");
    public static final boolean IS_OS_LINUX_AMD64 = IS_OS_LINUX && OS_ARCH.startsWith("amd64");
    public static final boolean IS_OS_MAC = OS_NAME.startsWith("Mac");
    public static final boolean IS_OS_WINDOWS = OS_NAME.startsWith("Windows");

    public static String extractDLLforOS(){
        if(IS_OS_LINUX_AMD64) {
            return extractFileFromResources("libraylib", ".so");
        }else if(IS_OS_MAC){
            return extractFileFromResources("libraylib", ".dylib");
        }else if(IS_OS_WINDOWS){
            return extractFileFromResources("raylib", ".dll");
        }else{
            return "libraylib.so";
        }
    }

    public static String extractFileFromResources(String name, String extension) {

        try {
            Path extractedLoc = Files.createTempFile(null, extension).toAbsolutePath();
            String path = "/" + name + extension;
            InputStream source = null;
            try {
                source = Util.class.getResourceAsStream(path);
            } catch (Exception e){
                e.printStackTrace(System.err);
            }
            if(source==null) source = ClassLoader.getSystemResourceAsStream(path);
            if(source==null){
                throw new RuntimeException("Couldn't extract "+name+extension+" from resources");
            }

            Files.copy(source, extractedLoc, StandardCopyOption.REPLACE_EXISTING);

            // TODO(brian): since this method is used for extracting the platform-specific shared lib
            //   as well as other resources (like the rabbit png when running Bunnymark), the shutdown
            //   hook can be registered more than once.
            //   this is not a problem in general but if we want to manually close the library arena
            //   then maybe we should only do so in the shutdown hook created for the library?
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                var currentThread = Thread.currentThread();
                var thread = STR."\{currentThread.getName()} \{currentThread.getThreadGroup().getName()}";
                try {
                    System.out.println(STR."Shutting down(\{thread}: closing library arena");
                    com.raylib.jextract.raylib_h_1.LIBRARY_ARENA.close();
                } catch (Exception _) {}
                try {
                    System.out.println(STR."Shutting down(\{thread}: deleting temp file \{extractedLoc}");
                    Files.delete(extractedLoc);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));

            return extractedLoc.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
