package course.spring.dataintro.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NonNull
    private String username;
    @NonNull
    private int age;
    @OneToMany(mappedBy = "user")
    private List<Account> accounts = new ArrayList<>();
}
