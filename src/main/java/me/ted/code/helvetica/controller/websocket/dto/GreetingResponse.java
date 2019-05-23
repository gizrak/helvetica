package me.ted.code.helvetica.controller.websocket.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GreetingResponse {

    private String content;
}
