package demos.springdata.advanced.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Set;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.EnumType.ORDINAL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "shampoos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shampoo {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    private String brand;
    private double price;
    @Enumerated(ORDINAL)
    private Size size;
    @ManyToOne(optional = true, cascade = {PERSIST, REFRESH})
    private Label label;
    @ManyToMany(cascade = {PERSIST, REFRESH}, fetch = EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name="shampoo_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="ingredient_id", referencedColumnName = "id")
    )
    private Set<Ingredient> ingredients;
}
