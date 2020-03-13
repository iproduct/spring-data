package demos.springdata.jsondemo.model;

import com.fasterxml.jackson.annotation.*;
import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.EAGER;

@Entity
@JsonIgnoreProperties({"authorities", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
@Data
public class User implements UserDetails {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Post.class)
    private Long id;

    @Expose
    @NotNull
    @Length(min = 3, max = 30)
    @NonNull
    private String username;

    @NotNull
    @Length(min = 4, max = 80)
    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Expose
    @JsonView(Views.Post.class)
    @NotNull
    @Length(min = 1, max = 30)
    @NonNull
    private String fname;

    @Expose
    @JsonView(Views.Post.class)
    @NotNull
    @Length(min = 1, max = 30)
    @NonNull
    private String lname;

    @Expose
    @NonNull
    private String roles = "ROLE_USER";

    @Expose
    private boolean active = true;

    @Expose
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created = new Date();

    @Expose
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated = new Date();

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Collection<Post> posts = new ArrayList<>();

    public User(long id,
                @NotNull @Length(min = 3, max = 30) String username,
                @NotNull @Length(min = 4, max = 80) String password,
                @NotNull @Length(min = 1, max = 30) String fname,
                @NotNull @Length(min = 1, max = 30) String lname,
                String roles,
                boolean active,
                Date created,
                Date updated,
                Collection<Post> articles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.roles = roles;
        this.active = active;
        this.created = created;
        this.updated = updated;
        this.posts = articles;
    }

    public User() {
    }

    @java.beans.ConstructorProperties({"username", "password", "fname", "lname", "roles"})
    public User(@NotNull @Length(min = 3, max = 30) String username,
                @NotNull @Length(min = 4, max = 80) String password,
                @NotNull @Length(min = 1, max = 30) String fname,
                @NotNull @Length(min = 1, max = 30) String lname,
                String roles) {
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.roles = roles;
    }

//    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

//    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(roles.split("\\s*,\\s*")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
