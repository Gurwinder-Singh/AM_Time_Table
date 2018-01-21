/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.timetable.helper;

import com.gdev.timetable.utility.Utility;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Admin
 */
public class SerialNumberClassCellRenderer extends DefaultTableCellRenderer {

    public SerialNumberClassCellRenderer() {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (isSelected) {
            c.setBackground(table.getSelectionBackground());
            c.setForeground(Color.white);
        } else {

            c.setBackground(Utility.getColor("FFFFCC"));
            //c.getBackground()
            c.setForeground(c.getBackground().getGreen() < 103 ? Color.white : Color.black);
        }
        this.setHorizontalAlignment(SwingUtilities.RIGHT);

        return this;
    }

}
