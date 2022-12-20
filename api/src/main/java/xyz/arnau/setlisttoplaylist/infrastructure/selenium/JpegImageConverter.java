package xyz.arnau.setlisttoplaylist.infrastructure.selenium;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.createImageOutputStream;
import static javax.imageio.ImageIO.getImageWritersByFormatName;

public class JpegImageConverter {
    public static byte[] convertImageToJpg(File inputFile) throws IOException {
        try (var outputStream = new ByteArrayOutputStream()) {
            var writer = getImageWritersByFormatName("jpg").next();
            writer.setOutput(createImageOutputStream(outputStream));
            writer.write(null,
                    new IIOImage(removeAlphaChannel(ImageIO.read(inputFile)), null, null),
                    writer.getDefaultWriteParam());
            writer.dispose();
            return outputStream.toByteArray();
        }
    }

    private static BufferedImage removeAlphaChannel(BufferedImage img) {
        if (!img.getColorModel().hasAlpha()) {
            return img;
        }

        BufferedImage target = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = target.createGraphics();
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        g.drawImage(img, 0, 0, null);
        g.dispose();

        return target;
    }
}