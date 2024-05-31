package edu.java.bot.interfaces;

import com.pengrad.telegrambot.model.Update;

public interface UserMessageProcessor {

    void process(Update update);
}
