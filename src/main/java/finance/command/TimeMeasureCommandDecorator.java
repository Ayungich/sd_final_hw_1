package finance.command;

import com.fasterxml.jackson.core.JsonProcessingException;

public class TimeMeasureCommandDecorator implements Command {

    private final Command command;

    public TimeMeasureCommandDecorator(Command command) { this.command = command; }

    @Override
    public void execute() throws JsonProcessingException {
        long start = System.nanoTime();
        command.execute();
        long end = System.nanoTime();
        System.out.println("Время выполнения: " + (end - start) / 1_000_000.0 + " ms");
    }
}
