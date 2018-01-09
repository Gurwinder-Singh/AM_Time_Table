package com.gdev.common.other;

import java.awt.Color;
import java.awt.Component;
import java.sql.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


import java.text.SimpleDateFormat;

public class DateCellRenderer extends DefaultTableCellRenderer {

    SimpleDateFormat sdf = null;

    public DateCellRenderer() {
        this.sdf = Utility.getSdf();
    }

    public DateCellRenderer(SimpleDateFormat SDF) {
        this.sdf = SDF;

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
         if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(row % 2 == 0 ? Utility.getDefault().getRowBgColor() : Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
        if (value != null && value instanceof Date) {
            this.setText(sdf.format((Date) value));
        }
        return c;
    }
}
