package edu.java.bot.interfaces;

import java.util.Map;

public interface CommandAggregator {
    Map<String, ? extends Command> gets();

    Command get(String name);
}
