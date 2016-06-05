package ru.spb.herzen.ivt3.second;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Second
{
    private static ExecutorService service = Executors.newCachedThreadPool();

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
                    image = new BufferedImage(tmp.getWidth(null), tmp.getHeight(null), BufferedImage.TYPE_INT_RGB);
                    image.getGraphics().drawImage(tmp, 0, 0, null);

                    return image;
                }
        ).thenApply(
                (resizedImage) -> {
                    if (resizedImage == null) return null;
                    for (int y = 0; y < resizedImage.getHeight(); y++) {
                        for (int x = 0; x < resizedImage.getWidth(); x++) {
                            Color color = new Color(resizedImage.getRGB(x, y));
                            resizedImage.setRGB(x,y, color.brighter().getRGB());
                        }
                    }
                    return resizedImage;
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
