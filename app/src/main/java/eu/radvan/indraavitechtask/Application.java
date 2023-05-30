package eu.radvan.indraavitechtask;

import eu.radvan.indraavitechtask.model.User;
import eu.radvan.indraavitechtask.model.dto.CreateUserDto;
import eu.radvan.indraavitechtask.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class Application {

    private final UserRepository userRepository;

    public Application(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void run() {
        add(1, "a1", "Robert");
        add(2, "a2", "Martin");

        printAll();
        deleteAll();
        printAll();

    }

    private void add(long id, String guid, String name) {
        User user = userRepository.create(new CreateUserDto(id, guid, name));

        log.info("Added: " + user);
    }

    private void printAll() {
        List<User> all = userRepository.findAll();

        log.info("Find all: " + Arrays.toString(all.toArray()));
    }

    private void deleteAll() {
        Integer deletedCount = userRepository.deleteAll();

        log.info("Deleted count: " + deletedCount);
    }
}
