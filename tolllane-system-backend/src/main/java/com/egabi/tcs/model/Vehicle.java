package com.egabi.tcs.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("ANBR_Image")
    private String aNBRImage;
}
