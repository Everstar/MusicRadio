package com.musicbubble.danmu.config;

import com.musicbubble.danmu.DanmuHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by happyfarmer on 5/6/2017.
 */
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(textHandler(), "/danmu").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler textHandler(){
        return new DanmuHandler();
    }
}
