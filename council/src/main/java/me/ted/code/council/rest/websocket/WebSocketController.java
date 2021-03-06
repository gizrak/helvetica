package me.ted.code.council.rest.websocket;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import me.ted.code.council.rest.websocket.dto.GreetingResponse;
import me.ted.code.council.rest.websocket.dto.HelloRequest;

@Controller
public class WebSocketController {

    @MessageMapping("/ws/hello")
    @SendTo("/topic/greetings")
    public GreetingResponse greeting(@Payload HelloRequest message) throws Exception {
        Thread.sleep(100);
        return GreetingResponse.builder()
                       .content("Hello, " + HtmlUtils.htmlEscape(message.getName()))
                       .build();
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
}
