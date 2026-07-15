package cli;

import java.util.ArrayList;
import java.util.List;

public class CommandLine {
    private final String command;
    private final List<String> args;
    public CommandLine(String command,List<String> args){
        this.command = command;
        this.args = new ArrayList<>(args);
    }

    public String getCommand() {
        return command;
    }

    public List<String> getArgs() {
        return args;
    }
}
