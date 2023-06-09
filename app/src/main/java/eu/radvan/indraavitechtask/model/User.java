package eu.radvan.indraavitechtask.model;

import eu.radvan.indraavitechtask.model.id.ULID;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@FieldNameConstants
@Entity
@Table(name = "SUSERS")
public class User {

    @NotNull
    @Setter(AccessLevel.PRIVATE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true, updatable = false, nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "USER_GUID", unique = true, updatable = false, nullable = false)
    private String guid = ULID.generate();

    @NotBlank
    @Column(name = "USER_NAME")
    private String name;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return guid.equals(user.guid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guid);
    }

    @Override
    public String toString() {
        return "'" + getClass().getSimpleName() + json() + "'";
    }

    public String json() {
        return "{ '%s': '%s', '%s': '%s', '%s': '%s'}".formatted(Fields.id, id, Fields.guid, guid, Fields.name, name);
    }
}
