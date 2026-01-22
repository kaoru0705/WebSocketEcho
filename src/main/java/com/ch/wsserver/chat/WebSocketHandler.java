package com.ch.wsserver.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    // 접속 마다 1:1 대응하는 접속 구분 객체
    // 접속 시
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("{}의 접속 감지", session.getId());

        // 접속한 클라이언트에게 메시지 전송
        session.sendMessage(new TextMessage("CONNECTED : sessionID=" + session.getId()));
    }

    // 대화를 주고 받는
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }

    // 에러가 났을 때
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    // 접속 종료
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}
