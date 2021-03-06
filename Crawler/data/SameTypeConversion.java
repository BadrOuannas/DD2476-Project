3
https://raw.githubusercontent.com/Silthus/sLimits/master/src/main/java/net/silthus/slib/config/typeconversions/SameTypeConversion.java
package net.silthus.slib.config.typeconversions;

import java.lang.reflect.Type;

/**
 * @author zml2008
 */
public class SameTypeConversion extends TypeConversion {

    @Override
    protected Object cast(Class<?> target, Type[] neededGenerics, Object value) {

        return value;
    }

    @Override
    public boolean isApplicable(Class<?> target, Object value) {

        return target.isInstance(value);
    }

    @Override
    public int getParametersRequired() {

        return 0;
    }
}
