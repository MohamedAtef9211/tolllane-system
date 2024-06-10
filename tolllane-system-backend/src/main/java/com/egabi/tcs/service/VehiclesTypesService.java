package com.egabi.tcs.service;

import com.egabi.tcs.model.VehicleType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiclesTypesService {

    public List<VehicleType> findAllVehicles(){
        return List.of(new VehicleType(1,"PRIVATE","private",10,false),
                new VehicleType(2,"MICRO_BUS","Micro bus",15,false),
                new VehicleType(3,"BUS","BUS",20,true)
        );
    }
}
