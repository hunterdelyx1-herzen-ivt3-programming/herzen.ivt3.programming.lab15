package ru.spb.herzen.ivt3.second;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import java.util.concurrent.CompletableFuture;


public class Second
{
    public static void main( String[] args )
    {
        CompletableFuture completableFuture = CompletableFuture.supplyAsync(
                () -> {
                    BufferedImage image;

                    try {
                        image = ImageIO.read(new File("./img/jugement.png"));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        return null;
                    }

                    return image;
                }
        ).thenApply(
                (image) -> {
                    if (image == null) return null;

                    Image tmp = image.getScaledInstance(100, -1, BufferedImage.SCALE_SMOOTH);
                    BufferedImage new_image = new BufferedImage(tmp.getWidth(null), tmp.getHeight(null), BufferedImage.TYPE_INT_RGB);
                    new_image.getGraphics().drawImage(tmp, 0, 0, null);

                    for (int y = 0; y < new_image.getHeight(); y++) {
                        for (int x = 0; x < new_image.getWidth(); x++) {
                            Color color = new Color(new_image.getRGB(x, y));
                            new_image.setRGB(x,y, color.brighter().getRGB());
                        }
                    }
                    return new_image;
                }
        ).thenApply(
                (resultImage) -> {
                    if (resultImage == null) return false;

                    try {
                        ImageIO.write(resultImage, "png", new File("./img/jugement_modified.png"));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        return false;
                    }

                    return true;
                }
        );

        try {
            System.out.println(completableFuture.get());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
