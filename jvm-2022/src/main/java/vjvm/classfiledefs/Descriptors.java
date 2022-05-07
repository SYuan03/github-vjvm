package vjvm.classfiledefs;

/**
 * Spec. Table 4.3-A. Interpretation of field descriptors
 */
public class Descriptors {
  public static final char DESC_byte = 'B';
  public static final char DESC_char = 'C';
  public static final char DESC_double = 'D';
  public static final char DESC_float = 'F';
  public static final char DESC_int = 'I';
  public static final char DESC_long = 'J';
  public static final char DESC_reference = 'L';
  public static final char DESC_short = 'S';
  public static final char DESC_boolean = 'Z';
  public static final char DESC_array = '[';
  public static final char DESC_void = 'V';

  /*
   * Get the descriptor of a type
   */
  public static String of(String name) {
    switch (name) {
      case "boolean":
        return Character.toString(DESC_boolean);
      case "byte":
        return Character.toString(DESC_byte);
      case "char":
        return Character.toString(DESC_char);
      case "double":
        return Character.toString(DESC_double);
      case "float":
        return Character.toString(DESC_float);
      case "int":
        return Character.toString(DESC_int);
      case "long":
        return Character.toString(DESC_long);
      case "short":
        return Character.toString(DESC_short);
      case "void":
        return Character.toString(DESC_void);
      default:
        return 'L' + name.replace('.', '/') + ';';
    }
  }
}
