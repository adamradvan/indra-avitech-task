package eu.radvan.indraavitechtask;

import eu.radvan.indraavitechtask.command.Command;
import eu.radvan.indraavitechtask.queue.CommandConsumer;
import eu.radvan.indraavitechtask.queue.CommandProducer;
import eu.radvan.indraavitechtask.repository.UserRepository;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static eu.radvan.indraavitechtask.constants.Constants.PERSISTENCE_UNIT_NAME;

@Slf4j
public class ApplicationRunner {

    public static void main(String[] args) {
        try (var entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME)) {
            var userRepository = new UserRepository(entityManagerFactory);

            BlockingQueue<Command> queue = new LinkedBlockingQueue<>();
            CommandProducer producer = new CommandProducer(userRepository, queue);
            CommandConsumer consumer = new CommandConsumer(queue);

            Thread producerThread = new Thread(producer);
            Thread consumerThread = new Thread(consumer);

            log.info("Starting two threads.");
            producerThread.start();
            consumerThread.start();

            try {
                // Wait for both threads to finish
                log.info("Waiting for both threads to finish...");
                producerThread.join();
                consumerThread.join();
                log.info("Both threads have finished.");
            } catch (InterruptedException e) {
                // Log and handle interruption
                log.error("Threads were interrupted: " + e);
                Thread.currentThread().interrupt();
            }
        }
    }

}