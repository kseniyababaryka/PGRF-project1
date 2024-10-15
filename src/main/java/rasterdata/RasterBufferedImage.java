package rasterdata;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class RasterBufferedImage implements rasterdata.Raster{

    private final BufferedImage image;

    public RasterBufferedImage(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public int width() {
        return image.getWidth();
    }

    @Override
    public int height() {
        return image.getHeight();
    }

    @Override
    public void setColor(int c, int r, int color) {
        // 1. check boundaries
        if (c >= 0 && r >= 0 && c < image.getWidth() && r < image.getHeight()) {
            // 2. set pixel if address is valid
            image.setRGB(c, r, color);
        }
        // else do nothing
    }

    @Override
    public Optional<Integer> getColor(int c, int r) {
        // 1. check boundaries
        // if address is valid
        if (c >= 0 && r >= 0 && c < image.getWidth() && r < image.getHeight()) {
            // 2. set pixel if address is valid
            return Optional.of(image.getRGB(c, r));
        }
        //      return Optional.of()
        // else return Optional.empty()
        return Optional.empty();
    }

    public void clear(int color){
        Graphics gr = image.getGraphics();
        gr.setColor(new Color(color));
        gr.fillRect(0, 0, image.getWidth(), image.getHeight());
    }

    public void present(Graphics graphics) {
        if (graphics != null) {
            graphics.drawImage(image, 0, 0, null);
        }
    }
}
