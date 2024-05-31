package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.interfaces.Command;
import edu.java.bot.service.StateMachineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("/start")
@RequiredArgsConstructor
public class StartCommand implements Command {
    private static final String COMMAND = "/start";
    private static final String DESCRIPTION = "register a new user";
    private final StateMachineService stateMachineService;

    @Override public String command() {
        return COMMAND;
    }

    @Override public String description() {
        return DESCRIPTION;
    }

    @Override public SendMessage handle(Update update) {
        //TODO: implement
        return new SendMessage(update.message().chat().id(), "User added");
    }
}
