package edu.java.bot.statemachine.listener;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.interfaces.CommandAggregator;
import edu.java.bot.service.LinkService;
import edu.java.bot.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.annotation.EventHeader;
import org.springframework.statemachine.annotation.OnEventNotAccepted;
import org.springframework.statemachine.annotation.OnStateChanged;
import org.springframework.statemachine.annotation.WithStateMachine;
import java.net.URI;
import java.util.stream.Collectors;

@WithStateMachine
@RequiredArgsConstructor
public class EventListener {
    private static final Logger log = LoggerFactory.getLogger(EventListener.class);
    private final MessageService messageService;
    private final LinkService linkService;
    private final CommandAggregator commandAggregator;

    @OnStateChanged(source = "TRACKED", target = "WAITED_COMMAND")
    public void track(@EventHeader("ID") Long ID, @EventHeader("URI") URI uri) {
        linkService.save(ID, uri);
        messageService.sendMessage(new SendMessage(ID, "URL is successfully tracked"));
    }

    @OnStateChanged(source = "UNTRACKED", target = "WAITED_COMMAND")
    public void untrack(@EventHeader("ID") Long ID, @EventHeader("URI") URI uri) {
        linkService.remove(ID, uri);
        messageService.sendMessage(new SendMessage(ID, "URI is successfully untracked"));
    }

    @OnStateChanged(source = "WAITED_COMMAND", target = "HELP")
    public void help(@EventHeader("ID") Long ID) {
        String commands = commandAggregator.gets().keySet().stream().collect(Collectors.joining("\n"));
        messageService.sendMessage(new SendMessage(ID, "List of commands:\n" + commands));
    }
    @OnStateChanged(source = "UNSUPPORTED_URL", target = "WAITED_COMMAND")
    public void url(@EventHeader("ID") Long ID) {
        messageService.sendMessage(new SendMessage(ID, "Invalid URL. Please, try again"));
    }
}
