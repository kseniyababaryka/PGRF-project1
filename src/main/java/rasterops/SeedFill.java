package rasterops;

import rasterdata.RasterBufferedImage;

import java.util.Optional;

public class SeedFill {
    public void fill(RasterBufferedImage raster, int c, int r, int bgColor, int fillColor, int boundaryColor) {
        Optional<Integer> maybeColor = raster.getColor(c, r);
        if (maybeColor.isPresent()) {
            int color = maybeColor.get();

            if ((color & 0xffffff) == (fillColor & 0xffffff)) {
                return;
            }

            if ((color & 0xffffff) == (bgColor & 0xffffff)) {
                raster.setColor(c, r, fillColor);
                fill(raster, c + 1, r, bgColor, fillColor, boundaryColor);
                fill(raster, c - 1, r, bgColor, fillColor, boundaryColor);
                fill(raster, c, r + 1, bgColor, fillColor, boundaryColor);
                fill(raster, c, r - 1, bgColor, fillColor, boundaryColor);

            } else if ((color & 0xffffff) == (boundaryColor & 0xffffff)) {
                return;
            }
        }
    }
}
