package eu.radvan.indraavitechtask.command;


import eu.radvan.indraavitechtask.model.User;
import eu.radvan.indraavitechtask.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class PrintAllCommand implements Command {
    private final UserRepository repository;

    public PrintAllCommand(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        List<User> allUsers = repository.findAll();

        log.info("Find all: " + Arrays.toString(allUsers.toArray()));
    }


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}