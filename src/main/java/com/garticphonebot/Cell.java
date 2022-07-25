package com.garticphonebot;

import java.awt.*;

public class Cell {
    private int x, y;
    private Color color;

    public Cell(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public javafx.scene.paint.Color getFXColor() {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int a = color.getAlpha();
        double opacity = a / 255.0 ;
        return javafx.scene.paint.Color.rgb(r, g, b, opacity);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", color=" + color +
                '}';
    }
}
