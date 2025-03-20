package finance.command;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Command {
    void execute() throws JsonProcessingException;
}
