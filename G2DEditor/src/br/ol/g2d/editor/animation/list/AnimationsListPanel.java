package br.ol.g2d.editor.animation.list;

import br.ol.g2d.Animation;
import br.ol.g2d.G2DContext;
import br.ol.ui.view.BasePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * AnimationsListPanel class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class AnimationsListPanel extends BasePanel {
    
    private G2DContext context;
    private final static Font fontNormal = new Font("Arial", Font.PLAIN, 12);
    private final static Font fontBold = new Font("Arial", Font.BOLD, 12);
    
    public AnimationsListPanel() {
        setDesiredSizeInPixels(10, 10);
    }
    
    public G2DContext getContext() {
        return context;
    }

    public void setContext(G2DContext context) {
        this.context = context;
    }

    @Override
    public void installActions() {
        addAction(new CreateNewAnimationAction(this, context));
        addAction(new RenameAnimationAction(this, context));
        addAction(new DeleteAnimationAction(this, context));
        addAction(new SelectAnimationAction(this, context));
    }
    
    @Override
    public void installTools() {
    }

    public void update() {
        setEnabled(context != null && context.getAnimations()!= null);
        if (isEnabled()) {
            int textHeight = getGraphics().getFontMetrics(fontNormal).getHeight();
            int dWidth = getParent().getWidth();
            int dHeight = textHeight * (context.getAnimations().getMap().size() + 1);
            setDesiredSizeInPixels(dWidth, dHeight);
        }
        repaint();
    }

    @Override
    public boolean canDraw() {
        return context != null && context.getAnimations()!= null;
    }
    
    @Override
    public void drawScaled(Graphics2D g) {
        Font originalFont = g.getFont();
        g.setFont(fontNormal);
        int textHeight = g.getFontMetrics().getHeight();
        int y = 1;
        for (Animation animation : context.getAnimations().getMap().values()) {
            if (animation == context.getSelectedAnimation()) {
                g.setFont(fontBold);
            }
            else {
                g.setFont(fontNormal);
            }
            g.drawString(animation.name, 10, y * textHeight);
            if (animation == context.getSelectedAnimation()) {
                g.setXORMode(Color.WHITE);
                g.fillRect(0, (y - 1) * textHeight + textHeight / 4, getWidth(), textHeight - textHeight / 8);
                g.setPaintMode();
            }
            y++;
        }
        g.setFont(originalFont);
    }
    
    public Animation getAnimationAtPosition(int px, int py) {
        int textHeight = getGraphics().getFontMetrics(fontNormal).getHeight();
        int selectedIndex = py / textHeight;
        int y = 0;
        for (Animation animation : context.getAnimations().getMap().values()) {
            if (y == selectedIndex) {
                return animation;
            }
            y++;
        }
        return null;
    }
    
}
