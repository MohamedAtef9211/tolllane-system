package com.egabi.tcs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleType {
    private int id;
    private String code;
    private String desc;
    private float fees;
    private boolean isFeesCalculated;
}
