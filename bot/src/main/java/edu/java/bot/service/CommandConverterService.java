package edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.statemachine.event.BotEvent;
import org.springframework.messaging.Message;
import reactor.core.publisher.Mono;

public interface CommandConverterService {
    Mono<Message<BotEvent>> convert(String command, Update update);
}
