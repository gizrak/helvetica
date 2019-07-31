package me.ted.code.council.rest;

import org.codehaus.jackson.map.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GreetingControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGreet() throws IOException {
        String body = restTemplate.getForObject("/greet", String.class);
        System.out.println("body: " +body);
        assertThat(new ObjectMapper().readTree(body)
                .get("message")
                .getTextValue(), Matchers.is("Hello World!"));
    }
}
