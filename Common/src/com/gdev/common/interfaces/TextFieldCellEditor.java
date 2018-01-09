/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common.interfaces;

//import com.as.common.utils.Utility;
//import com.as.interfaces.UiFiles.AdcNumericPad;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.math.BigDecimal;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;
import org.openide.util.Exceptions;
import com.gdev.common.other.SerialNumberClass;
    
/**
 *
 * @author Maninderjit Singh
 */
public class TextFieldCellEditor extends AbstractCellEditor implements TableCellEditor{
    private JTable table;
    private JTextField textField;
    private Object value=null;
    private int column;
    private int row;
    
    public TextFieldCellEditor(){
        this.textField = new JTextField();
         textField.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (table != null) {
                    table.setValueAt(getCellEditorValue(), row, column);
                    table.selectAll();
                    table.clearSelection();
                 }
            }
        });
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        
        this.table= table;
        this.value = value;
        this.column = column;
        this.row = row;
        
        if(value != null){
            textField.setText(value.toString());
        }else{
            textField.setText("");
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
//           if(Utility.isTouchInterface()){
//               if(!Utility.showVirtualKeyboard()) {
//                AdcNumericPad dialog=new AdcNumericPad(textField);
//                dialog.setLocation(getRightCenterLocation(dialog.getSize()));
//                dialog.setVisible(true);
//                   
//               }
//            }
           textField.setHorizontalAlignment(JTextField.RIGHT);
                textField.selectAll();
            }
        });
        return textField;
    }

    @Override
    public Object getCellEditorValue() {
        Class columnClass = table.getColumnClass(column);
        String str = textField.getText();
        try {
            if(columnClass == String.class){
               return str;
               
            }else if(columnClass == BigDecimal.class){
                BigDecimal s = null;
                    try {
                            s = new BigDecimal(str == null ? "0" :str.trim());
                    } catch (Exception e) {
                            s = (BigDecimal) value;
                    }
               return s;
            }else if(columnClass == Integer.class){
               return Integer.parseInt(str); 
            }else if(columnClass == Long.class){
               return Long.parseLong(str); 
            }else if(columnClass == SerialNumberClass.class){
                return SerialNumberClass.getInteger(str);
            }else{
                return value;
            }
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
            return value;
        }
    }

     public static Point getRightCenterLocation(Dimension frameSize) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        int x = (screenSize.width - frameSize.width);
        int y = (screenSize.height - frameSize.height) / 2;
        Point location = new Point(x, y);
        return location;
    }
     
   public JTextField getTextField(){
       return textField;
   }
}