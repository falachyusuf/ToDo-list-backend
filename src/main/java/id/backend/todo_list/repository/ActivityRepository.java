package id.backend.todo_list.repository;

import id.backend.todo_list.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {
}
