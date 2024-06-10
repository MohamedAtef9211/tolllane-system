package com.egabi.tcs.utils;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class ScreenUtils {

    private final double centeredHeight = getLayoutY();
    private final double centeredWidth = getLayoutX();
    private final static Rectangle2D SCREEN_VISUAL_BOUNDS = Screen.getPrimary().getVisualBounds();
    public static final double SCREEN_HEIGHT = SCREEN_VISUAL_BOUNDS.getHeight();
    public static final double MAX_SCREEN_HEIGHT = SCREEN_VISUAL_BOUNDS.getMaxX();
    public static final double SCREEN_WIDTH = SCREEN_VISUAL_BOUNDS.getWidth();
    public static final double MAX_SCREEN_WIDTH = SCREEN_VISUAL_BOUNDS.getMaxY();
    public static final double MIN_SCREEN_HEIGHT = SCREEN_VISUAL_BOUNDS.getMinX();
    public static final double MIN_SCREEN_WIDTH = SCREEN_VISUAL_BOUNDS.getMinY();

    public ScreenUtils() {
    }

    public double getCenteredHeight() {
        return centeredHeight;
    }

    public double getCenteredWidth() {
        return centeredWidth;
    }

    private static double getLayoutX(){
        double initialX = 0.4;

        return initialX * ScreenUtils.SCREEN_WIDTH;
    }

    private static double getLayoutY(){
        double initialX = 0.3;

        return initialX * ScreenUtils.SCREEN_HEIGHT;
    }
}
