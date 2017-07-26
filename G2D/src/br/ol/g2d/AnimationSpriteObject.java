package br.ol.g2d;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.io.Serializable;
import java.util.TreeMap;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

/**
 * AnimationSpriteObject class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class AnimationSpriteObject implements Serializable, AnimationObjectInterface {
    
    private transient G2DContext context;
    private Animation animation;
    public String name;
    public TreeMap<Integer, Sprite> drawableKeyframes = new TreeMap<Integer, Sprite>();
    public TreeMap<Integer, AnimationObjectFrame> keyframes = new TreeMap<Integer, AnimationObjectFrame>();
    public AnimationObjectFrame frame = new AnimationObjectFrame();
    public AffineTransform transform = new AffineTransform();
    public Path2D border = new Path2D.Double();
    
    public transient ScriptEngine se;
    public transient Invocable sei;
    public int scriptStartFrame;
    public int scriptEndFrame;
    public boolean useScriptPreview;
    public String previewScript = 
        "function previewStarted(realFrame) {\n} \n\n" +
        "function previewEnded(realFrame) {\n} \n\n" +
        "function update(frame) {\n" +
        "	// obj.x;\n" +
        "	// obj.y;\n" +
        "	// obj.px = 0;\n" +
        "	// obj.py;\n" +
        "	// obj.sx = 1;\n" +
        "	// obj.sy = 1;\n" +
        "	// obj.angle = 0;\n" +
        "	// obj.alpha = 1;\n" +
        "}    \n";
    
    private int zOrder;
    private static final Composite[] alphaCompositeCache = new Composite[256];

    static {
        createAlphaCompositeCache();
    }
    
    // limita a intensidade do alpha para 255
    private static void createAlphaCompositeCache() {
        for (int i=0; i<=255; i++) {
            float v = i / 255f;
            alphaCompositeCache[i] = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, v);
        }
    }
    
    public AnimationSpriteObject(String name, Animation animation) {
        this.name = name;
        this.animation = animation;
    }
    
    private void createScriptEngine() {
        ScriptEngineManager factory = new ScriptEngineManager();
        se = factory.getEngineByName("javascript");
        if (se != null) {
            sei = (Invocable) se;
        }
    }

    public G2DContext getContext() {
        return context;
    }
    
    @Override
    public void updateContext(G2DContext context) {
        this.context = context;
        for (AnimationObjectFrame keyframe : keyframes.values()) {
            keyframe.updateContext(context);
        }
    }

    public Animation getAnimation() {
        return animation;
    }

    @Override
    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void addKeyframe(int frameIndex, AnimationObjectFrame keyframe) {
        keyframes.put(frameIndex, keyframe);
    }

    public void removeKeyframe(int frameIndex) {
        keyframes.remove(frameIndex);
    }

    public void addDrawableKeyframe(int frameIndex, Sprite sprite) {
        drawableKeyframes.put(frameIndex, sprite);
    }

    public void removeDrawableKeyframe(int frameIndex) {
        drawableKeyframes.remove(frameIndex);
    }

    public Sprite getSprite(int index) {
        Integer floorIndex = drawableKeyframes.floorKey(index);
        Sprite floorDrawable = null;
        if (floorIndex != null) {
            floorDrawable = drawableKeyframes.get(floorIndex);
        }
        return floorDrawable;
    }

    public boolean isKeyFrame(int index) {
        AnimationObjectFrame keyFrame = keyframes.get(index);
        return keyFrame != null;
    }

    public AnimationObjectFrame getKeyFrame(int index) {
        Integer floorIndex = keyframes.floorKey(index);
        AnimationObjectFrame keyFrame = keyframes.get(floorIndex);
        return keyFrame;
    }

    public void setPreviewScript(String previewScript) {
        this.previewScript = previewScript;
        se.setContext(new SimpleScriptContext());
        se.put("obj", frame);
        try {
            se.eval(previewScript);
        } catch (ScriptException ex) {
        }
    }
    public AnimationObjectFrame getScriptFrame(int index) throws Exception {
        if (index == scriptStartFrame) {
            try {
                sei.invokeFunction("previewStarted", index);
            }
            catch (Exception e) {}
        }
        sei.invokeFunction("update", index - scriptStartFrame);
        if (index == scriptEndFrame) {
            try {
                sei.invokeFunction("previewEnded", index);
            }
            catch (Exception e) {}
        }
        return frame;
    }
    
    public AnimationObjectFrame getFrame(int index) {
        if (se == null && useScriptPreview) {
            createScriptEngine();
            setPreviewScript(previewScript);
        }
        if (se != null && useScriptPreview && index >= scriptStartFrame && index <= scriptEndFrame) {
            try {
                return getScriptFrame(index);
            } catch (Exception ex) {
            }
        }
        else {
            Integer floorIndex = keyframes.floorKey(index);
            AnimationObjectFrame floorFrame = keyframes.get(floorIndex);
            if (floorFrame.tween) {
                Integer ceilIndex = keyframes.ceilingKey(floorIndex + 1);
                if (ceilIndex != null) {
                    AnimationObjectFrame ceilFrame = keyframes.get(ceilIndex);
                    frame.interpolate(floorFrame, ceilFrame, floorIndex, ceilIndex, index);
                }
                else {
                    frame.set(floorFrame);
                }
            }
            else {
                frame.set(floorFrame);
            }
        }
        return frame;
    }
    
    @Override
    public void draw(int frameIndex, Graphics2D g) {
        Sprite sprite = getSprite(frameIndex);
        if (sprite != null) {
            getFrame(frameIndex);
            
            if (frame.alpha <= 0) {
                return;
            }
            
            AffineTransform at = g.getTransform();
            g.translate(frame.x, frame.y);
            g.rotate(frame.angle);
            g.scale(frame.sx, frame.sy);
            g.translate(-frame.px, -frame.py);
            
            if (frame.alpha == 1.0) {
                sprite.draw(g);
            }
            else if (frame.alpha > 0) {
                frame.alpha = frame.alpha > 1 ? 1 : frame.alpha;
                Composite oc = g.getComposite();
                g.setComposite(alphaCompositeCache[(int) (256 * frame.alpha)]);
                sprite.draw(g, 0, 0, frame.alpha);
                g.setComposite(oc);
            }
            
            g.setTransform(at);
        }
    }
    
    @Override
    public boolean updateBorder(int frameIndex)  {
        Sprite drawable = getSprite(frameIndex);
        if (drawable != null) {
            getFrame(frameIndex);
            transform.setToIdentity();
            transform.translate(frame.x, frame.y);
            transform.rotate(frame.angle);
            transform.scale(frame.sx, frame.sy);
            transform.translate(-frame.px, -frame.py);
            border.reset();
            int dpx = drawable.getPivotX();
            int dpy = drawable.getPivotY();
            int dw = drawable.getWidth();
            int dh = drawable.getHeight();
            border.moveTo(-dpx, -dpy);
            border.lineTo(-dpx + dw, -dpy);
            border.lineTo(-dpx + dw, -dpy + dh);
            border.lineTo(-dpx, -dpy + dh);
            border.closePath();
            border.transform(transform);
            return true;
        }
        return false;
    }
    
    private static final Stroke stroke = new BasicStroke(2);
    
    @Override
    public void drawBorder(int frameIndex, Graphics2D g, Color xorColor) {
        if (updateBorder(frameIndex)) {
            Stroke originalStroke = g.getStroke();
            g.setStroke(stroke);
            g.setXORMode(xorColor);
            g.draw(border);
            g.setPaintMode();
            g.setStroke(originalStroke);
        }
    }

    @Override
    public Path2D getBorder() {
        return border;
    }

    @Override
    public int getRealStartFrameIndex() {
        return 0;
    }

    @Override
    public int getZOrder() {
        return zOrder;
    }

    public void bakeScriptAnimation() throws Exception {
        setPreviewScript(previewScript);
        for (int frameIndex = scriptStartFrame; frameIndex <= scriptEndFrame; frameIndex++) {
            AnimationObjectFrame bakedFrame = new AnimationObjectFrame();
            bakedFrame.set(getScriptFrame(frameIndex));
            addKeyframe(frameIndex, bakedFrame);
        }
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

}
