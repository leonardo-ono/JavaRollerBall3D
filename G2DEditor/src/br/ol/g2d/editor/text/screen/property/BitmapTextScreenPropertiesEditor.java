package br.ol.g2d.editor.text.screen.property;

import br.ol.g2d.G2DContext;
import br.ol.ui.view.PropertiesEditor;

/**
 * AnimationPropertiesEditor class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class BitmapTextScreenPropertiesEditor extends PropertiesEditor {
    
    private G2DContext context;

    public BitmapTextScreenPropertiesEditor() {
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
        setEnabled(context != null && context.getSelectedTextScreen() != null);
        if (model == null || model.object != context.getSelectedTextScreen()) {
            setObject(context.getSelectedTextScreen());
        }
        repaint();
    }    

    @Override
    public void propertyValueChanged() {
        context.update();
    }
    
}
