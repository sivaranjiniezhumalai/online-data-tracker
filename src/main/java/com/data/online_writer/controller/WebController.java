package com.data.online_writer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        // Now Spring Boot will treat this as a real browser redirect
        return "redirect:/dashboard.html"; 
    }
}