52
https://raw.githubusercontent.com/mhyeon-lee/spring-data-sample-codes/master/spring-data-jdbc-plus-sql-groovy-sample/src/main/java/com/navercorp/spring/sql/groovy/query/criteria/IssueViewCriteria.java
package com.navercorp.spring.sql.groovy.query.criteria;

import com.navercorp.spring.sql.groovy.account.Account;
import com.navercorp.spring.sql.groovy.issue.Status;
import com.navercorp.spring.sql.groovy.label.Label;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class IssueViewCriteria {
    Status status;

    AggregateReference<Account, UUID> createdBy;

    List<AggregateReference<Label, UUID>> labelIds;

    String searchRepoName;

    String searchContent;
}
