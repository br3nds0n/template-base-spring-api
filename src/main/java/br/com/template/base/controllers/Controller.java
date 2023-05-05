package br.com.template.base.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class Controller {

    @GetMapping
    public String app() {
        return "Template Base Running...";
    }
}
