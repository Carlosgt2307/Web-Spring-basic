package com.example.tarea;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HolaMundoController {

    @GetMapping("/hola")
    public String holaMundo() {
        return "hola";
    }
}