package com.musicbubble.danmu.config;

import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * Created by happyfarmer on 5/6/2017.
 */
public class DanmuInterceptor extends HttpSessionHandshakeInterceptor {
    private static Logger LOGGER = Logger.getLogger(DanmuInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        LOGGER.info("Before Handshake");
        String song_id = ((ServletServerHttpRequest) request).getServletRequest().getParameter("id");
        String user_id = ((ServletServerHttpRequest) request).getServletRequest().getParameter("user");
        if (song_id == null || song_id == "" || user_id == null || user_id == "") {
            LOGGER.error("url wrong format");
            return false;
        }
        attributes.put("song_id", song_id);
        attributes.put("user_id", user_id);
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        LOGGER.info("After Handshake");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
