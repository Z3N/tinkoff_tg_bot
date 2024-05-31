package edu.java.bot.statemachine.action;

import edu.java.bot.statemachine.event.BotEvent;
import edu.java.bot.statemachine.state.BotState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class ActionHelp implements Action<BotState, BotEvent> {
    @Override
    public void execute(StateContext<BotState, BotEvent> context) {

    }
}
