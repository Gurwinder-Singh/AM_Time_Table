package com.gdev.common.interfaces;

//import com.as.common.utils.Utility;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import java.math.BigDecimal;
import com.gdev.common.other.Utility;

public class AmountCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value != null && value instanceof BigDecimal) {
            if (isSelected) {
                c.setBackground(table.getSelectionBackground());
                c.setForeground(Color.WHITE);
            } else {
                c.setBackground(row % 2 == 0 ? Utility.getDefault().getRowBgColor() : Color.WHITE);
                c.setForeground(Color.BLACK);
            }
            this.setText(value.toString());
//             this.setText(AdcNumberFormatter.getDefault().format((BigDecimal) value,Utility.getCompanyInfo().getCurrSymbol(),
//                     Utility.getmSettings().getDisplayFigureInReport()));
            this.setHorizontalAlignment(RIGHT);
        }
        return c;
    }
}
