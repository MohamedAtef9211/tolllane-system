package com.egabi.tls.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaneData {
    private String laneType;
    private String laneRoad;
    private String gate;
    private String laneNumber;
}
