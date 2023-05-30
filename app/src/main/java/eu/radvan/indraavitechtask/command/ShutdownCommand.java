package eu.radvan.indraavitechtask.command;

public class ShutdownCommand implements Command {
    @Override
    public void execute() {
        // nothing to do
    }


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
