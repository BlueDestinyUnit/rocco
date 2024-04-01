package com.jsh.rocco.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping("/")
    String getMain(){
        return "main";
    }

    @GetMapping("search")
    String getSearch(){return "/search";}
}
