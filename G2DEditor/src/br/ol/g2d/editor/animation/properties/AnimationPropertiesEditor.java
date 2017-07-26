package br.ol.g2d.editor.animation.properties;

import br.ol.g2d.G2DContext;
import br.ol.ui.view.PropertiesEditor;

/**
 * AnimationPropertiesEditor class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class AnimationPropertiesEditor extends PropertiesEditor {
    
    private G2DContext context;

    public AnimationPropertiesEditor() {
        setObject(null);
    }

    public G2DContext getContext() {
        return context;
    }

    public void setContext(G2DContext context) {
        this.context = context;
    }

    public void init() {
    }

    public void update() {
        setEnabled(context != null && context.getSelectedAnimation() != null);
        if (model == null || model.object != context.getSelectedAnimation()) {
            setObject(context.getSelectedAnimation());
        }
        repaint();
    }    

    @Override
    public void propertyValueChanged() {
        context.update();
    }
    
}
