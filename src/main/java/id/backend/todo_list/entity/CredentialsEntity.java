package id.backend.todo_list.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_m_credential")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CredentialsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String password;
    @Column(name = "user_id")
    private Long userId;
}
