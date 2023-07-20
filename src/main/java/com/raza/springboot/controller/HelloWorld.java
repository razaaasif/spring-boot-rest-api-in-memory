package com.raza.springboot.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    @GetMapping("/hello-world")
    public String helloWord(){
        return "Hello World";
    }
}
