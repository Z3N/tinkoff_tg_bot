package edu.java.bot.service.impl;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.interfaces.Bot;
import edu.java.bot.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final Bot bot;
    @Override
    public void sendMessage(SendMessage message) {
        bot.execute(message);
    }
}
