package br.ol.g2d;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Palette class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Palette implements Serializable {

    private transient G2DContext context;
    private List<PaletteColor> colors = new ArrayList<PaletteColor>();
    private PaletteColor selectedColor;
    private final PaletteColor notInPaletteColor = new PaletteColor(Color.WHITE, 0);
    
    public Palette() {
        addDefaultColors();
    }

    public Palette(List<PaletteColor> colors) {
        this.colors = colors;
    }

    private void addDefaultColors() {
        colors.add(new PaletteColor(Color.WHITE, 0));
        colors.add(new PaletteColor(Color.LIGHT_GRAY, 1));
        colors.add(new PaletteColor(Color.GRAY, 2));
        colors.add(new PaletteColor(Color.DARK_GRAY, 3));
        colors.add(selectedColor = new PaletteColor(Color.BLACK, 4));
        colors.add(new PaletteColor(Color.RED, 5));
        colors.add(new PaletteColor(Color.GREEN, 6));
        colors.add(new PaletteColor(Color.BLUE, 7));
        colors.add(new PaletteColor(Color.YELLOW, 8));
        colors.add(new PaletteColor(Color.CYAN, 9));
        colors.add(new PaletteColor(Color.MAGENTA, 10));
        colors.add(new PaletteColor(Color.ORANGE, 11));
        colors.add(new PaletteColor(Color.PINK, 12));
    }
    
    public G2DContext getContext() {
        return context;
    }
    
    public void updateContext(G2DContext context) {
        this.context = context;
    }

    public List<PaletteColor> getColors() {
        return colors;
    }
    
    public PaletteColor getColorByPosition(int x, int y) {
        for (PaletteColor color : colors) {
            if (color.getRectangle().contains(x, y)) {
                return color;
            }
        }
        return null;
    }

    public PaletteColor getColorByRGB(int rgb) {
        for (PaletteColor color : colors) {
            if (color.getColor().getRGB() == rgb) {
                return color;
            }
        }
        return null;
    }
    
    public PaletteColor getSelectedColor() {
        return selectedColor;
    }

    public PaletteColor getNotInPaletteColor() {
        return notInPaletteColor;
    }
    
    public void addColor(PaletteColor color) {
        colors.add(color);
    }

    public void removeColor(PaletteColor color) {
        colors.remove(color);
    }
    
    public void setSelectedColor(PaletteColor selectedColor) {
        this.selectedColor = selectedColor;
    }

    public void selectNotInPaletteColor(Color color) {
        notInPaletteColor.setColor(color);
        this.selectedColor = notInPaletteColor;
    }

    @Override
    public String toString() {
        return "Palette{" + "colors=" + colors 
                + ", selectedColor=" + selectedColor + '}';
    }

    
}
