package br.ol.ui.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.TransferHandler;

/**
 * DragAndDropBasePanel class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class DragAndDropBasePanel extends BasePanel implements DragGestureListener {

    private Rectangle r = new Rectangle(0, 0, 50, 50);
    
    public DragAndDropBasePanel() {
        super();
        
        DragSource dragSource = new DragSource();
        dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_REFERENCE, this);
        
        setTransferHandler(new TransferHandler(null));
        
        setDropTarget(new DropTarget(this, new DropTargetListener() {

            @Override
            public void dragEnter(DropTargetDragEvent dtde) {
                //System.out.println(dtde);
            }

            @Override
            public void dragOver(DropTargetDragEvent dtde) {
                //System.out.println(dtde);
                r.setLocation(dtde.getLocation().x, dtde.getLocation().y);
                repaint();
            }

            @Override
            public void dropActionChanged(DropTargetDragEvent dtde) {
                //System.out.println(dtde);
            }

            @Override
            public void dragExit(DropTargetEvent dte) {
                //System.out.println(dte);
            }

            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    System.out.println("drop " + dtde.getTransferable().getTransferData(DataFlavor.stringFlavor));
                } catch (Exception ex) {
                    Logger.getLogger(DragAndDropBasePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }));
    }

    @Override
    public void drawScaled(Graphics2D g) {
        super.drawScaled(g);
        
        g.setColor(Color.GREEN);
        g.fill(r);
    }
    
    @Override
    public void dragGestureRecognized(DragGestureEvent dge) {
        
        if ((dge.getSourceAsDragGestureRecognizer().getTriggerEvent().getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == 0) {
            return;
        }
        
        if (!r.contains(dge.getDragOrigin())) {
            return;
        }
        
        dge.startDrag(DragSource.DefaultMoveDrop, new Transferable() {

            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[] { DataFlavor.stringFlavor };
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return flavor.equals(DataFlavor.stringFlavor);
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                return "KKK";
            }
        });
    }
    
}
