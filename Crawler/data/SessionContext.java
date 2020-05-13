13
https://raw.githubusercontent.com/javamxd/ssssssss/master/src/main/java/org/ssssssss/context/SessionContext.java
package org.ssssssss.context;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class SessionContext extends HashMap<String,Object> {

    private HttpSession session;

    public SessionContext(HttpSession session){
        this.session = session;
    }

    @Override
    public Object get(Object key) {
        return session.getAttribute(key.toString());
    }
}
