package com.egabi.tls.controller;

import com.egabi.tls.model.Vehicle;
import com.egabi.tls.service.ITSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ITSController extends TCSController{

    private final ITSService itsService;

    @PostMapping("/its/queue/")
    public ResponseEntity addVehicleToQueue(@RequestBody Vehicle vehicle){
        return responseOk(itsService.addVehicleToQueue(vehicle));
    }
}
