package edu.java.bot.statemachine.listener;

import edu.java.bot.statemachine.event.BotEvent;
import edu.java.bot.statemachine.state.BotState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

@Slf4j
public class BotCommandListener implements StateMachineListener<BotState, BotEvent> {
    @Override
    public void stateChanged(State<BotState, BotEvent> from, State<BotState, BotEvent> to) {
        if (from != null) {
            log.info("State changed from {} to {}", from.getId(), to.getId());
        }
    }

    @Override
    public void stateEntered(State<BotState, BotEvent> state) {

    }

    @Override
    public void stateExited(State<BotState, BotEvent> state) {

    }

    @Override
    public void eventNotAccepted(Message<BotEvent> message) {
        log.warn("Event not accepted: {}", message);
    }

    @Override
    public void transition(Transition<BotState, BotEvent> transition) {
        log.info("Transition: {}", transition);
    }

    @Override
    public void transitionStarted(Transition<BotState, BotEvent> transition) {
        log.info("Transition started: {}", transition);
    }

    @Override
    public void transitionEnded(Transition<BotState, BotEvent> transition) {
        log.info("Transition ended: {}", transition);
    }

    @Override
    public void stateMachineStarted(StateMachine<BotState, BotEvent> stateMachine) {
        log.info("State machine started");
    }

    @Override
    public void stateMachineStopped(StateMachine<BotState, BotEvent> stateMachine) {

    }

    @Override
    public void stateMachineError(StateMachine<BotState, BotEvent> stateMachine, Exception e) {
        log.error("State machine error", e);
    }

    @Override
    public void extendedStateChanged(Object o, Object o1) {
        log.info("Extended state changed: {}, {}", o, o1);
    }

    @Override
    public void stateContext(StateContext<BotState, BotEvent> stateContext) {
        //log.info("State context: {}", stateContext);
    }
}
