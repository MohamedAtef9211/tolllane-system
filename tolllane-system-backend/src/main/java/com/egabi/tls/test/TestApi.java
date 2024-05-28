package com.egabi.tls.test;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {

    @GetMapping("/hi")
    public String hello(){
        return "Hiiiiiiiiiiii";
    }
}
