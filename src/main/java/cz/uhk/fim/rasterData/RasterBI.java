package cz.uhk.fim.rasterData;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class RasterBI implements raster{

    private final BufferedImage image;

    public RasterBI(int height,int width) {
        image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

    @Override
    public void setColor(int c, int r, int color) {
        if (checkValidNumber(c,r)) {
            image.setRGB(c,r,color);
        }
    }

    @Override
    public Optional<Integer> getColor(int c, int r) {
        if (checkValidNumber(c,r)){
            return Optional.of(image.getRGB(c,r));
        }
        return Optional.empty();
    }

    public boolean checkValidNumber(int c, int r){
        if (c>=0 && r >= 0 && c <= getWidth()-1 && r <= getHeight()-1){
            return true;
        }else return false;
    }

    public void present(final Graphics g) {
        if(g != null) {
            g.drawImage(image, 0, 0, null);
        }
    }

    public void clear(final int color) {
        Graphics g = image.getGraphics();
        if (g != null) {
            g.setColor(new Color(color));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}

