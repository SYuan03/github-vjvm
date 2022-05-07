package vjvm.runtime.classdata;

import lombok.var;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import vjvm.runtime.JClass;
import vjvm.runtime.classdata.constant.Constant;

import java.io.DataInput;

public class ConstantPool {
  // runtime constants are stored here
  private final Constant[] constants;
  @Getter
  @Setter
  private JClass jClass;

  /**
   * Constructs a runtime constant pool from binary data
   *
   * @param dataInput stream of data, contents of this constant pool will be read from stream
   * @param jClass    the class this pool belongs to
   */
  @SneakyThrows
  public ConstantPool(DataInput dataInput, JClass jClass) {
    this.jClass = jClass;
    var count = dataInput.readUnsignedShort();
    constants = new Constant[count];
    for (int i = 1; i < count; ) {
      var r = Constant.constructFromData(dataInput, jClass);
      constants[i] = r.getLeft();
      i += r.getRight();
    }
  }

  /**
   * Gets a constant at index
   *
   * @param index the index of the constant
   * @return the constant in the pool
   */
  public Constant constant(int index) {
    return constants[index];
  }

  public void constant(int index, Constant constant) {
    constants[index] = constant;
  }

  public int size() {
    return constants.length;
  }
}
