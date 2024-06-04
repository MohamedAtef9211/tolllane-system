package com.egabi.tls.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum VehicleTypeIcons {
    PRIVATE("/ui/images/vehicletypes/private.png"),
    BUS("/ui/images/vehicletypes/bus.png"),
    MICRO_BUS("/ui/images/vehicletypes/half-loader.png");

    private final String imagePath;

    private static final Map<String, String> imagePathsMap;

    static {
        imagePathsMap = new HashMap<>();
        for (VehicleTypeIcons type : VehicleTypeIcons.values()) {
            imagePathsMap.put(type.name(), type.imagePath);
        }
    }

    VehicleTypeIcons(String imagePath) {
        this.imagePath = imagePath;
    }

    public static String getImagePath(String vehicleTypeName) {
        return imagePathsMap.get(vehicleTypeName.toUpperCase());
    }
}
