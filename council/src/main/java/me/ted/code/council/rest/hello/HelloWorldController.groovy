import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {
    @RequestMapping("/")
    String sayHello() {
        return "Hello World!"
    }
}
