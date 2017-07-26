package br.ol.ui.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.AbstractAction;
import javax.swing.JScrollPane;

/**
 * ScrollPane class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class ScrollPane extends JScrollPane {
    
    private DragAndDropBasePanel basePanel;
    
    public ScrollPane() {
        disableScrollByArrowKeys();
    }
    
    private class DisableKeysAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }
    
    private void disableScrollByArrowKeys() {
        DisableKeysAction disableKeysAction = new DisableKeysAction();
        getActionMap().put("unitScrollLeft", disableKeysAction);
        getActionMap().put("unitScrollRight", disableKeysAction);
        getActionMap().put("unitScrollUp", disableKeysAction);
        getActionMap().put("unitScrollDown", disableKeysAction);
    }
    
    public void init() {
        basePanel = new DragAndDropBasePanel();
        basePanel.installActions();
        setViewportView(basePanel);
    }
    
}
