package edu.java.bot.configuration;

import edu.java.bot.statemachine.event.BotEvent;
import edu.java.bot.statemachine.listener.BotCommandListener;
import edu.java.bot.statemachine.persist.DefaultBotStateMachinePersister;
import edu.java.bot.statemachine.state.BotState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import reactor.core.publisher.Mono;
import java.util.EnumSet;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<BotState, BotEvent> {
    @Override
    public void configure(final StateMachineConfigurationConfigurer<BotState, BotEvent> config) throws Exception {
        config
            .withConfiguration()
            .autoStartup(true)
            .listener(new BotCommandListener());

    }

    @Override
    public void configure(final StateMachineStateConfigurer<BotState, BotEvent> states) throws Exception {
        states
            .withStates()
            .initial(BotState.WAITED_COMMAND)
            .states(EnumSet.allOf(BotState.class))
            .state(BotState.WAITED_COMMAND)
            .stateDo(BotState.HELP, ctx -> ctx.getStateMachine().sendEvent(Mono.just(MessageBuilder.withPayload(BotEvent.NOP).setHeader("ID", ctx.getMessageHeader("ID")).build())).subscribe())
            .state(BotState.HELP, BotEvent.NOP)
            .stateDo(BotState.UNSUPPORTED_URL, ctx -> ctx.getStateMachine().sendEvent(Mono.just(MessageBuilder.withPayload(BotEvent.NOP).setHeader("ID", ctx.getMessageHeader("ID")).build())).subscribe())
            .state(BotState.UNSUPPORTED_URL, BotEvent.NOP);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<BotState, BotEvent> transitions) throws Exception {

        transitions
            .withExternal()
            .source(BotState.WAITED_COMMAND)
            .target(BotState.WAITED_COMMAND)
            .event(BotEvent.UNKNOWN_COMMAND)
            //.actionFunction(ctx -> Mono.delay(Duration.from(Duration.ofSeconds(1))).doOnNext(aLong -> log.info("Point1 - check")).then())

            .and()
            .withExternal()
            .source(BotState.WAITED_COMMAND)
            .target(BotState.TRACKED)
            .event(BotEvent.TRACK)

            .and()
            .withExternal()
            .source(BotState.TRACKED)
            .target(BotState.WAITED_COMMAND)
            .event(BotEvent.URL)

            .and()
            .withExternal()
            .source(BotState.TRACKED)
            .target(BotState.UNSUPPORTED_URL)
            .event(BotEvent.UNSUPPORTED_URL)

            .and()
            .withExternal()
            .source(BotState.UNSUPPORTED_URL)
            .target(BotState.WAITED_COMMAND)
            .event(BotEvent.NOP)

            .and()
            .withExternal()
            .source(BotState.WAITED_COMMAND)
            .target(BotState.UNTRACKED)
            .event(BotEvent.UNTRACK)

            .and()
            .withExternal()
            .source(BotState.UNTRACKED)
            .target(BotState.WAITED_COMMAND)
            .event(BotEvent.URL)

            .and()
            .withExternal()
            .source(BotState.WAITED_COMMAND)
            .target(BotState.HELP)
            .event(BotEvent.HELP)

            .and()
            .withExternal()
            .source(BotState.HELP)
            .target(BotState.WAITED_COMMAND)
            .event(BotEvent.NOP)
        ;
    }

    @Bean
    public StateMachinePersister<BotState, BotEvent, Long> persister() {
        return new DefaultStateMachinePersister<>(new DefaultBotStateMachinePersister());
    }

}
