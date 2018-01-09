package com.gdev.common.reports;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Constructor;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.print.PrintService;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRClassLoader;
import net.sf.jasperreports.view.JRSaveContributor;
import org.mac.common.messages.ExtendedMessage;

import com.gdev.common.other.Utility;
import org.openide.awt.StatusDisplayer.Message;

import org.openide.util.Utilities;

public class ASReportViewer extends JFrame {

    private JRViewer viewer;
    private JPanel pnlMain;
    private static final String ICON_SMALL = "org/netbeans/core/startup/frame.gif"; // NOI18N
    private static final String ICON_BIG = "org/netbeans/core/startup/frame32.gif"; // NOI18N

    public ASReportViewer(JasperPrint jasperPrint) {

        initComponents();
        this.viewer = new InternalView(jasperPrint, null);
        viewer.btnSave.setVisible(true);
        viewer.btnPrint.setVisible(true);
//        viewer.btnSave.setVisible(Utility.getUserType().trim().equals("A") ? true : Utility.getUser().getSettings().isSave_report());
//        viewer.btnPrint.setVisible(Utility.getUserType().trim().equals("A") ? true : Utility.getUser().getSettings().isPrint_report());
        this.pnlMain.add(this.viewer, BorderLayout.CENTER);
        setIconImage(createIDEImage());
    }

    public ASReportViewer(boolean print, boolean save, JasperPrint jasperPrint) {
        System.out.println("In as viewer normal");

        initComponents();
        this.viewer = new InternalView(jasperPrint, null);
        viewer.btnSave.setVisible(save);
        viewer.btnPrint.setVisible(print);
        this.pnlMain.add(this.viewer, BorderLayout.CENTER);
        setIconImage(createIDEImage());
    }

    public ASReportViewer(JasperPrint jasperPrint, boolean withPrintDialog) {
        InternalView viewer = new InternalView(jasperPrint, null);
        viewer.print(jasperPrint, withPrintDialog);
    }

    public ASReportViewer(JasperPrint jasperPrint, boolean withPrintDialog, String printerName) {
        InternalView viewer = new InternalView(jasperPrint, null);
        viewer.print(jasperPrint, withPrintDialog, printerName);
    }

    public ASReportViewer(boolean print, boolean save, JasperPrint jasperPrint, ExtendedMessage message) {
        System.out.println("In as viewer normal");

        initComponents();
        this.viewer = new InternalView(jasperPrint, message);
        viewer.btnSave.setVisible(save);
        viewer.btnPrint.setVisible(print);
        this.pnlMain.add(this.viewer, BorderLayout.CENTER);
        setIconImage(createIDEImage());
    }

    public static void main(String[] args) throws JRException {

        JRPdfExporter exporter = new JRPdfExporter();
        //exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
        exporter.exportReport();
    }

    private static Image createIDEImage() {
        return Utilities.loadImage(Utilities.isLargeFrameIcons() ? ICON_BIG
                : ICON_SMALL, true);
    }

    /**
     *
     */
    public void setFitPageZoomRatio() {
        viewer.setFitPageZoomRatio();
    }

    public void setPageSize(int printPreview) {
        viewer.setPageSizeAsMasterSettings(printPreview);
    }

    public void createAnnexure(String annexureTitle) {
        //        viewer.jasperPrint;
        CreateReportAnnexure report = new CreateReportAnnexure(viewer.jasperPrint);
        report.setInformation(this);
        report.setTitle(annexureTitle);
    }

    private void initComponents() {// GEN-BEGIN:initComponents
        pnlMain = new javax.swing.JPanel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm();
            }
        });

        pnlMain.setLayout(new java.awt.BorderLayout());

        getContentPane().add(pnlMain, java.awt.BorderLayout.CENTER);

        pack();

        Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
        java.awt.Dimension screenSize = toolkit.getScreenSize();
        int screenResolution = toolkit.getScreenResolution();
        float zoom = ((float) screenResolution) / JRViewer.REPORT_RESOLUTION;

        int height = (int) (550 * zoom);
        if (height > screenSize.getHeight()) {
            height = (int) screenSize.getHeight();
        }
        int width = (int) (750 * zoom);
        if (width > screenSize.getWidth()) {
            width = (int) screenSize.getWidth();
        }

        java.awt.Dimension dimension = new java.awt.Dimension(width, height);
        setSize(dimension);
        setLocation((screenSize.width - width) / 2,
                (screenSize.height - height) / 2);
    }// GEN-END:initComponents

    private void exitForm() {
        this.setVisible(false);
        if (viewer != null) {
            this.viewer.clear();
        }
        this.viewer = null;
        this.getContentPane().removeAll();
        this.dispose();
    }

    private class InternalView extends JRViewer {

        private ResourceBundle resourceBundle;

//        public InternalView(JasperPrint jasperPrint) {
//            super(jasperPrint);
//            this.resourceBundle = ResourceBundle.getBundle("net/sf/jasperreports/view/viewer", getLocale());
//            // TODO Auto-generated constructor stub
//        }
        public InternalView(JasperPrint jasperPrint, ExtendedMessage msg) {
            super(jasperPrint, msg);
            this.resourceBundle = ResourceBundle.getBundle("net/sf/jasperreports/view/viewer", getLocale());
            // TODO Auto-generated constructor stub
        }

        protected void initSaveContributors() {
            final String[] DEFAULT_CONTRIBUTORS = {
                "net.sf.jasperreports.view.save.JRPdfSaveContributor",
                "net.sf.jasperreports.view.save.JRRtfSaveContributor",
                "net.sf.jasperreports.view.save.JRHtmlSaveContributor",
                "net.sf.jasperreports.view.save.JRSingleSheetXlsSaveContributor",
                "net.sf.jasperreports.view.save.JRMultipleSheetsXlsSaveContributor",
                "net.sf.jasperreports.view.save.JRCsvSaveContributor",};

            for (int i = 0; i < DEFAULT_CONTRIBUTORS.length; i++) {
                try {
                    Class saveContribClass = JRClassLoader.loadClassForName(DEFAULT_CONTRIBUTORS[i]);
                    Constructor constructor = saveContribClass.getConstructor(new Class[]{Locale.class, ResourceBundle.class});
                    JRSaveContributor saveContrib = (JRSaveContributor) constructor.newInstance(new Object[]{getLocale(), resourceBundle});
                    saveContributors.add(saveContrib);
                } catch (Exception e) {
                }
            }
        }

        public void print(JasperPrint print, final boolean withPrintDialog) {
            print(print, withPrintDialog, null);
        }

        public void print(JasperPrint print, final boolean withPrintDialog, final String printer) {
            final JasperPrint p = print;
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        System.out.println("In viewer Print");
                        PrinterJob pj = PrinterJob.getPrinterJob();

                        if (printer != null) {
//                            PrintService ps = PrinterUtility.getDefault().getPrintService(printer);
//                            if(ps!=null){
//                             pj.setPrintService(ps);
//                            }
//                    boolean printSucceed = JasperPrintManager.printReport(p, false);
                        }
                        JasperPrintManager.printReport(p, withPrintDialog);
                    } catch (Exception ex) {
                        System.out.println("In viewer Print Exception");

                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(viewer,
                                "error.printing", "Information",
                                JOptionPane.INFORMATION_MESSAGE);

                    }
                }
            });

            thread.start();
        }
    }
}
