package id.backend.todo_list.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_m_user")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
}
