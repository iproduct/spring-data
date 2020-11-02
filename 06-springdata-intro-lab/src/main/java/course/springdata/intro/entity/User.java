package course.springdata.intro.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @NonNull
    private String username;
    @NonNull
    private int age;

    @OneToMany(mappedBy = "user")
    private Set<Account> accounts = new HashSet<>();

}
