/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common.other;

import java.awt.Color;
import java.awt.Component;
import java.util.Hashtable;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Maninderjit Singh
 */
public class SerialNumberClassCellRenderer extends DefaultTableCellRenderer {

    private Hashtable<Integer, Element> elements;

    public SerialNumberClassCellRenderer() {
    }

    public void setBackColor(int row, Color color) {
        setBackColor(row, color, "");
    }

    public void setBackColor(int row, Color color, String tooltip) {
        if (elements == null) {
            elements = new Hashtable();
        }
        Element ele = new Element();
        ele.setBackColor(color);
        ele.setToolTipText(tooltip);
        this.elements.put(row, ele);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Element element = null;
        if (isSelected) {
            c.setBackground(table.getSelectionBackground());
            c.setForeground(Color.white);
        } else {
            element = this.elements.get(table.convertRowIndexToModel(row));
            if (element != null) {
//                c.setBackground(element != null ? element.getBackColor() : c.getBackground());
                c.setBackground(row % 2 == 0 ? Utility.getDefault().getRowBgColor() : Color.WHITE);
            }
            c.setForeground(c.getBackground().getGreen() < 103 ? Color.white : Color.black);
        }
        this.setHorizontalAlignment(SwingUtilities.RIGHT);
        this.setToolTipText(element != null ? element.getToolTipText() : "");
        return this;
    }

    public String getTooltipTextOfRow(int position) {
        return elements.get(position).getToolTipText();
    }

    class Element {

        private Color backColor;
        private String toolTipText;

        /**
         * @return the backColor
         */
        public Color getBackColor() {
            return backColor;
        }

        /**
         * @param backColor the backColor to set
         */
        public void setBackColor(Color backColor) {
            this.backColor = backColor;
        }

        /**
         * @return the toolTipText
         */
        public String getToolTipText() {
            return toolTipText;
        }

        /**
         * @param toolTipText the toolTipText to set
         */
        public void setToolTipText(String toolTipText) {
            this.toolTipText = toolTipText;
        }
    }
}
