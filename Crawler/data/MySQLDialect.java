13
https://raw.githubusercontent.com/javamxd/ssssssss/master/src/main/java/org/ssssssss/dialect/MySQLDialect.java
package org.ssssssss.dialect;

import org.ssssssss.context.RequestContext;

public class MySQLDialect implements Dialect {

    @Override
    public String getPageSql(String sql, RequestContext context, long offset, long limit) {
        context.addParameter(limit);
        context.addParameter(offset);
        return sql + " limit ?,?";
    }
}
