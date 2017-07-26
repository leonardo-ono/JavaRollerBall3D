package br.ol.ui.view;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.JTextComponent;

/**
 * PropertiesEditor class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class PropertiesEditor extends JTable {
    
    private final static Map<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();
    
    static {
        map.put(boolean.class, Boolean.class);
        map.put(byte.class, Byte.class);
        map.put(short.class, Short.class);
        map.put(char.class, Character.class);
        map.put(int.class, Integer.class);
        map.put(long.class, Long.class);
        map.put(float.class, Float.class);
        map.put(double.class, Double.class);
    }

    protected PropertiesModel model;
    private Class editingClass;

    public PropertiesEditor() {
    }

    public class PropertiesModel extends AbstractTableModel {
        
        public Object object;

        public PropertiesModel(Object object) {
            this.object = object;
        }
        
        @Override
        public int getRowCount() {
            if (object == null) {
                return 0;
            }
            return object.getClass().getDeclaredFields().length;
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (object == null) {
                return null;
            }
            Field field = object.getClass().getDeclaredFields()[rowIndex];
            if (columnIndex == 0) {
                return field.getName();
            }
            field.setAccessible(true);
            Object returnObject = null;
            try {
                returnObject = field.get(object);
            } catch (Exception ex) {
            }
            return returnObject;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (object == null) {
                return;
            }
            Field field = object.getClass().getDeclaredFields()[rowIndex];
            field.setAccessible(true);
            try {
                field.set(object, aValue);
                propertyValueChanged();
            } catch (Exception ex) {
            }
        }
        
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (object == null) {
                return false;
            }
            return columnIndex == 1;
        }

        @Override
        public String getColumnName(int column) {
            if (column == 0) {
                return "Name";
            }
            else if (column == 1) {
                return "Value";
            }
            return "";
        }
        
    }

    public Object getObject() {
        return model.object;
    }

    public void setObject(Object object) {
        setModel(model = new PropertiesModel(object));
    }
    
    private Class getRowClass(int row) {
        if (model == null || model.object == null) {
            return String.class;
        }
        Field field = model.object.getClass().getDeclaredFields()[row];
        if (field.getType().isPrimitive()) {
            return map.get(field.getType());
        }
        return field.getType();
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        TableCellRenderer cellRenderer = null;
        editingClass = null;
        int modelColumn = convertColumnIndexToModel(column);
        if (modelColumn == 1) {
            Class rowClass = getRowClass(row);
            cellRenderer = getDefaultRenderer(rowClass);
        } else {
            cellRenderer = super.getCellRenderer(row, column);
        }
        if (cellRenderer == null) {
            cellRenderer = getDefaultRenderer(String.class);
        }
        return cellRenderer;
    }

    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        editingClass = null;
        int modelColumn = convertColumnIndexToModel(column);
        if (modelColumn == 1) {
            editingClass = getRowClass(row);
            return getDefaultEditor(editingClass);
        } else {
            return super.getCellEditor(row, column);
        }
    }
    
    @Override
    public Class getColumnClass(int column) {
        return editingClass != null ? editingClass : super.getColumnClass(column);
    }    

    @Override
    public boolean editCellAt(int row, int column, EventObject e) {
        boolean result = super.editCellAt(row, column, e);
        final Component editor = getEditorComponent();
        if (editor == null || !(editor instanceof JTextComponent)) {
            return result;
        }
        if (e instanceof KeyEvent) {
            ((JTextComponent) editor).selectAll();
        }
        return result;
    }
    
    public void propertyValueChanged() {
    }    
    
}
