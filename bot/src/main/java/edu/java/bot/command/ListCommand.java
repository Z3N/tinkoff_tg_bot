package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.interfaces.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("/list")
@RequiredArgsConstructor
public class ListCommand implements Command {
    private static final String COMMAND = "/list";
    private static final String DESCRIPTION = "show tracked links";

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
