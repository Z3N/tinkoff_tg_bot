package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.interfaces.Command;
import edu.java.bot.statemachine.event.BotEvent;
import edu.java.bot.statemachine.state.BotState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Component;

@Slf4j
@Component("/track")
@RequiredArgsConstructor
public class TrackCommand implements Command {
    private static final String COMMAND = "/track";
    private static final String DESCRIPTION = "track a link";
    private final StateMachineFactory<BotState, BotEvent> stateMachineFactory;
    private final StateMachinePersister<BotState, BotEvent, Long> persister;

    @Override public String command() {
        return COMMAND;
    }

    @Override public String description() {
        return DESCRIPTION;
    }

    @Override public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), "Link added");
    }
}
