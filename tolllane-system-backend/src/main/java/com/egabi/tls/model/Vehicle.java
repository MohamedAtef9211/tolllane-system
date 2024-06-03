package com.egabi.tls.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    private Axes axes;
    private List<Integer> weights;
    private int totalWeight;
    private String hashcode;
    private String vehicleType;
    private String plateNo;
    private String aNBRImage;
}
