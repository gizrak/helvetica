package me.ted.code.helvetica.controller.hello;

import me.ted.code.helvetica.controller.hello.dto.HelloResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public ResponseEntity<HelloResponse> helloworld() {
        return ResponseEntity.ok(HelloResponse.of("world"));
    }
}
