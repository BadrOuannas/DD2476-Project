140
https://raw.githubusercontent.com/GraxCode/threadtear/master/src/me/nov/threadtear/analysis/stack/ConstantValue.java
package me.nov.threadtear.analysis.stack;

import java.util.Objects;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.analysis.*;

public class ConstantValue implements Value {
  private final BasicValue type;
  Object value; // null if unknown or NULL

  ConstantValue(BasicValue type, Object value) {
    this.type = Objects.requireNonNull(type);
    this.value = value;
  }

  @Override
  public int getSize() {
    return getType().getSize();
  }

  @Override
  public String toString() {
    Type t = getType().getType();
    if (t == null)
      return "uninitialized";
    String typeName = getType() == BasicValue.REFERENCE_VALUE ? "a reference type" : t.getClassName();
    return this == ConstantTracker.NULL ? "null" : value == null ? "unknown value of " + typeName : value + " (" + typeName + ")";
  }

  public Object getValue() {
    return value;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (this == ConstantTracker.NULL || obj == ConstantTracker.NULL || !(obj instanceof ConstantValue))
      return false;
    ConstantValue that = (ConstantValue) obj;
    return Objects.equals(this.value, that.value) && Objects.equals(this.getType(), that.getType());
  }

  @Override
  public int hashCode() {
    if (this == ConstantTracker.NULL)
      return ~0;
    return (value == null ? 7 : value.hashCode()) + getType().hashCode() * 31;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public boolean isInteger() {
    return getType() == BasicValue.INT_VALUE;
  }

  public boolean isString() {
    return value instanceof String;
  }

  public boolean isLong() {
    return getType() == BasicValue.LONG_VALUE;
  }

  public Integer getInteger() {
    if (value == null)
      return null;
    return (Integer) value;
  }

  public BasicValue getType() {
    return type;
  }

  public boolean isKnown() {
    return value != null;
  }

}