package vjvm.runtime.classdata.attribute;

import lombok.var;
import lombok.SneakyThrows;
import vjvm.runtime.classdata.ConstantPool;

import java.io.DataInput;

public abstract class Attribute {

  @SneakyThrows
  public static Attribute constructFromData(DataInput input, ConstantPool constantPool) {
    var nameIndex = input.readUnsignedShort();
    var attrLength = Integer.toUnsignedLong(input.readInt());

    return new UnknownAttribute(input, attrLength);
  }
}
