package com.garticphonebot;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImageUtil {

    public static Color[] colors = {Color.decode("#000000"), Color.decode("#666666"), Color.decode("#0050cd"),
            Color.decode("#ffffff"), Color.decode("#aaaaaa"), Color.decode("#26c9ff"), Color.decode("#017420"),
            Color.decode("#990000"), Color.decode("#964112"), Color.decode("#11b03c"), Color.decode("#ff0013"),
            Color.decode("#ff7829"), Color.decode("#b0701c"), Color.decode("#99004e"), Color.decode("#cb5a57"),
            Color.decode("#ffc126"), Color.decode("#ff008f"), Color.decode("#feafa8")};

//    public static List<Color> colors = new List();


    public static Image pixelate(File file, int pixelSize){
        BufferedImage imageToPixelate = null;
        try {
            imageToPixelate = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage pixelateImage = new BufferedImage(
                imageToPixelate.getWidth(),
                imageToPixelate.getHeight(),
                imageToPixelate.getType());

        for (int y = 0; y < imageToPixelate.getHeight(); y += pixelSize) {
            for (int x = 0; x < imageToPixelate.getWidth(); x += pixelSize) {
                BufferedImage croppedImage = getCroppedImage(imageToPixelate, x, y, pixelSize, pixelSize);
                Color dominantColor = similarTo(getDominantColor(croppedImage));
                for (int yd = y; (yd < y + pixelSize) && (yd < pixelateImage.getHeight()); yd++) {
                    for (int xd = x; (xd < x + pixelSize) && (xd < pixelateImage.getWidth()); xd++) {
                        pixelateImage.setRGB(xd, yd, dominantColor.getRGB());
                    }
                }
            }
        }
        return convertToFxImage(pixelateImage);
    }

    public static Color similarTo(Color c){

        double minDistance = Double.POSITIVE_INFINITY;
        Color similarColor = null;
        for(Color color : colors) {
            double distance = (c.getRed() - color.getRed())*(c.getRed() - color.getRed()) +
                    (c.getGreen() - color.getGreen())*(c.getGreen() - color.getGreen()) +
                    (c.getBlue() - color.getBlue())*(c.getBlue() - color.getBlue());
            if(distance < minDistance) {
                minDistance = distance;
                similarColor = color;
            }
        }

        return similarColor;
    }

    public static BufferedImage getCroppedImage(BufferedImage image, int startx, int starty, int width, int height) {
        if (startx < 0) startx = 0;
        if (starty < 0) starty = 0;
        if (startx > image.getWidth()) startx = image.getWidth();
        if (starty > image.getHeight()) starty = image.getHeight();
        if (startx + width > image.getWidth()) width = image.getWidth() - startx;
        if (starty + height > image.getHeight()) height = image.getHeight() - starty;
        return image.getSubimage(startx, starty, width, height);
    }

    public static Color getDominantColor(BufferedImage image) {
        int sumR = 0, sumB = 0, sumG = 0, sum2 = 0;
        int color = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                color = image.getRGB(x, y);
                Color c = new Color(color);
                sumR += c.getRed();
                sumB += c.getBlue();
                sumG += c.getGreen();
                sum2++;
            }
        }
        return new Color(sumR/sum2, sumG/sum2, sumB/sum2);
//        Map<Integer, Integer> colorCounter = new HashMap<>(100);
//        for (int x = 0; x < image.getWidth(); x++) {
//            for (int y = 0; y < image.getHeight(); y++) {
//                int currentRGB = image.getRGB(x, y);
//                int count = colorCounter.getOrDefault(currentRGB, 0);
//                colorCounter.put(currentRGB, count + 1);
//            }
//        }
//        return getDominantColor(colorCounter);
    }

    public static Color getDominantColor(Map<Integer, Integer> colorCounter) {
        int dominantRGB = colorCounter.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();
        return new Color(dominantRGB);
    }

    private static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }
}
