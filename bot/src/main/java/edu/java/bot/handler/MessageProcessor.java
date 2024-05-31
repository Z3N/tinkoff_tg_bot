package edu.java.bot.handler;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.interfaces.Command;
import edu.java.bot.interfaces.UserMessageProcessor;
import java.util.Map;
import edu.java.bot.service.CommandConverterService;
import edu.java.bot.service.StateMachineService;
import edu.java.bot.statemachine.event.BotEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MessageProcessor implements UserMessageProcessor {
    private final Map<String, Command> commands;
    private final CommandConverterService commandConverterService;
    private final StateMachineService stateMachineService;

    @Override
    public void process(Update update) {
        Mono<Message<BotEvent>> event = commandConverterService.convert(update.message().text(), update);
        stateMachineService.changeState(update.message().from().id(), event);
    }
}
