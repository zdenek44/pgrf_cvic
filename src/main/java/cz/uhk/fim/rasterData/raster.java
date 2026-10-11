package cz.uhk.fim.rasterData;

import org.w3c.dom.css.RGBColor;

import java.awt.*;
import java.util.Optional;

/**
 * represents two-dimensional grid containing addresable pixels
 * each pixel is represented as a 32-bit integers
 */
public interface raster {
    /**
     * returns numbler of columns or rows
     */
    int getWidth();

    int getHeight();

    /**
     * sets color on specific position
     *
     * @param c     is for columns
     * @param r     is for rows
     * @param color which color to set
     */
    void setColor(int c, int r, int color);

    /**
     * returns optional color for a pixel
     *
     * @param c for columns
     * @param r for rows
     * @return integer of a color where if not returned address is invalid
     */
    Optional<Integer> getColor(int c, int r);

    /**
     * checks for a valid coordinated
     *
     * @param c for columns
     * @param r for rows
     * @return true or false
     */
    boolean checkValidNumber(int c, int r);


}
