13
https://raw.githubusercontent.com/javamxd/ssssssss/master/src/main/java/org/ssssssss/dialect/DB2Dialect.java
package org.ssssssss.dialect;

import org.ssssssss.context.RequestContext;

public class DB2Dialect implements Dialect {
    @Override
    public String getPageSql(String sql, RequestContext context, long offset, long limit) {
        context.addParameter(offset + 1);
        context.addParameter(offset + limit);
        return "SELECT * FROM (SELECT TMP_PAGE.*,ROWNUMBER() OVER() AS ROW_ID FROM ( " + sql +
                " ) AS TMP_PAGE) TMP_PAGE WHERE ROW_ID BETWEEN ? AND ?";
    }
}
