package eu.radvan.indraavitechtask;

import eu.radvan.indraavitechtask.model.User;
import eu.radvan.indraavitechtask.model.dto.CreateUserDto;
import eu.radvan.indraavitechtask.repository.UserRepository;
import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserRepositoryTest {
    @Mock
    private EntityManagerFactory emf;

    @Mock
    private EntityManager em;

    @Mock
    private EntityTransaction transaction;

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        when(emf.createEntityManager()).thenReturn(em);
        when(em.getTransaction()).thenReturn(transaction);
        userRepository = new UserRepository(emf);
    }

    @Test
    @DisplayName("create_NullDto_ThrowsIllegalArgumentException")
    void testCreate_withNullDto() {
        assertThrows(IllegalArgumentException.class, () -> userRepository.create(null));
    }

    @Test
    @DisplayName("create_ValidDto_ReturnsExpectedUser")
    void testCreate_success() {
        CreateUserDto dto = new CreateUserDto(1L, "guid", "name");
        User expectedUser = new User(dto.id(), dto.guid(), dto.name());

        when(em.merge(any(User.class))).thenReturn(expectedUser);

        User actualUser = userRepository.create(dto);

        assertEquals(expectedUser, actualUser);
        verify(em).merge(expectedUser);
    }

    @Test
    @DisplayName("create_ThrowsPersistenceException_WhenDatabaseErrorOccurs")
    void testCreate_withDatabaseException() {
        CreateUserDto dto = new CreateUserDto(1L, "guid", "name");

        when(em.merge(any(User.class))).thenThrow(PersistenceException.class);

        assertThrows(PersistenceException.class, () -> userRepository.create(dto));
    }


    @Test
    @DisplayName("deleteAll_NoUsers_ReturnsZero")
    void testDeleteAll_whenNoUsers() {
        Query queryForDelete = mock(Query.class);
        when(em.createQuery(any(String.class))).thenReturn(queryForDelete);
        when(queryForDelete.executeUpdate()).thenReturn(0);

        Integer deleted = userRepository.deleteAll();

        assertEquals(0, deleted);
    }

    @Test
    @DisplayName("deleteAll_UsersExist_ReturnsDeletedCount")
    void testDeleteAll_whenUsersExist() {
        Query queryForDelete = mock(Query.class);
        when(em.createQuery(any(String.class))).thenReturn(queryForDelete);
        when(queryForDelete.executeUpdate()).thenReturn(5);

        Integer deleted = userRepository.deleteAll();

        assertEquals(5, deleted);
    }

    @Test
    @DisplayName("deleteAll_ThrowsPersistenceException_WhenDatabaseErrorOccurs")
    void testDeleteAll_withDatabaseException() {
        Query queryForDelete = mock(Query.class);
        when(em.createQuery(any(String.class))).thenReturn(queryForDelete);
        when(queryForDelete.executeUpdate()).thenThrow(PersistenceException.class);

        assertThrows(PersistenceException.class, () -> userRepository.deleteAll());
    }

    @Test
    @DisplayName("findAll_NoUsers_ReturnsEmptyList")
    void testFindAll_whenNoUsers() {
        TypedQuery<User> query = mock(TypedQuery.class);
        when(em.createQuery(any(String.class), eq(User.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        List<User> users = userRepository.findAll();

        assertTrue(users.isEmpty());
    }

    @Test
    @DisplayName("findAll_UsersExist_ReturnsUserList")
    void testFindAll_whenUsersExist() {
        TypedQuery<User> query = mock(TypedQuery.class);
        User user1 = new User(1L, "guid1", "name1");
        User user2 = new User(2L, "guid2", "name2");
        when(em.createQuery(any(String.class), eq(User.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userRepository.findAll();

        assertEquals(2, users.size());
        assertTrue(users.containsAll(Arrays.asList(user1, user2)));
    }

    @Test
    @DisplayName("findAll_ThrowsPersistenceException_WhenDatabaseErrorOccurs")
    void testFindAll_withDatabaseException() {
        TypedQuery<User> query = mock(TypedQuery.class);
        when(em.createQuery(any(String.class), eq(User.class))).thenReturn(query);
        when(query.getResultList()).thenThrow(PersistenceException.class);

        assertThrows(PersistenceException.class, () -> userRepository.findAll());
    }
}
