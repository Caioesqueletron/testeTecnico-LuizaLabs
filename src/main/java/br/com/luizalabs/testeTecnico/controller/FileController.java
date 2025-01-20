package br.com.luizalabs.testeTecnico.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}
