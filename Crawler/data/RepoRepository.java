52
https://raw.githubusercontent.com/mhyeon-lee/spring-data-sample-codes/master/spring-data-jdbc-plus-sql-groovy-sample/src/main/java/com/navercorp/spring/sql/groovy/repo/RepoRepository.java
package com.navercorp.spring.sql.groovy.repo;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RepoRepository extends CrudRepository<Repo, String> {
    @Query("SELECT * FROM REPO repo WHERE repo.id = :id FOR UPDATE")
    @Override
    Optional<Repo> findById(@Param("id") String id);
}
