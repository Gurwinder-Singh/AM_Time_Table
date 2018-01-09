package com.gdev.common.other;



import com.gdev.common.interfaces.AmountCellRenderer;
import com.gdev.common.interfaces.AmountDrCrCellRenderer;
import com.gdev.common.interfaces.ITotalTableFooter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.math.BigDecimal;
import javax.swing.BorderFactory;

import javax.swing.CellRendererPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class TableFooter implements Border, ChangeListener, AdjustmentListener, TableColumnModelListener, ListSelectionListener {

    private static Font TOTAL_FONT = new Font("Arial", Font.BOLD, 12);
    private static final int INSET_PADDING_BOTTOM = 6;
//        private static final int BORDER_HEIGHT = 16; 
    private static Border compBorder = BorderFactory.createBevelBorder(1, Color.GRAY, Color.GRAY, Color.WHITE, Color.GRAY);
//     private static Border compBorder = BorderFactory.createBevelBorder(1, Color.ORANGE, Color.ORANGE, Color.WHITE, Color.ORANGE);
    JTable table;
    private CellRendererPane pane;

    public TableFooter(JTable table) {
        this.table = table;

        if (table.getParent() instanceof JViewport) {
            JViewport vp = (JViewport) table.getParent();
            vp.addChangeListener(this);
            if (vp.getParent() instanceof JScrollPane) {
                JScrollPane sp = (JScrollPane) vp.getParent();
                sp.getHorizontalScrollBar().addAdjustmentListener(this);
                pane = new CellRendererPane();
            }
        }

        table.getColumnModel().addColumnModelListener(this);
        table.getSelectionModel().addListSelectionListener(this);
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if (!(table.getModel() instanceof ITotalTableFooter)) {
            return;
        }
        //... render the cells taking care of the visible rect:
        Rectangle visRect = ((JViewport) table.getParent()).getViewRect();
        //... the "visRect.x" can be subtracted from the "x" parameter ...
        int startX = x - visRect.x;
        int startY = y + visRect.height;
        TOTAL_FONT = new Font("Arial", Font.BOLD, table.getFont().getSize());
        int rowCount = table.getRowCount();
        if (rowCount > 0) {
            int count = table.getColumnCount();
            for (int i = 0; i < count; i++) {
                int modelColIndex = table.convertColumnIndexToModel(i);
                TableColumn viewColumn = table.getColumnModel().getColumn(i);
                TableCellRenderer renderer = table.getCellRenderer(0, i);//col.getCellRenderer();
                int colWidth = viewColumn.getWidth();
                Object val = null;
                if (((ITotalTableFooter) table.getModel()).showTotalForColumn(modelColIndex)) {
                    val = ((ITotalTableFooter) table.getModel()).getTotal(modelColIndex);
                }
                if (renderer != null) {
                    DefaultTableCellRenderer.UIResource comp = new DefaultTableCellRenderer.UIResource();
                    comp.setHorizontalAlignment(JLabel.RIGHT);
                    comp.setFont(TOTAL_FONT);
//                                        comp.setVerticalTextPosition(JLabel.BOTTOM);
                    comp.setForeground(Color.BLACK);
                    if (renderer instanceof AmountCellRenderer
                            || renderer instanceof AmountDrCrCellRenderer) {
                        comp.setText((val == null) ? ""
                                : val.toString());
                    } else {
                        comp.setText((val == null) ? "" : val.toString());
                    }
                    comp.setOpaque(true);
//                    comp.setBackground(Color.LIGHT_GRAY);
//                    ;
//                    comp.setBackground(Utility.getColor("3C78B5"));
                    comp.setBackground(Utility.getDefault().getTableFooterColor());
                    comp.setBorder(compBorder);
//                                        if(UIManager.getBorder("TableHeader.cellBorder") != null) 
//                                            comp.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                    pane.paintComponent(g, comp, table, startX, startY, colWidth, table.getRowHeight());
                }
                startX += colWidth;
//				g.drawLine(startX , height + INSET_PADDING_BOTTOM, startX , height + table.getRowHeight() + INSET_PADDING_BOTTOM);
            }
//			g.drawRect(1, height, width, table.getRowHeight());
//			g.drawRect(1, height+INSET_PADDING_BOTTOM, width, 16);
        }
    }

    @Override
    public Insets getBorderInsets(Component c) {
        //... Here I assume the footer has the same font that the table's ...
        return new Insets(0, 0, table.getRowHeight() + 1, 0);

    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    private void repaintTable() {
        if (table.getParent() instanceof JViewport
                && table.getParent().getParent() instanceof JScrollPane) {
            table.getParent().getParent().repaint();
        } else {
            table.repaint();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        repaintTable();
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        repaintTable();
    }

    @Override
    public void columnAdded(TableColumnModelEvent e) {
        repaintTable();
    }

    @Override
    public void columnRemoved(TableColumnModelEvent e) {
        repaintTable();
    }

    @Override
    public void columnMoved(TableColumnModelEvent e) {
        repaintTable();
    }

    @Override
    public void columnMarginChanged(ChangeEvent e) {
        repaintTable();
    }

    @Override
    public void columnSelectionChanged(ListSelectionEvent e) {
        repaintTable();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        repaintTable();
    }
}