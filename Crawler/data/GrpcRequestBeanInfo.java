3
https://raw.githubusercontent.com/zalopay-oss/jmeter-grpc-request/master/src/main/java/vn/zalopay/benchmark/testbean/GrpcRequestBeanInfo.java
package vn.zalopay.benchmark.testbean;

import org.apache.jmeter.testbeans.BeanInfoSupport;
import org.apache.jmeter.testbeans.gui.TypeEditor;

import java.beans.PropertyDescriptor;

public class GrpcRequestBeanInfo extends BeanInfoSupport {

    private void setProp(PropertyDescriptor property, String name, Object obj) {
        property = property(name);
        property.setValue(NOT_UNDEFINED, Boolean.TRUE);
        property.setValue(DEFAULT, obj);
    }

    public GrpcRequestBeanInfo() {
        super(GrpcRequest.class);

        createPropertyGroup("serverInfoGroup", new String[]{"HOST_PORT", "FULL_METHOD"});
        createPropertyGroup("requestInfoGroup", new String[]{"PROTO_FOLDER", "REQUEST_FILE", "DEADLINE", "REQUEST_JSON"});

        PropertyDescriptor props = property("REQUEST_JSON", TypeEditor.TextAreaEditor);
        props.setValue(NOT_UNDEFINED, Boolean.FALSE);
        props.setValue(DEFAULT, "");
        props.setValue(NOT_EXPRESSION, Boolean.TRUE);
        props.setValue(TEXT_LANGUAGE, "javascript");

        setProp(props, "HOST_PORT", "");
        setProp(props, "FULL_METHOD", "");
        setProp(props, "PROTO_FOLDER", "");
        setProp(props, "REQUEST_FILE", "");

        setProp(props, "DEADLINE", 0L);
    }

}