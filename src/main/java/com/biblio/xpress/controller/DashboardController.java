package com.biblio.xpress.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/member-dashboard")
    public String memberDashboard() {
        return "member-dashboard";
    }

    @GetMapping("/librarian-dashboard")
    public String librarianDashboard() {
        return "librarian-dashboard";
    }

    @GetMapping("/admin-dashboard")
    public String adminDashboard() {
        return "admin-dashboard";
    }
}