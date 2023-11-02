package hexlet.code.repositories;

import hexlet.code.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface TaskRepository extends JpaRepository<Task, Long>, QuerydslPredicateExecutor<Task> {
}
