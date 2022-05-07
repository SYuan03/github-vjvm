package vjvm.vm;

import lombok.Getter;
import lombok.var;
import vjvm.classloader.JClassLoader;
import vjvm.classloader.searchpath.ClassSearchPath;
import vjvm.classloader.searchpath.ModuleSearchPath;

public class VMContext {
  @Getter
  private final JClassLoader bootstrapLoader;
  @Getter
  private final JClassLoader userLoader;

  public VMContext(String userClassPath) {
    bootstrapLoader = new JClassLoader(
      null,
      getSystemSearchPaths(),
      this
    );

    userLoader = new JClassLoader(
      bootstrapLoader,
      ClassSearchPath.constructSearchPath(userClassPath),
      this
    );
  }
  // private static ClassSearchPath[] getSystemSearchPaths() {
  private static ClassSearchPath[] getSystemSearchPaths() {
    var bootClassPath = System.getProperty("sun.boot.class.path");

    if (bootClassPath != null) {
      return ClassSearchPath.constructSearchPath(bootClassPath);
    }

    // For compatibility with JDK9+
    return new ClassSearchPath[] { new ModuleSearchPath() };
  }
}
