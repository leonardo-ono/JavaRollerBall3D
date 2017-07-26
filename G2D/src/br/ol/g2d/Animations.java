package br.ol.g2d;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Animations class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Animations implements Serializable {
    
    private transient G2DContext context;
    private final Map<String, Animation> animations = new HashMap<String, Animation>();
    private Animation selectedAnimation;
    private int defaultScreenWidth;
    private int defaultScreenHeight;
    
    public Animations(int defaultScreenWidth, int defaultScreenHeight) {
        this.defaultScreenWidth = defaultScreenWidth;
        this.defaultScreenHeight = defaultScreenHeight;
    }
    
    public G2DContext getContext() {
        return context;
    }
    
    public void updateContext(G2DContext context) {
        this.context = context;
        for (Animation animation : animations.values()) {
            animation.updateContext(context);
        }
    }

    public int getDefaultScreenWidth() {
        return defaultScreenWidth;
    }

    public void setDefaultScreenWidth(int defaultScreenWidth) {
        this.defaultScreenWidth = defaultScreenWidth;
    }

    public int getDefaultScreenHeight() {
        return defaultScreenHeight;
    }

    public void setDefaultScreenHeight(int defaultScreenHeight) {
        this.defaultScreenHeight = defaultScreenHeight;
    }
    
    public Map<String, Animation> getMap() {
        return animations;
    }
    
    public int getCount() {
        return animations.size();
    }
    
    public Animation get(String animationName) {
        return animations.get(animationName);
    }
    
    public Animation getCopy(String animationName) {
        Animation originalAnimation = get(animationName);
        if (originalAnimation != null) {
            Animation duplicatedAnimation = new Animation(context, animationName, 0, 0);
            duplicatedAnimation.set(originalAnimation);
            return duplicatedAnimation;
        }
        return null;
    }
    
    public void create(String animationName) {
        Animation animation = new Animation(context, animationName, defaultScreenWidth, defaultScreenHeight);
        
        animations.put(animationName, animation);
        context.update();
    }
    
    public void delete(String animationName) {
        animations.remove(animationName);
        context.update();
    }

    public Animation getSelectedAnimation() {
        return selectedAnimation;
    }

    public void setSelectedAnimation(Animation selectedAnimation) {
        this.selectedAnimation = selectedAnimation;
        context.update();
    }

    public boolean checkAnimatinoNameAlreadyExists(String animationName) {
        return animations.keySet().contains(animationName);
    }

}
