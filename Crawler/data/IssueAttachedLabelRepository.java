52
https://raw.githubusercontent.com/mhyeon-lee/spring-data-sample-codes/master/spring-data-r2dbc-sample/src/main/java/spring/data/r2dbc/issue/IssueAttachedLabelRepository.java
package spring.data.r2dbc.issue;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface IssueAttachedLabelRepository extends ReactiveCrudRepository<IssueAttachedLabel, Long> {
    Flux<IssueAttachedLabel> findByIssueId(UUID issueId, Sort sort);

    default Flux<IssueAttachedLabel> findByIssueId(UUID issueId) {
        return this.findByIssueId(issueId, Sort.by("attachedAt"));
    }
}
