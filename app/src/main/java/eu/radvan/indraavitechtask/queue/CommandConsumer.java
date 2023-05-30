package eu.radvan.indraavitechtask.queue;

import eu.radvan.indraavitechtask.command.Command;
import eu.radvan.indraavitechtask.command.ShutdownCommand;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CommandConsumer implements Runnable {
    private final BlockingQueue<Command> queue;

    public CommandConsumer(BlockingQueue<Command> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                log.info("Polling the queue...");
                Command command = queue.poll(2, TimeUnit.SECONDS); // wait for 0.5 seconds

                if (command == null) {
                    log.info("Queue is empty");
                    continue;
                }

                if (command instanceof ShutdownCommand) {
                    log.info("Shutting down the consumer.");
                    break;
                }

                log.info("Executing command '{}'", command.getClass().getSimpleName());
                command.execute();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
