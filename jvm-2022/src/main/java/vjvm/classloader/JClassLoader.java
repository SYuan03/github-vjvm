package vjvm.classloader;

import lombok.var;
import lombok.Getter;
import lombok.SneakyThrows;
import vjvm.classloader.searchpath.ClassSearchPath;
import vjvm.runtime.JClass;
import vjvm.vm.VMContext;
import vjvm.utils.UnimplementedError;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.HashMap;

public class JClassLoader implements Closeable {
  private final JClassLoader parent;
  private final ClassSearchPath[] searchPaths;
  private final HashMap<String, JClass> definedClass = new HashMap<>();
  @Getter
  private final VMContext context;

  public JClassLoader(JClassLoader parent, ClassSearchPath[] searchPaths, VMContext context) {
    this.context = context;
    this.parent = parent;
    this.searchPaths = searchPaths;
  }

  /**
   * Load class
   *
   * If a class is found, construct it using the data
   * returned by ClassSearchPath.findClass and return it.
   *
   * Otherwise, return null.
   */
  public JClass loadClass(String descriptor) {
    // descriptor = descriptor.substring(0, descriptor.length() - 1);
    // System.out.println(descriptor);
    // throw new UnimplementedError("TODO: load class");

    // String[] descriptor_array = descriptor.split("/");
    // int n = descriptor_array.length;
    String name = descriptor.substring(1, descriptor.length()-1);
    if (definedClass.containsKey(descriptor)) {
      return definedClass.get(descriptor);
    } else if (parent != null) {
      JClass res = parent.loadClass(descriptor);
      if (res == null) {
        for (ClassSearchPath searchPath : searchPaths) {
          InputStream s = searchPath.findClass(name);
          if (s != null) {
            JClass newClass = new JClass(new DataInputStream(s), this);
            definedClass.put(descriptor, newClass);
            return newClass;
          }
        }
        return null;
      } else {
        definedClass.put(descriptor, res);
        return res;
      }
    } else {
      for (ClassSearchPath searchPath : searchPaths) {
        InputStream s = searchPath.findClass(name);
        if (s != null) {
          // return new JClass(new DataInputStream(s), this);
          JClass newClass = new JClass(new DataInputStream(s), this);
          definedClass.put(descriptor, newClass);
          return newClass;
        }
      }
      return null;
    }
    // To construct a JClass, use the following constructor
    // return new JClass(new DataInputStream(istream_from_file), this);
  }

  @Override
  @SneakyThrows
  public void close() {
    for (var s : searchPaths)
      s.close();
  }
}
