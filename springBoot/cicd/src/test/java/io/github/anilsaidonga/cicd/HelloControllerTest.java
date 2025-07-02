package io.github.anilsaidonga.cicd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class HelloControllerTest {

    @Test
    void testSayHello() {
        HelloController controller = new HelloController();
        String result = controller.sayHello();
        assertEquals("Hello, world!", result);
    }
}
