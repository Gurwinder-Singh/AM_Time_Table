/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.helper;

import com.gdev.timetable.interfaces.AutoCompleteListner;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * JComboBox with an auto complete drop down menu. Also accept Generic type
 *
 * @author Gurwinder Singh
 */
public class AutoCompleteComboBox<T> extends JComboBox {

    private List data;
    private JTextField input;
    private JPopupMenu popupMenu = new JPopupMenu();
    private JMenuItem ClearItem = null;
    private boolean mandatory = false;
    private AutoCompleteListner listner;

    public AutoCompleteComboBox() {
        super();
        setEditable(true);
        init();
    }

    public AutoCompleteComboBox(AutoCompleteListner listner) {
        super();
        init();
        setData(listner);
    }

    public void setMandatory(boolean mand) {
        mandatory = mand;
    }

    public boolean isMandatory() {
        return listner != null ? listner.isMandatory() : mandatory;
    }

    public void setImplement(AutoCompleteListner implement) {
        listner = implement;
    }
     
//    @Override
//    public void addActionListener(ActionListener l){
//        getEditor().addActionListener(l);
//    }
    
    private void init() {
        setEditable(true);
        //if you need custom model uncomment the all custom model code
//        setModel(new CustomModel());
        setRenderer(createListRenderer());
        if (getEditor().getEditorComponent() instanceof JTextField) {
            input = (JTextField) getEditor().getEditorComponent();
            input.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    validateInput();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    validateInput();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                }

            });

            input.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent arg0) {
                    if (input.getText().length() > 0) {
                        setPopupVisible(true);
                    }
                }

                @Override
                public void focusLost(FocusEvent arg0) {
                    if (!isValidate() && isMandatory()) {
                        setPopupVisible(true);
                        input.requestFocus();
                        input.select(0, 0);
                        input.setCaretPosition(input.getDocument().getLength());
                    }
                }
            });
            generatePopup();

        } else {
            throw new NullPointerException("TextField in comboBox not found!");
        }
    }

    private void validateInput() {
        filter();
        if (isValidate()) {
            input.setForeground(Color.BLACK);
        } else {
            input.setForeground(Color.RED);
        }

    }

    public JTextField getInputField(){
        return input;
    }
    private void generatePopup() {
        ClearItem = new JMenuItem("Clear");
        ClearItem.addActionListener(e -> {
            input.setText("");
            filter();
        });
        popupMenu.add(ClearItem);

        input.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }

            private void showPopup(MouseEvent e) {

                if (e.isPopupTrigger() || e.getButton() == JButton.RIGHT) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }

            }
        });

    }

    private boolean isValidate() {
        if (input.getText().length() > 0) {
            return listner.contains(input.getText(), data);
        }
        return true;
    }

    /**
     * use setData(AutoCompleteListner listner)
     *
     * @param v Vector of data
     * @deprecated
     */
    @Deprecated
    public void setData(Vector v) {
        this.data = v;
        data.forEach(x -> {
            addItem(x);
        });
        setSelectedItem(null);
    }

    public void setData(AutoCompleteListner listner) {

        this.listner = listner;
        this.data = new Vector(listner.getData());
        removeAllItems();
        data.forEach(x -> {
            addItem(x);
        });
        setSelectedItem(null);
        setEditable(true);
    }

    
    public void filter() {
        SwingUtilities.invokeLater(() -> {
            java.util.List<T> v = new ArrayList();
            String value = input.getText();
            if (value != null && !value.isEmpty()) {
                data.stream().filter((java.lang.Object x1) -> (listner != null ? listner.search(value, x1) : (String.valueOf(x1).toLowerCase().matches(value.toLowerCase() + "(.*)")))).forEach((java.lang.Object x2) -> {
                    v.add((T) x2);
                });
            } else {
                v.addAll(data);
            }
            setEditable(false);
            removeAllItems();
            if (listner == null) {
                //not test case (List type T but check for constains String )
                if (!v.contains(input.getText().toLowerCase())) {
                    addItem(input.getText());
                }
            } else {
                if (!listner.contains(input.getText(), data)) {
                    addItem(listner.addDefault(input.getText()));
                }
            }
            v.stream().forEach((i) -> {
                addItem(i);
            });
            setEditable(true);
            setPopupVisible(true);
            input.requestFocus();
            input.select(0, 0);
            input.setCaretPosition(input.getDocument().getLength());
        });
    }

    private ListCellRenderer createListRenderer() {
        return new DefaultListCellRenderer() {
            private Color background = new Color(0, 100, 255, 15);
            private Color defaultBackground = (Color) UIManager.get("List.background");

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (listner != null && value != null) {
                    if (value instanceof String) {
                        this.setText(String.valueOf(value));
                    } else {
                        this.setText(listner.show(value));
                    }
                }
                if (!isSelected) {
                    this.setBackground(index % 2 == 0 ? background : defaultBackground);
                }
                return this;
            }
        };
    }
    
    //no need
//    class CustomModel extends AbstractListModel implements MutableComboBoxModel, Serializable {
//
//        Vector objects;
//        Object selectedObject;
//
//        public CustomModel() {
//            objects = new Vector();
//        }
//
//        public CustomModel(Vector data) {
//            objects = data;
//        }
//
//        public void updateData(Vector data) {
//            objects = data;
//        }
//
//        public void filter() {
//            Vector v = new Vector();
//            String value = input.getText();
//            if (value != null && !value.isEmpty()) {
//                data.stream().filter(x -> String.valueOf(x).matches("^" + value)).forEach(x -> {
//                    v.add(x);
//                });
//            } else {
//                v.addAll(data);
//            }
////            setEditable(false);
//            removeAllItems();
//
//            for (Object i : v) {
//                addItem(i);
//            }
////            setEditable(true);
//
//        }
//
//        @Override
//        public int getSize() {
//            return objects.size();
//        }
//
//        @Override
//        public Object getElementAt(int index) {
//            return objects.get(index);
//        }
//
//        @Override
//        public void addElement(Object item) {
//            objects.add(item);
//        }
//
//        @Override
//        public void removeElement(Object obj) {
//            objects.remove(obj);
//        }
//
//        @Override
//        public void insertElementAt(Object item, int index) {
//            objects.add(index, item);
//        }
//
//        @Override
//        public void removeElementAt(int index) {
//            objects.remove(index);
//        }
//
//        @Override
//        public void setSelectedItem(Object anItem) {
//            if ((selectedObject != null && !selectedObject.equals(anItem))
//                    || selectedObject == null && anItem != null) {
//                selectedObject = anItem;
//            }
//        }
//
//        @Override
//        public Object getSelectedItem() {
//            return selectedObject;
//        }
//
//        public int getIndex(Object value) {
//            return objects.indexOf(value);
//        }
//    }
}
