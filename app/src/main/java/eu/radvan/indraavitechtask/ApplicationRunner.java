package eu.radvan.indraavitechtask;

import eu.radvan.indraavitechtask.model.User;
import eu.radvan.indraavitechtask.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class ApplicationRunner {

    private static final UserRepository USER_REPOSITORY = UserRepository.getInstance();

    public static void main(String[] args) {
        try {
            add(1, "a1", "Robert");
            add(2, "a2", "Martin");

            printAll();
            deleteAll();
            printAll();

        } finally {
            USER_REPOSITORY.release();
        }
    }

    private static void add(long id, String guid, String Robert) {
        User user = USER_REPOSITORY.create(id, guid, Robert);

        log.info("Added: " + user);
    }

    private static void printAll() {
        List<User> all = USER_REPOSITORY.findAll();

        log.info("Find all: " + Arrays.toString(all.toArray()));
    }

    private static void deleteAll() {
        Integer deletedCount = USER_REPOSITORY.deleteAll();

        log.info("Deleted count: " + deletedCount);
    }


}