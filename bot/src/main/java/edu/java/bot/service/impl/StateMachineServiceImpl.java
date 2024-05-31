package edu.java.bot.service.impl;

import edu.java.bot.service.StateMachineService;
import edu.java.bot.statemachine.event.BotEvent;
import edu.java.bot.statemachine.state.BotState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class StateMachineServiceImpl implements StateMachineService {
    private final StateMachineFactory<BotState, BotEvent> stateMachineFactory;
    private final StateMachinePersister<BotState, BotEvent, Long> persister;
    @Override
    public void changeState(Long userID, Mono<Message<BotEvent>> state) {
        final StateMachine<BotState, BotEvent> stateMachine = stateMachineFactory.getStateMachine();
        try {
            persister.restore(stateMachine, userID);
                stateMachine.sendEvent(state).blockFirst();
            persister.persist(stateMachine, userID);
        } catch (Exception e)  {
            log.error("Error persisting state machine", e);
        }
    }
}
