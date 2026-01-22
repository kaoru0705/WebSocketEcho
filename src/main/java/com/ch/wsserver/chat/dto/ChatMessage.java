package com.ch.wsserver.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/*
    메시지 한 건을 표현한 DTO 객체
    메시지는 문자열임에도 불구하고 DTO로 정의하는 이유는? java객체와 JSON 문자열 자동
    변환해주는 MessageConverter를 이용하기 위해..
 */

@Getter
@Setter
// 모든 필드에 대해 자동으로 생성자
@AllArgsConstructor
public class ChatMessage {
    private String id;  // 메시지 보낸 자
    private String message;
    private String icon;
    private LocalDateTime time;
}
