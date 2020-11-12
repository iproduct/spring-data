package demos.springdata.restdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
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

    @JsonView(Views.Post.class)
    @Expose
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @JsonView(Views.Post.class)
    @Expose
    @NonNull
    @Length(min = 2, max = 60)
    private String firstName;

    @JsonView(Views.Post.class)
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
    @Column(nullable = false)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Expose
    @NonNull
    @NotNull
    private String roles;

    @JsonView(Views.Post.class)
    @Expose
    @NonNull
    @Length(min=8, max=512)
    @URL
    private String imageUrl;

    private boolean active = true;

    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    @JsonIgnore
    @Expose(serialize = false, deserialize = false)
    private Collection<Post> posts = new ArrayList<Post>();

    @Expose
    private Date created = new Date();
    @Expose
    private Date modified = new Date();
}
