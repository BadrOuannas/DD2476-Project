13
https://raw.githubusercontent.com/javamxd/ssssssss/master/src/main/java/org/ssssssss/validator/MaxLenValidator.java
package org.ssssssss.validator;

import org.apache.commons.lang3.math.NumberUtils;
import org.ssssssss.utils.DomUtils;
import org.w3c.dom.Node;

public class MaxLenValidator implements IValidator {
    @Override
    public String support() {
        return "max-len";
    }

    @Override
    public boolean validate(Object input, Node node) {
        if (input instanceof String) {
            int len = NumberUtils.toInt(DomUtils.getNodeAttributeValue(node, "value"), 0);
            return len <= 0 || input.toString().length() <= len;
        }
        return false;
    }
}
