package edu.java.bot.service;

import edu.java.bot.statemachine.event.BotEvent;
import org.springframework.messaging.Message;
import reactor.core.publisher.Mono;

public interface StateMachineService {
    void changeState(Long userID, Mono<Message<BotEvent>> state);
}
