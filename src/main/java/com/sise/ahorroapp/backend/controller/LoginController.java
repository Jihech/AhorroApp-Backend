package com.sise.ahorroapp.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // debe estar en templates/login.html
    }

}
