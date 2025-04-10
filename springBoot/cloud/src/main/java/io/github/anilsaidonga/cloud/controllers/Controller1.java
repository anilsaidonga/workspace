package io.github.anilsaidonga.cloud.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Controller1 {

    @GetMapping("/hello")
    public String handleHello()
    {
        return "Hello, World!, from Spring Boot";
    }

    @GetMapping("/bye")
    public String handleBye()
    {
        return "see ya!";
    }
}
