package me.ted.code.council.rest.websocket.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GreetingResponse {

    private String content;
}
