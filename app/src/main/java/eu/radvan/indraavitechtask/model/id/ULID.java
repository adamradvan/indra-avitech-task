package eu.radvan.indraavitechtask.model.id;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Universally Unique Lexicographically Sortable Identifier
 * (<a href="https://github.com/ulid/spec">ULID GitHub</a>)
 * <p>
 * Used as GUID instead of UUID
 * - e.g. {@code "01GZNXTKH37CNK2FHFNHHPQKC2"}
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ULID {

    private static final de.huxhorn.sulky.ulid.ULID INSTANCE = new de.huxhorn.sulky.ulid.ULID();

    public static String generate() {
        return INSTANCE.nextULID();
    }
}