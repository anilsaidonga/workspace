package io.github.anilsaidonga.cicd;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloControllerTest {

    @Test
    void testSayHello() {
        HelloController controller = new HelloController();
        String result = controller.sayHello();
        assertEquals("Hello, world!", result);
    }
}
