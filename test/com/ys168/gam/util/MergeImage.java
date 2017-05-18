package com.ys168.gam.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class MergeImage {

    private static String BASE = "D:/BackUp/Desktop/sword/direction/";
    private static String MERGED = BASE + "merged/";
    private static BufferedImage BACKGROUND;
    private static List<BufferedImage> DIRECTIONS;

    private static void loadBaseImage() {
        BACKGROUND = loadImageLocal(BASE + "background.png");
        DIRECTIONS = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            String path = BASE + i + ".png";
            DIRECTIONS.add(loadImageLocal(path));
        }
    }

    public static BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        loadBaseImage();
        for (int i = 0; i <= 0xFF; i++) {
            merge(i);
        }
        System.out.println(Math.log(256) / Math.log(2));
    }

    private static void merge(int i) {
        BufferedImage image = new BufferedImage(BACKGROUND.getWidth(), BACKGROUND.getHeight(), BACKGROUND.getType());
        Graphics g = image.createGraphics();
        g.drawImage(BACKGROUND, 0, 0, null);

        String newName = "";
        for (int k = 7; k >= 0; k--) {
            int v = i & (int) Math.pow(2, k);
            newName += v > 0 ? 1 : 0;

            if (v > 0) {
                BufferedImage dir = DIRECTIONS.get(k);
                g.drawImage(dir, 0, 0, null);
            }
        }
System.out.println(newName);
         String destName = MERGED + newName + ".png";
         writeImageLocal(destName, image);
    }

    /**
     * 生成新图片到本地
     */
    public static void writeImageLocal(String newImage, BufferedImage img) {
        if (newImage != null && img != null) {
            try {
                File outputfile = new File(newImage);
                ImageIO.write(img, "png", outputfile);
            }
            catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
