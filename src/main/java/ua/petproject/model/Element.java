package ua.petproject.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import ua.petproject.categories.Category;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "elements")
public class Element {
    @Id
    private long id;
    private String name;
    private Category category;
}
