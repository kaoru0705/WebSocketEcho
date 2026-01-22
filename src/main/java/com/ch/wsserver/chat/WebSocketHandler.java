package com.ch.wsserver.chat;

import com.ch.wsserver.chat.dto.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    // 스프링 부트는 친Rest 환경이기 때문에 이미 jackson 라이브러리가 존재함
    private final ObjectMapper objectMapper;

    // 접속 마다 1:1 대응하는 접속 구분 객체
    // 접속 시
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("{}의 접속 감지", session.getId());

        // 접속한 클라이언트에게 메시지 전송
        session.sendMessage(new TextMessage("CONNECTED : sessionID=" + session.getId()));
    }

    // 클라이언트로부터 메시지가 도착하면 아래의 메서드가 호출됨...
    // 대화를 주고 받는
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        log.debug("클라이언트가 전송한 메시지: {} ", payload);

        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);     // json 문자열이 --> ChatMessage로 자동변환

        // {"id":"zino","msg":input,"icon":"smile", "time":"2026:01:20xxxx"}

        ChatMessage response = new ChatMessage(chatMessage.getId()
                , chatMessage.getMessage()
                , chatMessage.getIcon()
                , LocalDateTime.now());     // 서버에서 생성한 날짜를 껴넣기

        // response인 ChatMessage 객체는 자바의 객체이므로, 그 자체를 통신으로 전송할 수 없다.
        // 따라서 전송을 위해서는 문자열화 되어야 한다.... Object --> json 문자열로 (jackson 사용)
        String json = objectMapper.writeValueAsString(response);
        log.debug("클라이언트에게 전송할 json 문자열은 {}", json);

        // 서버는 클라이언트의 메시지를 다시 보내줘야 함...
        session.sendMessage(new TextMessage(json));
    }

    // 에러가 났을 때
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    // 접속 종료
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.debug("{}가 접속 해제 함", session.getId());
    }
}
