package com.netcracker.crm.controller.base.entity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author Karpunets
 * @since 21.04.2017
 */

@RequestMapping(value = "/ROLE_ADMIN")
@Controller
public class AdminController {

    @GetMapping("/")
    public String main(Map<String, Object> model) {
        return "home";
    }

    @GetMapping("/createUser")
    public String createUser(Map<String, Object> model) {
        return "createUser";
    }

    @GetMapping("/profile")
    public String profile(Map<String, Object> model) {
        return "profile";
    }

    @GetMapping("/dashboard")
    public String dashboard(Map<String, Object> model) {
        return "dashboard";
    }
}