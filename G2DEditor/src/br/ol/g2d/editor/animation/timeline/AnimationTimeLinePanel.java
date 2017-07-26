package br.ol.g2d.editor.animation.timeline;

import br.ol.g2d.Animation;
import br.ol.g2d.AnimationSpriteObject;
import br.ol.g2d.G2DContext;
import br.ol.ui.view.BasePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

/**
 * AnimationTimeLinePanel class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class AnimationTimeLinePanel extends BasePanel {
    
    private G2DContext context;
    private final int frameRectangleSize = 8;
    
    private Timer timer;
    private final Time time = new Time();
    
    private class Time {

        long delta = 0;
        int fps = 0;

        int fpsCount;
        long fpsTime;
        long lastTime = System.currentTimeMillis();

        public void update() {
            long currentTime = System.currentTimeMillis();
            delta = currentTime - lastTime;
            fpsTime += delta;
            if (fpsTime > 1000) {
                fps = fpsCount;
                fpsTime = fpsCount = 0;
            }
            else {
                fpsCount++;
            }
            lastTime = currentTime;
        }

    }
    private class MainThread extends TimerTask {
        @Override
        public void run() {
            time.update();
            updateAnimation();
            repaint();
        }

    }
    
    private synchronized void updateAnimation() {
        if (context.getSelectedAnimation() != null) {
            context.getSelectedAnimation().update(time.delta);
            if (context.getSelectedAnimation().stopped) {
                stopSelectedAnimation();
            }
            context.update();
        }
    }
    
    public AnimationTimeLinePanel() {
        setDesiredSizeInPixels(10, 10);
    }

    @Override
    public void init() {
        super.init();
    }
    
    public void playSelectedAnimation() {
        Animation selectedAnimation = context.getSelectedAnimation();
        if (selectedAnimation == null) {
            return;
        }
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new MainThread(), 0, 5);
        selectedAnimation.play();
        time.update();
    }
    
    public void pauseSelectedAnimation() {
        Animation selectedAnimation = context.getSelectedAnimation();
        if (selectedAnimation == null) {
            return;
        }
        selectedAnimation.pause();
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }
    
    public void stopSelectedAnimation() {
        Animation selectedAnimation = context.getSelectedAnimation();
        if (selectedAnimation == null) {
            return;
        }
        selectedAnimation.stop();
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }
    
    public G2DContext getContext() {
        return context;
    }

    public void setContext(G2DContext context) {
        this.context = context;
    }

    public int getFrameRectangleSize() {
        return frameRectangleSize;
    }

    @Override
    public void installActions() {
        addAction(new PlayPauseStopAnimationAction(this, context));
        addAction(new SelectAnimationFrameAction(this, context));
        addAction(new ChangeLastFrameIndexAction(this, context));
        addAction(new SetSpriteAtCurrentFrameAction(this, context));
        addAction(new RemoveSpriteAtCurrentFrameAction(this, context));
        addAction(new InsertKeyframeAction(this, context));
        addAction(new RemoveKeyframeAction(this, context));
        addAction(new AddRemoveFrameAction(this, context));
        addAction(new NewAnimationSpriteObjectAction(this, context));
        addAction(new AddAnimationToAnotherAnimationObjectAction(this, context));
        addAction(new RemoveAnimationObjectAction(this, context));
        addAction(new BringAnimationObjectToFrontOrBackAction(this, context));
        addAction(new CopyPasteOneFrameAction(this, context));
    }
    
    @Override
    public void installTools() {
    }

    public void update() {
        setEnabled(context != null && context.getSelectedAnimation() != null);
        if (isEnabled()) {
            Animation selectedAnimation = context.getSelectedAnimation();
            int dWidth = (selectedAnimation.lastFrameIndex + 1) * frameRectangleSize; 
            int dHeight = selectedAnimation.objects.size() * frameRectangleSize;
            setDesiredSizeInPixels(dWidth, dHeight);
            
            //if (!context.getSelectedAnimation().playing) {
            //    context.getSelectedAnimation().play();
            //}
        }
        repaint();
    }

    @Override
    public boolean canDraw() {
        return context != null && context.getAnimations()!= null;
    }
    
    @Override
    public void drawScaled(Graphics2D g) {
        Animation selectedAnimation = context.getSelectedAnimation();
        if (selectedAnimation == null) {
            return;
        }
        
        int rows = selectedAnimation.objects.size();
        int cols = selectedAnimation.lastFrameIndex;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col <= cols; col++) {
                g.setColor(Color.BLACK);
                if (col == selectedAnimation.currentFrameIndex) {
                    g.fillRect(col * frameRectangleSize, row * frameRectangleSize, frameRectangleSize, frameRectangleSize);
                }
                g.drawRect(col * frameRectangleSize, row * frameRectangleSize, frameRectangleSize, frameRectangleSize);

                // se a animacao for do tipo sprite object
                if (selectedAnimation.objects.get(row) instanceof AnimationSpriteObject) {
                    AnimationSpriteObject animationSpriteObject = (AnimationSpriteObject) selectedAnimation.objects.get(row);
                    // sprites
                    if (animationSpriteObject.drawableKeyframes.keySet().contains(col)) {
                        g.setColor(Color.BLUE);
                        g.fillOval(col * frameRectangleSize, row * frameRectangleSize, frameRectangleSize, frameRectangleSize / 2);
                    }

                    // properties
                    if (animationSpriteObject.keyframes.keySet().contains(col)) {
                        g.setColor(Color.RED);
                        g.fillOval(col * frameRectangleSize, row * frameRectangleSize + frameRectangleSize / 2, frameRectangleSize, frameRectangleSize / 2);
                    }
                }
                // se for animation object
                else {
                    g.setColor(Color.GRAY);
                    g.fillOval(col * frameRectangleSize, row * frameRectangleSize + frameRectangleSize / 2, frameRectangleSize, frameRectangleSize / 2);
                }
        
            }
            
            // selection
            if (selectedAnimation.objects.get(row) == selectedAnimation.getSelectedObject()) { 
                g.setXORMode(Color.GREEN);
                g.fillRect(0, row * frameRectangleSize, frameRectangleSize * cols, frameRectangleSize);
                g.setPaintMode();
            }
        }
    }
    
}
