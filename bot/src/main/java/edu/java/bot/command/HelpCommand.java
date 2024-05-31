package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.interfaces.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("/help")
@RequiredArgsConstructor
public class HelpCommand implements Command {
    private static final String COMMAND = "/help";
    private static final String DESCRIPTION = "provide help";

    @Override public String command() {
        return COMMAND;
    }

    @Override public String description() {
        return DESCRIPTION;
    }

    @Override public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), "This is not so helpful...");
    }
}
