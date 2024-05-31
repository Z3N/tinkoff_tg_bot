package edu.java.bot.aggregator;

import edu.java.bot.interfaces.Command;
import edu.java.bot.interfaces.CommandAggregator;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Commands implements CommandAggregator {
    private final Map<String, ? extends Command> availableCommands;

    @Override public Map<String, ? extends Command> gets() {
        return this.availableCommands;
    }

    @Override public Command get(String name) {
        return this.availableCommands.get(name);
    }
}
