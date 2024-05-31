package edu.java.bot.statemachine.persist;

import edu.java.bot.statemachine.event.BotEvent;
import edu.java.bot.statemachine.state.BotState;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import java.util.HashMap;

public class DefaultBotStateMachinePersister implements StateMachinePersist<BotState, BotEvent, Long> {
    private final HashMap<Long, StateMachineContext<BotState, BotEvent>> contexts = new HashMap<>();
    @Override
    public void write(final StateMachineContext<BotState, BotEvent> stateMachineContext, final Long ContextObject) throws Exception {
        contexts.put(ContextObject, stateMachineContext);
    }

    @Override
    public StateMachineContext<BotState, BotEvent> read(final Long ContextObject) throws Exception {
        return contexts.get(ContextObject);
    }
}
