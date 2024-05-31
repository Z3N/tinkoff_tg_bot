package edu.java.bot;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.interfaces.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
@RequiredArgsConstructor
public class BotApplication implements CommandLineRunner {
    private final Bot bot;

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }

    @Override
    public void run(String... args) {
        bot.start();
    }
}
