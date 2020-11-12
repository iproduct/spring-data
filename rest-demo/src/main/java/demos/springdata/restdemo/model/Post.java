package demos.springdata.restdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.annotations.Expose;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonView(Views.Post.class)
public class Post {
    @Expose
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Expose
    @NonNull
    @Length(min = 3, max = 80)
    private String title;

    @Expose
    @NonNull
    @Length(min = 3, max = 2048)
    private String content;

    @Expose
    @NotNull
    @NonNull
    @Length(min=8, max=512)
    @URL
    private String imageUrl;

    @Expose
    @ManyToOne(optional = true)
    private User author;

    @Expose(serialize = false)
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long authorId;

    @Expose
    private Date created = new Date();
    @Expose
    private Date modified = new Date();
}
