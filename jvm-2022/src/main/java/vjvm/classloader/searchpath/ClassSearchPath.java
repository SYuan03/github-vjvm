package vjvm.classloader.searchpath;

import vjvm.utils.UnimplementedError;

import java.io.Closeable;
import java.io.InputStream;

/**
 * Represents a path to search class files in.
 * A search path may hold resources, such as jar files, so it must implement the Closeable interface.
 * If a subclass doesn't hold any resources, then just do nothing.
 */
public abstract class ClassSearchPath implements Closeable {
  /**
   * Construct search path objects with a given path.
   */
  public static ClassSearchPath[] constructSearchPath(String path) {
    // System.out.println(path);
    String sep = System.getProperty("path.separator");
    // throw new UnimplementedError("TODO: parse path and return an array of search paths");
    String[] path_array = path.split(sep);
    int n = path_array.length;
    ClassSearchPath[] myClassPath = new ClassSearchPath[n];
    for (int i = 0; i < n; i++) {
      if (path_array[i].endsWith(".jar") || path_array[i].endsWith(".JAR") ||
        path_array[i].endsWith(".zip") || path_array[i].endsWith(".ZIP")) {
        myClassPath[i] = new JarSearchPath(path_array[i]);
      }
      else {
        myClassPath[i] = new DirSearchPath(path_array[i]);
      }
    }
    return myClassPath;
  }

  /**
   * Find a class with specified name.
   *
   * @param name name of the class to find.
   * @return Returns a stream containing the binary data if such class is found, or null if not.
   */
  public abstract InputStream findClass(String name);
}
