package com.gdev.common.interfaces;



import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import java.math.BigDecimal;

public class AmountDrCrCellRenderer extends DefaultTableCellRenderer {
    @Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if(value != null && value instanceof BigDecimal) {
                            this.setText(value.toString());
//				this.setText(AdcNumberFormatter.getDefault().getDrCrAmount((BigDecimal)value,
//                                        Utility.getCompanyInfo().getCurrSymbol(),Utility.getmSettings().getDisplayFigureInReport()));
                this.setAlignmentX(RIGHT);
              }
			return c;
		}
		
	}
