13
https://raw.githubusercontent.com/javamxd/ssssssss/master/src/main/java/org/ssssssss/validator/IValidator.java
package org.ssssssss.validator;

import org.w3c.dom.Node;

public interface IValidator {

    /**
     * 支持的节点名称
     *
     * @return
     */
    public String support();

    /**
     * 验证方法
     *
     * @param input 输入值
     * @param node  节点
     * @return
     */
    public boolean validate(Object input, Node node);
}
