package vjvm.classloader.searchpath;

import java.io.IOException;
import java.io.InputStream;

import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class JarSearchPath extends ClassSearchPath {
  private JarFile jarFile;
  public JarSearchPath(String jarFileName) {
    try {
      this.jarFile = new JarFile(jarFileName);
    } catch (IOException e) {
      this.jarFile = null;
    }

  }
  @Override
  public InputStream findClass(String name) {
    if (jarFile == null) {
      return null;
    }
    ZipEntry entry = jarFile.getEntry(name + ".class");
    try {
      if (entry == null) {
        return null;
      }
      else {
        InputStream input = jarFile.getInputStream(entry);
        return input;
      }

    } catch (IOException e) {
      return null;
    }
  }

  @Override
  public void close() throws IOException {
    if (jarFile != null) {
      jarFile.close();
    }
  }
}
