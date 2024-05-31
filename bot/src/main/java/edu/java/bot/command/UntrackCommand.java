package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.interfaces.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("/untrack")
@RequiredArgsConstructor
public class UntrackCommand implements Command {
    private static final String COMMAND = "/untrack";
    private static final String DESCRIPTION = "stop tracking a link";

    @Override public String command() {
        return COMMAND;
    }

    @Override public String description() {
        return DESCRIPTION;
    }

    @Override public SendMessage handle(Update update) {
        return null;
    }
}
