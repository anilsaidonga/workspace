package donga.sai.anil.sample;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class greet
{
    @GetMapping("/greet")
    public String greetUser(@RequestParam(value="name", defaultValue="World") String name)
    {
        return String.format("Hello, %s!", name);
    }
}