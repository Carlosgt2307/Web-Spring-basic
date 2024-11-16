package com.example.tarea;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import java.util.Map;

@Controller
public class HolaMundoController {

    @GetMapping("/hola")
    public String holaMundo() {
        return "hola";
    }

    @GetMapping("/clima")
    public String mostrarClima() {
        return "clima";
    }

     @GetMapping("/data/clima")
    @ResponseBody
    public List<Map<String, Object>> obtenerClima() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/static/data/clima.json");
        return mapper.readValue(file, List.class);
    }
}