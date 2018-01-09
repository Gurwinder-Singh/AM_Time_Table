/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common.other;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author admin
 */
public class TableHeaderRenderer implements TableCellRenderer {

    private TableHeaderRendererPanel panel = new TableHeaderRendererPanel();
    private MacTable smTable1 = null;
    private boolean advance;

    public TableHeaderRenderer(TableColumn col, MacTable table) {
        this.smTable1 = table;
        this.advance = true;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value != null && advance) {
            if (panel.getTextField().getText().length() > 0 && hasFocus) {
//                panel.getTextField().setBackground(Utility.getColor("CCFFCC"));
            } else {
//                panel.getTextField().setBackground(new java.awt.Color(255, 255, 204));
            }
            panel.getLabel().setText(value.toString());
            String filterVal = "";
            filterVal = smTable1.get_Cols().get(value.toString());
            panel.getTextField().setText(filterVal);
            panel.getTextField().requestFocusInWindow();
            panel.getTextField().selectAll();
        }

        JComponent comp = (JComponent) table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(advance){
        comp = panel;
        }
        return comp;
    }

    public JTextField getTextField() {
        return panel.getTextField();
    }
    
    public void setAdvance(boolean adv){
        this.advance = adv;
    }
}