package eu.radvan.indraavitechtask.command;

import eu.radvan.indraavitechtask.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeleteAllCommand implements Command {
    private final UserRepository repository;

    public DeleteAllCommand(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        Integer deletedCount = repository.deleteAll();

        log.info("Deleted count: " + deletedCount);
    }


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}