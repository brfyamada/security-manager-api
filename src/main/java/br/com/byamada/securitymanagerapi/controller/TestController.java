package br.com.byamada.securitymanagerapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<String> getTest() {
        //[SPRING SLEUTH] [Step 3] When generete logs the trace Id will be generated
        log.info("Received a call for:  /test");
        return ResponseEntity.ok("Hello World - You are a simple user");
    }

    @GetMapping("/protected/test")
    public ResponseEntity<String> getTestProtected() {
        //[SPRING SLEUTH] [Step 3] When generete logs the trace Id will be generated
        log.info("Received a call for:  /protected/test");
        return ResponseEntity.ok("Hello World - You are a protected user");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/test")

    public ResponseEntity<String> getTestAdmin() {
        //[SPRING SLEUTH] [Step 3] When generete logs the trace Id will be generated
        log.info("Received a call for:  /admin/test");
        return ResponseEntity.ok("Hello World - You are an admin user");
    }

}
