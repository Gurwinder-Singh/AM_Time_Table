/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.fill.JRTemplatePrintText;
import org.openide.util.Exceptions;

/**
 *
 * @author Maninderjit singh
 */
//    Please before edit call Maninderjit Singh 
public class CreateReportAnnexure {

    private JasperPrint printableReport;
    private String title;

    public CreateReportAnnexure(JasperPrint printableReport) {
        this.printableReport = printableReport;
    }

    public void setInformation(JFrame asReportViewer) {
        List<JRPrintPage> pages = printableReport.getPages();
        ArrayList list = new ArrayList();
        ArrayList<String> txtlist = new ArrayList();
        for (int i = 0; i < pages.size(); i++) {
            JRPrintPage page = pages.get(i);
            List<JRPrintElement> elements = page.getElements();
            for (int j = 0; j < elements.size(); j++) {
                JRPrintElement element = elements.get(j);

                if (element instanceof JRTemplatePrintText) {
                    JRTemplatePrintText text = (JRTemplatePrintText) element;
                    text.getKey();//textfield-1
                    if (text.getOrigin().getGroupName() != null) {//
                        if (text.getOrigin().getBandTypeValue().getName() != null
                                && text.getOrigin().getBandTypeValue().getName().equalsIgnoreCase("groupHeader")
                                && text.getKey() != null && text.getKey().equalsIgnoreCase("textField")) {//    Please call Maninderjit Singh before edit

                            Element val = new Element();
                            val.setPageno(i + 1);//Page No.
                            val.setBandname(text.getOrigin().getBandTypeValue().getName());//title: band name
                            String txtValue = text.getText();
                            val.setText(txtValue);//001 Traders: text value
                            val.setGroupname(text.getOrigin().getGroupName());//Account: group name

                            if (txtValue != null && !txtlist.contains(txtValue)) {
                                txtlist.add(txtValue);
                                list.add(val);
                            }
                        }
                    }
                }
            }
        }
        //list after compare  by pageno.
        createReportAnnexure(list.iterator(), asReportViewer);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private void createReportAnnexure(Iterator iterator, JFrame asReportViewer) {
        try {
            Model model = new Model(iterator);
            JasperReport areport = new ASJasperReport().getJasperReport("/com/as/report/objects/AnnexureReport.jasper");
            HashMap param = new HashMap();
            JasperPrint localprint = JasperFillManager.fillReport(areport, param, model);
             if( printableReport!=null && localprint!=null ){
            int s = localprint.getPages().size();
           for (int i = s-1 ; i >=0 ; i--) {
               JRPrintPage page = localprint.getPages().get(i);
                printableReport.addPage(0,page);
            }
            }

//            for (int i = 0; i < printableReport.getPages().size(); i++) {
//                JRPrintPage page = printableReport.getPages().get(i);
//                pagelist.add(page);
//            }
//
//            for (int i = 0; i < printableReport.getPages().size(); i++) {
//                printableReport.removePage(i);
//            }

//            for (int i = 0; i < pagelist.size(); i++) {
//                JRPrintPage page = pagelist.get(i);
//                .addPage(page);
//            }
//            JRPrintPage
//            this.printableReport.getPages();
//            localprint.getPages();
//            ASReportViewer viewer = new ASReportViewer(true, true, localprint);
//            viewer.setTitle(title != null && !title.isEmpty() ? title : "Annexure");
//            viewer.setVisible(true);
        } catch (JRException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}

class Model implements JRDataSource {

    private Iterator<Element> iterator = null;
    private Element obj = null;

    public Model(Iterator iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean next() throws JRException {
        if (iterator.hasNext()) {
            obj = (Element) iterator.next();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object getFieldValue(JRField field) throws JRException {
        if (field.getName().equals("text")) {
            return obj.getText();
        } else if (field.getName().equals("pageno")) {
            return obj.getPageno() + "";
        } else {
            return "";
        }
    }
}

class Element {

    private int pageno;
    private String bandname;
    private String text;
    private String groupname;

    /**
     * @return the pageno
     */
    public int getPageno() {
        return pageno;
    }

    /**
     * @param pageno the pageno to set
     */
    public void setPageno(int pageno) {
        this.pageno = pageno;
    }

    /**
     * @return the bandname
     */
    public String getBandname() {
        return bandname;
    }

    /**
     * @param bandname the bandname to set
     */
    public void setBandname(String bandname) {
        this.bandname = bandname;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the groupname
     */
    public String getGroupname() {
        return groupname;
    }

    /**
     * @param groupname the groupname to set
     */
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}
