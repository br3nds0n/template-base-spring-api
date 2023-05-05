package br.com.template.base.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/app")
public class Controller {

    @GetMapping
    @Operation(tags = "Auth", summary = "sumario")
    public String app() {
        return "Template Base Running...";
    }
}
