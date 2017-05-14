package com.musicbubble.danmu;

import com.musicbubble.danmu.cache.DanmuTask;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by happyfarmer on 5/6/2017.
 */
public class DanmuHandler extends TextWebSocketHandler {
    private final static Map<String, List<WebSocketSession>> sessions = new ConcurrentHashMap<>();
    private final static Logger LOGGER = Logger.getLogger(DanmuHandler.class);
    private Timer storeTimer;

    @Autowired
    private DanmuTask danmuTask;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String song_id = (String) session.getAttributes().get("song_id");
        String user_id = (String) session.getAttributes().get("user_id");
        String global_time = new Timestamp(System.currentTimeMillis()).toString();
        int index = message.getPayload().lastIndexOf('|');
        String local_time, text;
        if (index != -1) {
            text = message.getPayload().substring(0, index);
            local_time = message.getPayload().substring(index + 1);
        } else {
            LOGGER.error("message wrong format......");
            return;
        }
        TextMessage returnMessage = new TextMessage(text);
        for (WebSocketSession s : sessions.get(song_id)) {
            s.sendMessage(returnMessage);
        }

        //数据存储
        danmuTask.save(returnMessage.getPayload(), local_time, global_time, user_id, song_id);
        LOGGER.info("Danmu : " + text + " " + user_id + " " + song_id + " " + local_time + " " + global_time);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String song_id = (String) session.getAttributes().get("song_id");
        LOGGER.info("Connection Established!" + "song_id=" + song_id);

        List<WebSocketSession> list = null;
        if (sessions.containsKey(song_id))
            list = sessions.get(song_id);
        else
            list = new ArrayList<WebSocketSession>();
        list.add(session);
        sessions.put(song_id, list);

        if (storeTimer == null) {
            Timer timer = new Timer();
            timer.schedule(danmuTask, 3000, 1000);
            storeTimer = timer;
            LOGGER.info("storeTimer started!!!");
        }

    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String song_id = (String) session.getAttributes().get("song_id");
        LOGGER.info("Connection Closed!" + "song_id=" + song_id);

        sessions.get(song_id).remove(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        String song_id = (String) session.getAttributes().get("song_id");
        LOGGER.info("websocket chat connection closed......" + "song_id=" + song_id);
        sessions.get(song_id).remove(session);
    }


    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
