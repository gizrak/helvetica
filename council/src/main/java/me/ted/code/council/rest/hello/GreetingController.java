package me.ted.code.council.rest.hello;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class GreetingController {

    @GetMapping("/greet")
    Greet greet() {
        return new Greet("Hello World!");
    }

    @GetMapping("/hateoas")
    @ResponseBody
    public HttpEntity<Greet> hateoas(@RequestParam(value = "name", required = false, defaultValue = "HATEOAS") String name) {
        Greet greet = new Greet("Hello " + name);
        greet.add(linkTo(
                methodOn(GreetingController.class)
                        .hateoas(name))
                .withSelfRel());
        return new ResponseEntity<Greet>(greet, HttpStatus.OK);
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Greet extends ResourceSupport {
    private String message;
}
