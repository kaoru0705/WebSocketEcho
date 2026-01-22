package com.ch.wsserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    // 웹소켓의 접속, 해제, 메세지 처리 등을 담당하는 핸들러 객체.. 보유..
    private final WebSocketHandler webSocketHandler;

    // 개발 단계에서는 React, Vue와 같은 프로젝트에서 접근할 예정이므로, 접근자체를 허용해주어야 함.
    // 하지만 실제 제품화 시켰을 경우, 존재하는 도메인에 대해 접속을 허용 ex) text.com
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //
        registry.addHandler(webSocketHandler, "/ws/echo")    // 클라이언트가 서버에 접속할 때의 URI
                .setAllowedOriginPatterns("*");      // 개발할 때는 연습이기 때문에 모두 열어주고, 실제 제품화 될 때는 특정 도메인 적기
    }


}