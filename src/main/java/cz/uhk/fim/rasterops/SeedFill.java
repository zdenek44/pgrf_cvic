package cz.uhk.fim.rasterops;

import java.awt.image.Raster;

public interface SeedFill {
    public void fill(final Raster raster, final int c, final int r, final int BGcolor, final int fillColor);


}
