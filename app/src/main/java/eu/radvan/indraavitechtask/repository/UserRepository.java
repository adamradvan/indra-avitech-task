package eu.radvan.indraavitechtask.repository;

import eu.radvan.indraavitechtask.model.User;
import eu.radvan.indraavitechtask.model.dto.UserCreateDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static eu.radvan.indraavitechtask.constants.Constants.PERSISTENCE_UNIT_NAME;

@Slf4j
public class UserRepository {

    private static UserRepository INSTANCE;

    private final EntityManagerFactory entityManagerFactory;

    private UserRepository() {
        log.info("Initialising Entity Manager Factory with persistence unit name: {}", PERSISTENCE_UNIT_NAME);
        this.entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public static synchronized UserRepository getInstance() {
        return INSTANCE == null ? INSTANCE = new UserRepository() : INSTANCE;
    }

    public void release() {
        if (this.entityManagerFactory != null) {
            log.info("Releasing Entity Manager Factory");

            this.entityManagerFactory.close();
            INSTANCE = null;
        }
    }

    public User create(UserCreateDto dto) {
        User user = new User();
        user.setName(dto.name());

        return runInTransaction(em -> em.merge(user));
    }

    public User create(long id, String guid, String name) {
        User user = new User(id, guid, name);

        return runInTransaction(em -> em.merge(user));
    }

    public Integer deleteAll() {
        return runInTransaction(em -> em
                .createQuery("delete from User u")
                .executeUpdate()
        );
    }

    public List<User> findAll() {
        return runInTransaction(em -> em
                .createQuery(
                        "select u from User u",
                        User.class
                )
                .getResultList());
    }

    private  <T> T runInTransaction(Function<EntityManager, T> function) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        boolean success = false;
        try {
            T returnValue = function.apply(entityManager);
            success = true;
            return returnValue;
        } finally {
            if (success) {
                transaction.commit();
            } else {
                transaction.rollback();
            }
        }
    }

    private void runInTransactionWithoutResult(Consumer<EntityManager> callback) {
        runInTransaction(entityManager -> {
            callback.accept(entityManager);
            return null;
        });
    }
}