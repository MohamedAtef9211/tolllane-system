package com.egabi.tcs.controller;

import org.springframework.http.ResponseEntity;

public class TCSController {
    public ResponseEntity responseOk(Object response) {
        return ResponseEntity.ok(response);
    }
}
