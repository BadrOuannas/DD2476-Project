3
https://raw.githubusercontent.com/Silthus/sLimits/master/src/main/java/net/silthus/slib/config/typeconversions/SetTypeConversion.java
package net.silthus.slib.config.typeconversions;

import net.silthus.slib.config.ConfigUtil;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zml2008
 */
public class SetTypeConversion extends TypeConversion {

    @Override
    protected Object cast(Class<?> target, Type[] neededGenerics, Object value) {

        Set<Object> values = new HashSet<>();
        Collection raw = (Collection) value;
        for (Object obj : raw) {
            values.add(ConfigUtil.smartCast(neededGenerics[0], obj));
        }
        return values;
    }

    @Override
    public boolean isApplicable(Class<?> target, Object value) {

        return target.equals(Set.class) && value instanceof Collection;
    }

    @Override
    public int getParametersRequired() {

        return 1;
    }
}
