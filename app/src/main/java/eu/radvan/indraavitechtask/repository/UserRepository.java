package eu.radvan.indraavitechtask.repository;

import eu.radvan.indraavitechtask.model.User;
import eu.radvan.indraavitechtask.model.dto.CreateUserDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Function;

@Slf4j
public class UserRepository {

    private final EntityManagerFactory entityManagerFactory;

    public UserRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public User create(CreateUserDto dto) {
        if (dto == null) throw new IllegalArgumentException("CreateUserDto cannot be null.");

        User user = new User(dto.id(), dto.guid(), dto.name());

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

    private <T> T runInTransaction(Function<EntityManager, T> function) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
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
    }

}