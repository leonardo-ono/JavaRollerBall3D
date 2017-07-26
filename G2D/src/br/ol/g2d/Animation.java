package br.ol.g2d;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Animation class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Animation implements Serializable, AnimationObjectInterface {
    
    private transient G2DContext context;
    private Animation animation;
    public String name;
    public int screenWidth;
    public int screenHeight;
    public AnimationObjectInterface selectedObject;
    public List<AnimationObjectInterface> objects = new ArrayList<AnimationObjectInterface>();
    public long frameRateTime = 1000 / 30;
    public long accumulatedTime;
    public int currentFrameIndex = 0;
    public int lastFrameIndex = 0;
    public boolean playing;
    public boolean stopped;
    public boolean loop;
    public Path2D border = new Path2D.Double();
    public int realStartFrameIndex;
    private int zOrder;
    private boolean zOrderChanged;
    
    public Animation(G2DContext context, String name, int screenWidth, int screenHeight) {
        this.context = context;
        this.name = name;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void set(Animation animation) {
        context = animation.context;
        this.animation = animation.animation;
        name = animation.name;
        screenWidth = animation.screenWidth;
        screenHeight = animation.screenHeight;
        selectedObject = animation.selectedObject;
        objects = animation.objects;
        frameRateTime = animation.frameRateTime;
        lastFrameIndex = animation.lastFrameIndex;
        currentFrameIndex = 0;
        playing = false;
        stopped = true;
        loop = animation.loop;
        realStartFrameIndex = animation.realStartFrameIndex;
        zOrder = animation.zOrder;
        zOrderChanged = animation.zOrderChanged;
    }
    
    public G2DContext getContext() {
        return context;
    }

    public Animation getAnimation() {
        return animation;
    }

    @Override
    public void setAnimation(Animation animation) {
        this.animation = animation;
    }
    
    @Override
    public void updateContext(G2DContext context) {
        this.context = context;
        for (AnimationObjectInterface object : objects) {
            object.updateContext(context);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public AnimationObjectInterface getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(AnimationObjectInterface selectedObject) {
        this.selectedObject = selectedObject;
    }

    public void setScreenSize(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }
    
    public void addObject(AnimationObjectInterface ao) {
        ao.setAnimation(this);
        objects.add(ao);
    }

    public void removeObject(AnimationObjectInterface ao) {
        objects.remove(ao);
    }
        
    public void play() {
        playing = true;
        accumulatedTime = 0;
        if (stopped) {
            currentFrameIndex = 0;
            stopped = false;
        }
    }

    public void pause() {
        playing = false;
    }
    
    public void stop() {
        playing = false;
        stopped = true;
    }
    
    public void update(long dt) {
        if (!playing) {
            return;
        }
        if (accumulatedTime > frameRateTime) {
            currentFrameIndex += (int) (accumulatedTime / frameRateTime);
            accumulatedTime = (accumulatedTime % frameRateTime);
            if (currentFrameIndex > lastFrameIndex) {
                currentFrameIndex = lastFrameIndex;
                playing = false;
                if (loop) {
                    currentFrameIndex = 0;
                    play();
                }
                else {
                    stopped = true;
                }
            }
        }
        accumulatedTime += dt;
    }
    
    //private void reorderObjectsIfNecessary() {
    //    if (zOrderChanged) {
    //        Collections.sort(objects);
    //        zOrderChanged = false;
    //    }
    //}
    
    public void draw(Graphics2D g) {
        //reorderObjectsIfNecessary();
        for (AnimationObjectInterface object : objects) {
            object.draw(currentFrameIndex, g);
        }
    }

    @Override
    public void draw(int frameIndex, Graphics2D g) {
        if (frameIndex < realStartFrameIndex) {
            return;
        }
        frameIndex = frameIndex - realStartFrameIndex;
        if (frameIndex > lastFrameIndex && !loop) {
            frameIndex = lastFrameIndex;
        }
        else if (frameIndex > lastFrameIndex) {
            frameIndex = frameIndex % (lastFrameIndex + 1);
        }
        //reorderObjectsIfNecessary();
        for (AnimationObjectInterface object : objects) {
            object.draw(frameIndex, g);
        }
    }

    @Override
    public void drawBorder(int currentFrameIndex, Graphics2D ig, Color xorColor) {
    }

    @Override
    public boolean updateBorder(int currentFrameIndex) {
        return true;
    }
    
    public void drawBorders(Graphics2D ig) {
        //reorderObjectsIfNecessary();
        for (AnimationObjectInterface object : objects) {
            Color xorColor = Color.BLUE;
            if (object == selectedObject) {
                xorColor = Color.RED;
            }
            object.drawBorder(currentFrameIndex, ig, xorColor);
        }
    }

    public void previous() {
        currentFrameIndex--;
        currentFrameIndex = currentFrameIndex < 0 ? 0 : currentFrameIndex;
    }

    public void next() {
        currentFrameIndex++;
        currentFrameIndex = currentFrameIndex > lastFrameIndex ? lastFrameIndex : currentFrameIndex;
    }

    public AnimationObjectInterface getAnimationObjectAtPosition(int px, int py) {
        for (AnimationObjectInterface object : objects) {
            object.updateBorder(currentFrameIndex);
            if (object.getBorder().contains(px, py)) {
                return object;
            }
        }
        return null;
    }

    @Override
    public Path2D getBorder() {
        return border;
    }

    @Override
    public int getRealStartFrameIndex() {
        return realStartFrameIndex;
    }

    @Override
    public int getZOrder() {
        return zOrder;
    }

    @Override
    public int compareTo(AnimationObjectInterface o) {
        return getZOrder() - o.getZOrder();
    }

    @Override
    public void setZOrder(int zOrder) {
        if (this.zOrder != zOrder) {
            this.zOrder = zOrder;
            if (animation != null) {
                animation.zOrderChanged();
            }
        }
    }

    public void zOrderChanged() {
        zOrderChanged = true;
    }

}
