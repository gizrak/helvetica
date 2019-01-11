package me.ted.code.helvetica.websocket;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class WebSocketController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/hello")
    @SendToUser("/topic/roomId")
    public String processMessageFromClient(@Payload String message) throws Exception {
        final String content = new Gson().fromJson(message, Map.class)
                                         .get("content")
                                         .toString();
//        messagingTemplate.convertAndSendToUser("ted", "/queue/reply", name);
        return content;
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
}
