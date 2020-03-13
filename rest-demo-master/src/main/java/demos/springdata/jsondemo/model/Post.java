package demos.springdata.jsondemo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.annotations.Expose;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@JsonView(Views.Post.class)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;

    @NonNull
    @Length(min=3, max=80)
    @Expose
    private String title;

    @NonNull
    @Length(min=3, max=2048)
    @Expose
    private String content;

    @Expose
    @ManyToOne(optional = true)
//    @JoinColumn(name="author", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User author;

    @Expose(serialize = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private Long authorId;

    @Expose
    @Length(min=3, max=256)
    private String pictureUrl;

    @Expose
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created = new Date();

    @Expose
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated = new Date();
    
}
