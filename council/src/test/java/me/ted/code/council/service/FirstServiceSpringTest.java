package me.ted.code.council.service;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import me.ted.code.council.CouncilApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CouncilApplication.class)
public class FirstServiceSpringTest {

    @Autowired
    private FirstService firstService;

    @Test
    public void test() {
        int sum = firstService.add(1, 2);
        assertThat(sum, equalTo(3));
    }
}
