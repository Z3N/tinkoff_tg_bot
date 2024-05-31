package edu.java.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.interfaces.Bot;
import edu.java.bot.interfaces.Command;
import edu.java.bot.interfaces.UserMessageProcessor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import edu.java.bot.statemachine.event.BotEvent;
import edu.java.bot.statemachine.state.BotState;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

@Service
public class CommandBot implements Bot {
    private final UserMessageProcessor messageProcessor;
    private final StateMachineFactory<BotState, BotEvent> stateMachineFactory;
    private final TelegramBot bot;

    public CommandBot(UserMessageProcessor messageProcessor, ApplicationConfig config, StateMachineFactory<BotState, BotEvent> stateMachineFactory, Command[] commands) {
        this.messageProcessor = messageProcessor;
        this.bot = new TelegramBot(config.telegramToken());
        SetMyCommands commandArray = new SetMyCommands(Arrays.stream(commands)
            .map(command -> new BotCommand(command.command(), command.description())).toArray(BotCommand[]::new));
        this.bot.execute(commandArray);
        this.stateMachineFactory = stateMachineFactory;
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            messageProcessor.process(update);
        }
        return CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void start() {
        bot.setUpdatesListener(this);
    }

    @Override
    public void close() {
        bot.shutdown();
    }
}
