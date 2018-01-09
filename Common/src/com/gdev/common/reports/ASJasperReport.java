/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common.reports;

//import com.as.common.messages.BookConfigDetail;
import com.gdev.common.other.Utility;
import java.io.InputStream;
import java.io.ObjectInputStream;
import net.sf.jasperreports.crosstabs.JRCellContents;
import net.sf.jasperreports.crosstabs.JRCrosstabCell;
import net.sf.jasperreports.crosstabs.JRCrosstabColumnGroup;
import net.sf.jasperreports.crosstabs.JRCrosstabRowGroup;
import net.sf.jasperreports.crosstabs.base.JRBaseCrosstab;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRSection;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseStaticText;
import net.sf.jasperreports.engine.base.JRBaseTextField;

/**
 *
 * @author admin
 */
public class ASJasperReport {

    private JasperReport report;

    
      public JasperReport getJasperReport(String parameter) {
        try {
             InputStream is = this.getClass().getResourceAsStream(parameter);
           getJasperReport(is);
         } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
        }
        
//          public JasperReport getJasperReport(BookConfigDetail config, String parameter) {
//        try {
//            InputStream is = this.getClass().getResourceAsStream(parameter);
//           getJasperReport(config,is);
//         } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return report;
//        }
        
//    public JasperReport getJasperReport(BookConfigDetail config,  InputStream is) {
//        try {
//          
//            //Object of ObjectInputStream class is created and Intialised by passing Object of InputStream class
//            ObjectInputStream ois = new ObjectInputStream(is);
//            //Object of Jaspereport class is created and cast using calling readObject method of ObjectInputStream class
//            report = (JasperReport) ois.readObject();
//            if (config.getFontSize() > 0) {
//                for (JRBand band : report.getDetailSection().getBands()) {
//                    for (int i = 0; band.getChildren().size() > i; i++) {
//                        try {
//                            JRBaseTextField txtFld = (JRBaseTextField) band.getChildren().get(i);
//                            txtFld.setFontSize(txtFld.getFontSize() + config.getFontSize());
//                        } catch (ClassCastException e) {
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return report;
//    }
    
    public JasperReport getJasperReport(InputStream is) {
        try {
            //Object of ObjectInputStream class is created and Intialised by passing Object of InputStream class
            ObjectInputStream ois = new ObjectInputStream(is);
            //Object of Jaspereport class is created and cast using calling readObject method of ObjectInputStream class
            report = (JasperReport) ois.readObject();

            setFontSizeForSection(report.getDetailSection());// EDITED BY MANINDERJIT SINGH 25-JULY-2014

            if (report.getGroups() != null) {
                for (JRGroup group : report.getGroups()) {
                    setFontSizeForSection(group.getGroupHeaderSection());
                    setFontSizeForSection(group.getGroupFooterSection());
                }
            }
            setFontSizeForBand(report.getColumnHeader());
            setFontSizeForBand(report.getColumnFooter());

            JRBand summary = report.getSummary();
            for (int i = 0; summary.getChildren().size() > i; i++) {
                try {
                    if (summary.getChildren().get(i) instanceof JRBaseTextField) {
                        JRBaseTextField txtFld = (JRBaseTextField) summary.getChildren().get(i);
                        txtFld.setFontSize(txtFld.getFontSize() + 0);
                        txtFld.setStretchWithOverflow(true);
                    } else if (summary.getChildren().get(i) instanceof JRBaseCrosstab) {//For Cross Tab EDITED BY MANINDERJIT SINGH 25-JULY-2014
                        JRBaseCrosstab crossTab = (JRBaseCrosstab) summary.getChildren().get(i);

                        setFontSizeForCellContents(crossTab.getHeaderCell());
                        setFontSizeForCellContents(crossTab.getWhenNoDataCell());

                        JRCrosstabColumnGroup[] columnGroups = crossTab.getColumnGroups();
                        for (JRCrosstabColumnGroup colGrp : columnGroups) {
                            setFontSizeForCellContents(colGrp.getHeader());
                            setFontSizeForCellContents(colGrp.getTotalHeader());
                        }
                        JRCrosstabRowGroup[] rowGroups = crossTab.getRowGroups();
                        for (JRCrosstabRowGroup rolGrp : rowGroups) {
                            setFontSizeForCellContents(rolGrp.getHeader());
                            setFontSizeForCellContents(rolGrp.getTotalHeader());
                        }
                        JRCrosstabCell[][] cells = crossTab.getCells();
                        for (int c = 0; c < cells.length; c++) {
                            if (cells[c] != null) {
                                for (int r = 0; r < cells[c].length; r++) {
                                    if (cells[c][r] != null) {
                                        JRCrosstabCell cell = (JRCrosstabCell) cells[c][r];
                                        setFontSizeForCellContents(cell.getContents());
                                    }
                                }
                            }
                        }
                    }
                } catch (ClassCastException e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
    }

    private void setFontSizeForBand(JRBand band) {
        if (band!=null && band.getChildren() != null) {
            for (int i = 0; band.getChildren().size() > i; i++) {
                try {
                    if (band.getChildren().get(i) instanceof JRBaseTextField) {
                        JRBaseTextField txtFld = (JRBaseTextField) band.getChildren().get(i);
                        txtFld.setFontSize(txtFld.getFontSize() +0);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    private void setFontSizeForSection(JRSection section) {
        if (section.getBands() != null) {
            for (JRBand band : section.getBands()) {
                setFontSizeForBand(band);
            }
        }
    }

    private void setFontSizeForCellContents(JRCellContents contents) {
        if (contents != null) {
            JRElement[] elements = contents.getElements();
            for (JRElement element : elements) {
                if (element instanceof JRBaseTextField) {
                    JRBaseTextField txtFld = (JRBaseTextField) element;
                    txtFld.setFontSize(txtFld.getFontSize() + 0);
                    txtFld.setStretchWithOverflow(true);
                } else if (element instanceof JRBaseStaticText) {
                    JRBaseStaticText txtFld = (JRBaseStaticText) element;
                    txtFld.setFontSize(txtFld.getFontSize() + 0);
                }
            }
        }
    }
  
}
