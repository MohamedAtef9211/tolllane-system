package com.egabi.tls.controller;

import org.springframework.http.ResponseEntity;

public class TCSController {
    public ResponseEntity responseOk(Object response) {
        return ResponseEntity.ok(response);
    }
}
