package com.garticphonebot;

import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainController{

    @FXML
    private Rectangle color1, color10, color11, color12, color13, color14, color15, color16, color17, color18,
            color2, color3, color4, color5, color6, color7, color8, color9;
    @FXML
    private ImageView image, inputImage;
    @FXML
    private Button openImageButton, setColorsButton, setFrameButton, startButton, setSizeButtons;
    @FXML
    private Slider qualityPicker;


    private List<Rectangle> rectangles;
    public static List<Cell> cells = new ArrayList<>();
    private NativeMouseInputListener listener;
    private File file;
    public static List<Point> points = new ArrayList<>();
    public static List<Point> sizepoints = new ArrayList<>();


    public void initialize() {
        try { GlobalScreen.registerNativeHook();}
        catch (NativeHookException e) { e.printStackTrace();}

        openImageButton.setDisable(true);
        setFrameButton.setDisable(true);
        startButton.setDisable(true);
        qualityPicker.setDisable(true);
        setSizeButtons.setDisable(true);

        startButton.setOnAction(event -> {
            GlobalScreen.removeNativeMouseListener(listener);
            Main.stage.setIconified(true);
            try {
                DrawUtil.draw(ImageIO.read(file), (int) (2 * qualityPicker.getValue()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Main.stage.setIconified(false);
        });

        setColorsButton.setOnAction(event -> {
            GlobalScreen.removeNativeMouseListener(listener);
            cells.clear();
            openImageButton.setDisable(true);
            setFrameButton.setDisable(true);
            startButton.setDisable(true);
            qualityPicker.setDisable(true);
            setSizeButtons.setDisable(true);
            setColors();
        });

        openImageButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            file = fileChooser.showOpenDialog(openImageButton.getScene().getWindow());
            javafx.scene.image.Image newImage = new Image(file.toURI().toString());
            inputImage.setImage(newImage);
            double x = (200 - inputImage.getImage().getWidth() * 112 / inputImage.getImage().getHeight())/2;
            if(x > 0) {
                inputImage.setX(x);
            } else {
                inputImage.setX(0);
            }
            setSizeButtons.setDisable(false);
            drawImage();
        });

        setSizeButtons.setOnAction(event -> {
            startButton.setDisable(true);
            setFrameButton.setDisable(true);
            GlobalScreen.removeNativeMouseListener(listener);
            sizepoints.clear();
            class MouseListener implements NativeMouseInputListener {
                @Override
                public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
                    if(sizepoints.size() < 5) {
                        sizepoints.add(new Point(nativeMouseEvent.getX(), nativeMouseEvent.getY()));
                        if(sizepoints.size() == 5) {
                            setFrameButton.setDisable(false);
                        }
                    } else {
                        GlobalScreen.removeNativeMouseListener(this);
                    }
                }
            }
            listener = new MouseListener();
            GlobalScreen.addNativeMouseListener(listener);
        });

        setFrameButton.setOnAction(event -> {
            startButton.setDisable(true);
            GlobalScreen.removeNativeMouseListener(listener);
            points.clear();
            class MouseListener implements NativeMouseInputListener {
                @Override
                public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
                    if(points.size() < 1) {
                        points.add(new Point(nativeMouseEvent.getX(), nativeMouseEvent.getY()));
                        if(points.size() == 1) {
                            startButton.setDisable(false);
                        }
                    } else {
                        GlobalScreen.removeNativeMouseListener(this);
                    }
                }
            }
            listener = new MouseListener();
            GlobalScreen.addNativeMouseListener(listener);
        });

        qualityPicker.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            drawImage();
        });
    }

    public void drawImage() {
        if(file != null) {
            image.setImage(ImageUtil.pixelate(file, (int) (2 * qualityPicker.getValue())));
            double x = (890 - image.getImage().getWidth() * 500 / image.getImage().getHeight())/2;
            if(x > 0) {
                image.setX(x);
            } else {
                image.setX(0);
            }
        }
    }

    public void setColors() {
        class MouseListener implements NativeMouseInputListener {
            @Override
            public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
                if(cells.size() < 18) {
                    try {
                        Robot robot = new Robot();
                        Cell cell = new Cell(nativeMouseEvent.getX(), nativeMouseEvent.getY(),
                                robot.getPixelColor(nativeMouseEvent.getX(), nativeMouseEvent.getY()));
                        cells.add(cell);
                        ImageUtil.colors[cells.size() - 1] = cell.getColor();
                        fillRectangle(cell, cells.size());
                        if(cells.size() == 18) {
                            openImageButton.setDisable(false);
                            qualityPicker.setDisable(false);
                        }
                    } catch (AWTException e) {e.printStackTrace();}
                } else {
                    GlobalScreen.removeNativeMouseListener(this);
                }
            }
        }

        listener = new MouseListener();
        GlobalScreen.addNativeMouseListener(listener);
    }

    public void fillRectangle(Cell cell, int num) {
        switch (num){
            case 1 -> color1.setFill(cell.getFXColor());
            case 2 -> color2.setFill(cell.getFXColor());
            case 3 -> color3.setFill(cell.getFXColor());
            case 4 -> color4.setFill(cell.getFXColor());
            case 5 -> color5.setFill(cell.getFXColor());
            case 6 -> color6.setFill(cell.getFXColor());
            case 7 -> color7.setFill(cell.getFXColor());
            case 8 -> color8.setFill(cell.getFXColor());
            case 9 -> color9.setFill(cell.getFXColor());
            case 10 -> color10.setFill(cell.getFXColor());
            case 11 -> color11.setFill(cell.getFXColor());
            case 12 -> color12.setFill(cell.getFXColor());
            case 13 -> color13.setFill(cell.getFXColor());
            case 14 -> color14.setFill(cell.getFXColor());
            case 15 -> color15.setFill(cell.getFXColor());
            case 16 -> color16.setFill(cell.getFXColor());
            case 17 -> color17.setFill(cell.getFXColor());
            case 18 -> color18.setFill(cell.getFXColor());
        }
    }
}