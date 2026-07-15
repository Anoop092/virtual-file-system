package cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandParser {
    public CommandLine parse(String str){
        String[] newStr = str.split("\\s+");
        List<String> args = new ArrayList<>();
        args.addAll(Arrays.asList(newStr).subList(1, newStr.length));
        return new CommandLine(newStr[0],args);
    }
}
