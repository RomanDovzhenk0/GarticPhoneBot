package com.garticphonebot;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

import static com.garticphonebot.ImageUtil.*;

public class DrawUtil {

    private static Color prevColor = Color.decode("#ffffff");
    private static boolean isMousePressed = false;

    public static void draw(BufferedImage bufferedImage, int quality) {
        double SCALE = 0;
        if((890 / (double)bufferedImage.getWidth()) > (500 / (double)bufferedImage.getHeight())) {
            SCALE = 500 / (double) bufferedImage.getHeight();
        } else {
            SCALE = 890 / (double) bufferedImage.getWidth();
        }

        BufferedImage pixelateImage = new BufferedImage((int) (SCALE * bufferedImage.getWidth(null)),
                (int) (SCALE * bufferedImage.getHeight(null)), BufferedImage.TYPE_INT_ARGB);

        Graphics2D grph = (Graphics2D) pixelateImage.getGraphics();
        grph.scale(SCALE, SCALE);
        grph.drawImage(bufferedImage, 0, 0, null);
        grph.dispose();


        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        setPenSize(robot, quality);

        int zeroX = MainController.points.get(0).getX();
        int zeroY = MainController.points.get(0).getY();
        robot.mouseMove(zeroX, zeroY);

        for (int y = 0; y < pixelateImage.getHeight(); y += quality) {
            prevColor = Color.decode("#ffffff");
            for (int x = 0; x < pixelateImage.getWidth(); x += quality) {

                BufferedImage croppedImage = getCroppedImage(pixelateImage, x, y, quality, quality);
                Color dominantColor = similarTo(getDominantColor(croppedImage));

                if(dominantColor.equals(Color.decode("#ffffff"))) {
                    if(isMousePressed) {
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                        isMousePressed = false;
                    }
                    prevColor = dominantColor;
                    continue;
                } else {
                    if(prevColor.equals(dominantColor)) {
                        robot.mouseMove(zeroX + x, zeroY + y);
                    } else {
                        if(isMousePressed) {
                            robot.mouseRelease(InputEvent.BUTTON1_MASK);
                            isMousePressed = false;
                        }
                        setColor(robot, dominantColor);
                        try {
                            Thread.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        robot.mouseMove(zeroX + x, zeroY + y);
                        if(!isMousePressed) {
                            robot.mousePress(InputEvent.BUTTON1_MASK);
                            isMousePressed = true;
                        }
                    }
                }
                prevColor = dominantColor;
            }
        }
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    private static void setColor(Robot robot, Color color) {

        if(color.equals(colors[0])) {
            robot.mouseMove(MainController.cells.get(0).getX(), MainController.cells.get(0).getY());
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (color.equals(colors[1])) {
            robot.mouseMove(MainController.cells.get(1).getX(), MainController.cells.get(1).getY());
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (color.equals(colors[2])) {
            robot.mouseMove(MainController.cells.get(2).getX(), MainController.cells.get(2).getY());
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (color.equals(colors[3])) {
            robot.mouseMove(MainController.cells.get(3).getX(), MainController.cells.get(3).getY());
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (color.equals(colors[4])) {
            robot.mouseMove(MainController.cells.get(4).getX(), MainController.cells.get(4).getY());
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (color.equals(colors[5])) {
            robot.mouseMove(MainController.cells.get(5).getX(), MainController.cells.get(5).getY());
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (color.equals(colors[6])) {
            robot.mouseMove(MainController.cells.get(6).getX(), MainController.cells.get(6).getY());
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (color.equals(colors[7])) {
            robot.mouseMove(MainController.cells.get(7).getX(), MainController.cells.get(7).getY());
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (color.equals(colors[8])) {
            robot.mouseMove(MainController.cells.get(8).getX(), MainController.cells.get(8).getY());
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (color.equals(colors[9])) {
            robot.mouseMove(MainController.cells.get(9).getX(), MainController.cells.get(9).getY());
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (color.equals(colors[10])) {
            robot.mouseMove(MainController.cells.get(10).getX(), MainController.cells.get(10).getY());
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (color.equals(colors[11])) {
            robot.mouseMove(MainController.cells.get(11).getX(), MainController.cells.get(11).getY());
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (color.equals(colors[12])) {
            robot.mouseMove(MainController.cells.get(12).getX(), MainController.cells.get(12).getY());
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (color.equals(colors[13])) {
            robot.mouseMove(MainController.cells.get(13).getX(), MainController.cells.get(13).getY());
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (color.equals(colors[14])) {
            robot.mouseMove(MainController.cells.get(14).getX(), MainController.cells.get(14).getY());
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (color.equals(colors[15])) {
            robot.mouseMove(MainController.cells.get(15).getX(), MainController.cells.get(15).getY());
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (color.equals(colors[16])) {
            robot.mouseMove(MainController.cells.get(16).getX(), MainController.cells.get(16).getY());
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else if (color.equals(colors[17])) {
            robot.mouseMove(MainController.cells.get(17).getX(), MainController.cells.get(17).getY());
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        }
    }

    private static void setPenSize(Robot robot, int quality) {
        switch (quality) {
            case 2 -> {
                robot.mouseMove(MainController.sizepoints.get(0).getX(), MainController.sizepoints.get(0).getY());
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
            }
            case 4 -> {
                robot.mouseMove(MainController.sizepoints.get(1).getX(), MainController.sizepoints.get(1).getY());
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
            }
            case 6 -> {
                robot.mouseMove(MainController.sizepoints.get(2).getX(), MainController.sizepoints.get(2).getY());
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
            }
            case 8 -> {
                robot.mouseMove(MainController.sizepoints.get(3).getX(), MainController.sizepoints.get(3).getY());
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
            }
            case 10 -> {
                robot.mouseMove(MainController.sizepoints.get(4).getX(), MainController.sizepoints.get(4).getY());
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
            }
        }
    }

}
