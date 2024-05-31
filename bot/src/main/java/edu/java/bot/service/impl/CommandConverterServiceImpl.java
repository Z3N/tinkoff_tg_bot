package edu.java.bot.service.impl;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.CommandConverterService;
import edu.java.bot.service.ValidURLDomainService;
import edu.java.bot.statemachine.event.BotEvent;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.EnumSet;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CommandConverterServiceImpl implements CommandConverterService {

    private final EnumSet<BotEvent> events = EnumSet.allOf(BotEvent.class);
    private final ValidURLDomainService validURLDomainService;

    @Override
    public Mono<Message<BotEvent>> convert(String command, Update update) {
        if (!command.startsWith("/")) {
            try {
                URI uri = new URI(command);
                uri.toURL();
                return supportedURL(update.message().from().id(), uri);
            } catch (URISyntaxException | IllegalArgumentException | NullPointerException | MalformedURLException ignored) {
                return Mono.just(MessageBuilder.withPayload(BotEvent.UNKNOWN_COMMAND).setHeader("ID", update.message().from().id()).build());
            }
        }
        try {
            BotEvent event = BotEvent.valueOf(command.substring(1).toUpperCase());
            if (events.contains(event)) {
                return Mono.just(MessageBuilder.withPayload(event).setHeader("ID", update.message().from().id()).build());
            }
        } catch (IllegalArgumentException ignored) {
            return Mono.just(MessageBuilder.withPayload(BotEvent.UNKNOWN_COMMAND).setHeader("ID", update.message().from().id()).build());
        }
        return Mono.just(MessageBuilder.withPayload(BotEvent.UNKNOWN_COMMAND).setHeader("ID", update.message().from().id()).build());
    }
    private Mono<Message<BotEvent>> supportedURL(Long ID, URI uri) {
        if (validURLDomainService.isValidDomain(uri)) {
            return Mono.just(MessageBuilder.withPayload(BotEvent.URL).setHeader("ID", ID).setHeader("URI", uri).build());
        } else {
            return Mono.just(MessageBuilder.withPayload(BotEvent.UNSUPPORTED_URL).setHeader("ID", ID).build());
        }
    }
}
