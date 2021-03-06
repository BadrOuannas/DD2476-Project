3
https://raw.githubusercontent.com/Silthus/sLimits/master/src/main/java/net/silthus/slimits/config/LimitModeMapConverter.java
package net.silthus.slimits.config;

import de.exlll.configlib.Converter;
import net.silthus.slib.util.EnumUtils;
import net.silthus.slimits.LimitMode;
import org.bukkit.Material;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class LimitModeMapConverter implements Converter<Map<String, LimitMode>, Map<String, String>> {

    @Override
    public Map<String, String> convertTo(Map<String, LimitMode> entries, ConversionInfo conversionInfo) {
        return entries.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, o -> o.getValue().toString()));
    }

    @Override
    public Map<String, LimitMode> convertFrom(Map<String, String> entries, ConversionInfo conversionInfo) {
        return entries.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, o -> EnumUtils.getEnumFromString(LimitMode.class, o.getValue())));
    }
}
