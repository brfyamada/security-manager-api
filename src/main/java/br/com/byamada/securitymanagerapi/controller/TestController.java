package br.com.byamada.securitymanagerapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<String> getTest() {
        return ResponseEntity.ok("Hello World - You are a simple user");
    }

    @GetMapping("/protected/test")
    public ResponseEntity<String> getTestProtected() {
        return ResponseEntity.ok("Hello World - You are a protected user");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/test")
    public ResponseEntity<String> getTestAdmin() {
        return ResponseEntity.ok("Hello World - You are an admin user");
    }

}
