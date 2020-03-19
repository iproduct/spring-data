package demos.springdata.restdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Expose
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
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
    @EqualsAndHashCode.Include
    private String username;

    @Expose(serialize = false)
    @NonNull
    @Length(min = 4, max = 80)
    @Column(unique = true, nullable = false)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Expose
    @NonNull
    @NotNull
    private String roles;

    private boolean active = true;

    @Expose
    @Length(min=8, max=512)
    @URL
    private String imageUrl;


    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    @JsonIgnore
    private Collection<Post> posts = new ArrayList<Post>();

    @Expose
    private Date created = new Date();
    @Expose
    private Date modified = new Date();
}
