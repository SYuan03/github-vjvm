package vjvm.runtime.classdata.constant;

import lombok.var;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.DataInput;

public class IntegerConstant extends Constant {
  @Getter
  private final int value;

  @SneakyThrows
  IntegerConstant(DataInput input) {
    value = input.readInt();
  }

  @Override
  public String toString() {
    return String.format("Integer: %d", value);
  }
}
