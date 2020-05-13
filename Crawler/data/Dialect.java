13
https://raw.githubusercontent.com/javamxd/ssssssss/master/src/main/java/org/ssssssss/dialect/Dialect.java
package org.ssssssss.dialect;

import org.ssssssss.context.RequestContext;

public interface Dialect {

    /**
     * 获取查总数的sql
     */
    default String getCountSql(String sql) {
        return "select count(1) from (" + sql + ") count_";
    }

    /**
     * 获取分页sql
     */
    String getPageSql(String sql, RequestContext context, long offset, long limit);
}
