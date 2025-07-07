package com.sise.ahorroapp.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/admin_dashboard")
    public String mostrarAdminDashboard() {
        return "admin_dashboard";
    }

    @GetMapping("/usuario_dashboard")
    public String mostrarUsuarioDashboard() {
        return "usuario_dashboard";
    }
}
