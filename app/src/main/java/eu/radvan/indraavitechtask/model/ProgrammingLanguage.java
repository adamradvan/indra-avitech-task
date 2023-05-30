package eu.radvan.indraavitechtask.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "programming_language")
@NoArgsConstructor
@Getter
@Setter
public class ProgrammingLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pl_id")
    private Integer id;


    @Column(name = "pl_name")
    private String name;

    @Column(name = "pl_rating")
    private Integer rating;

    public ProgrammingLanguage(String name, Integer rating) {
        this.name = name;
        this.rating = rating;
    }
}