package com.danielvargas.InventarioWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class juegosController {

    @RequestMapping("/juegos/burbujas")
    public String burbujas(){
        return "juegos/burbujas";
    }
}
