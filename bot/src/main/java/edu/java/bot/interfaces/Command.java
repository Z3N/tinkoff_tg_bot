package edu.java.bot.interfaces;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Optional;

public interface Command {
    String command();

    String description();

    SendMessage handle(Update update);

    default boolean supports(Update update) {
        return Optional.ofNullable(update.message()).map(Message::text).filter(text -> text.equals(command()))
            .isPresent();
    }
}
