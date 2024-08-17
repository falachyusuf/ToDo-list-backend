package id.backend.todo_list.repository;

import id.backend.todo_list.entity.CredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialsRepository extends JpaRepository<CredentialsEntity, Long> {
}
