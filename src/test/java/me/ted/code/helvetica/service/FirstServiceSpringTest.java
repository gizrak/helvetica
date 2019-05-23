package me.ted.code.helvetica.service;

import me.ted.code.helvetica.HelveticaApplication;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelveticaApplication.class)
public class FirstServiceSpringTest {

    @Autowired
    private FirstService firstService;

    @Test
    public void test() {
        int sum = firstService.add(1, 2);
        assertThat(sum, equalTo(3));
    }
}
