package eu.radvan.indraavitechtask;

import eu.radvan.indraavitechtask.repository.UserRepository;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

import static eu.radvan.indraavitechtask.constants.Constants.PERSISTENCE_UNIT_NAME;

@Slf4j
public class ApplicationRunner {


    public static void main(String[] args) {
        try (var entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME)) {
            var userRepository = new UserRepository(entityManagerFactory);

            Application application = new Application(userRepository);
            application.run();
        }
    }

}