package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;

public interface MessageService {

    void sendMessage(SendMessage message);
}
