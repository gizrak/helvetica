package me.ted.code.council.rest.hello;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import me.ted.code.council.rest.hello.dto.HelloResponse;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public ResponseEntity<HelloResponse> helloworld() {
        return ResponseEntity.ok(HelloResponse.of("world"));
    }
}
