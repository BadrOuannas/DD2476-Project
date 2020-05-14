52
https://raw.githubusercontent.com/mhyeon-lee/spring-data-sample-codes/master/spring-data-jdbc-plus-sql-groovy-sample/src/main/java/com/navercorp/spring/sql/groovy/issue/IssueRepository.java
package com.navercorp.spring.sql.groovy.issue;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface IssueRepository extends PagingAndSortingRepository<Issue, UUID>, IssueRepositoryCustom {
    List<Issue> findByTitleLikeAndStatus(String titleStartAt, Status status, Pageable pageable);

    @Query("SELECT COUNT(*) FROM ISSUE WHERE title LIKE :titleStartAt AND status = :status")
    long countByTitleLikeAndStatus(@Param("titleStartAt") String titleStartAt, @Param("status") Status status);
}
