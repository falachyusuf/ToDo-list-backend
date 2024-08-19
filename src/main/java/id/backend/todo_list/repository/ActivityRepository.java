package id.backend.todo_list.repository;

import id.backend.todo_list.entity.ActivityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {
    @Query(value = "SELECT * FROM tb_tr_activity", nativeQuery = true)
    Page <ActivityEntity> getAllActivity(Pageable pageable);

    @Query(value = "SELECT * FROM tb_tr_activity WHERE id = ?1", nativeQuery = true)
    public Optional<ActivityEntity> getActivityById(long id);

}
