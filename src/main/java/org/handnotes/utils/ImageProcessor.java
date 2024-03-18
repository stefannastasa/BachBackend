package org.handnotes.utils;

import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.util.Iterator;

@Service
public class ImageProcessor {

    public File generateResizedImage(File file){
        try {
            BufferedImage original = ImageIO.read(file);

            int orgWidth = original.getWidth();
            int orgHeight = original.getHeight();
            float ratio = (float) orgHeight/orgWidth;
            BufferedImage resizedImage = new BufferedImage(160, (int) (160 * ratio), original.getType());
            Graphics2D g2d = resizedImage.createGraphics();

            g2d.drawImage(original, 0, 0, 160, (int) (160 * ratio), null);
            g2d.dispose();
            File output = new File("SMALL_"+file.getName());
            ImageIO.write(resizedImage, "jpg", output);
            return output;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public File compressImage(File file){
        try {
            BufferedImage original = ImageIO.read(file);
            File compressedImageFile = new File("compressed");
            try(OutputStream os = new FileOutputStream(compressedImageFile)){
                Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
                ImageWriter writer = (ImageWriter) writers.next();

                ImageOutputStream ios = ImageIO.createImageOutputStream(os);
                writer.setOutput(ios);

                ImageWriteParam param = writer.getDefaultWriteParam();

                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(0.5f);  // Change the quality value you prefer
                writer.write(null, new IIOImage(original, null, null), param);

                os.close();
                ios.close();
                writer.dispose();
            }

            return compressedImageFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
