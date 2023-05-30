package eu.radvan.indraavitechtask.queue;

import eu.radvan.indraavitechtask.command.*;
import eu.radvan.indraavitechtask.model.dto.CreateUserDto;
import eu.radvan.indraavitechtask.repository.UserRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;

@Slf4j
public class CommandProducer implements Runnable {
    private final UserRepository repository;
    private final BlockingQueue<Command> queue;

    public CommandProducer(UserRepository repository, BlockingQueue<Command> queue) {
        this.repository = repository;
        this.queue = queue;
    }

    @SneakyThrows
    @Override
    public void run() {
        add(1, "a1", "Robert");
        add(2, "a2", "Martin");

        printAll();
        deleteAll();
        printAll();

        shutdown(); // Shut down the consumer
    }


    private void add(long id, String guid, String name) {
        putIntoQueue(new AddCommand(repository, new CreateUserDto(id, guid, name)));
    }

    private void printAll() {
        putIntoQueue(new PrintAllCommand(repository));
    }

    private void deleteAll() {
        putIntoQueue(new DeleteAllCommand(repository));

    }

    private void shutdown() {
        putIntoQueue(new ShutdownCommand());
    }

    @SneakyThrows
    private void putIntoQueue(Command command) {
        log.info("Producing command '{}'", command);

        queue.put(command);
    }
}
