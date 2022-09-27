package com.malerx.bot;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.discovery.event.ServiceReadyEvent;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Slf4j
public class BotRegistryService implements ApplicationEventListener<ServiceReadyEvent> {
    @Inject
    LongPollingBot assistantBot;

    @Override
    public void onApplicationEvent(ServiceReadyEvent event) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            log.info("onApplicationEvent() -> registering bot {}", assistantBot.getBotUsername());
            telegramBotsApi.registerBot(assistantBot);
        } catch (TelegramApiException e) {
            log.error("onApplicationEvent() -> failed start bot", e);
        }
    }
}
