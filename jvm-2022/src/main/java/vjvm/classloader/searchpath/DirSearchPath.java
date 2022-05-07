package vjvm.classloader.searchpath;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class DirSearchPath extends ClassSearchPath {
  private Path path;
  public DirSearchPath(String dirName) {
    path = FileSystems.getDefault().getPath(dirName);
  }

  @Override
  public InputStream findClass(String name) {
    File target = path.resolve(name + ".class").toFile();
    try {
      return new FileInputStream(target);
    } catch (FileNotFoundException e){
      return null;
    }
  }

  @Override
  public void close() throws IOException {
  }
}
