package demos.springdata.restdemo.model;

import com.google.gson.annotations.Expose;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Expose
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Expose
    @NonNull
    @Length(min = 2, max = 60)
    private String firstName;

    @Expose
    @NonNull
    @Length(min = 2, max = 60)
    private String lastName;

    @Expose
    @NonNull
    @Length(min = 3, max = 60)
    @Column(unique = true, nullable = false)
    @NotNull
    private String username;

    @Expose(serialize = false)
    @NonNull
    @Length(min = 4, max = 30)
    @Column(unique = true, nullable = false)
    @NotNull
    private String password;

    @Expose
    @NotNull
    @NonNull
    private String role = "ROLE_USER";

    private boolean active = true;

    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    private Collection<Post> posts = new ArrayList<Post>();

    @Expose
    private Date created = new Date();
    @Expose
    private Date modified = new Date();
}
