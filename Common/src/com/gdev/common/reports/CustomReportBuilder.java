package com.gdev.common.reports;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import com.gdev.common.Controller;
import org.mac.common.messages.SalePurchaseDetailClass;
import org.mac.common.messages.SalePurchaseMasterClass;
import org.mac.common.messages.companyInfoClass;
import com.gdev.common.other.FileUtility;
import com.gdev.common.other.MessageDisplay;
import com.gdev.common.other.Utility;

//import com.as.common.messages.CreateSale;
/**
 *
 * @author Gurwinder Singh
 */
public class CustomReportBuilder {

    private SalePurchaseMasterClass sResp;
    private boolean vat;
    private Vector data;

    public CustomReportBuilder() {
    }

    public void printReport(SalePurchaseMasterClass info, boolean vat) {
        this.sResp = info;
        this.vat = vat;
        this.data = info.getSpDetail();
        try {
//            ModelSaleUi Smodel = new ModelSaleUi(data); //new ModelStac(data);
            JasperReport report = null;
            if (data != null && data.size() > 0) {
                report = new ASJasperReport().getJasperReport("invoice_tax_31(A)(MRP).rep");
//                 report = new ASJasperReport().getJasperReport("Slip_invoice_34(A).rep");

//                report.getColumnFoos  ter().setSplitType(SplitTypeEnum.STRETCH);
//report.getNoData().getChildren().
                companyInfoClass compInfo = FileUtility.getDefault().getCompanyInfo();
                if (compInfo == null) {
                    MessageDisplay.showErrorDialog(Controller.getDefault().getParent(), "Company Information Not Found");
                    return;
                }

                JasperPrint printableReport = getPrint(report, true);
                int i = MessageDisplay.showConfirmDialog(Controller.getDefault().getParent(), "Do you want Duplicate Copy of the Bill?", 
                        "Sure?");
                if (i == MessageDisplay.YES_OPTION) {
                    JasperPrint duplicate = getPrint(report, false);
                    for (Iterator<JRPrintPage> it = duplicate.getPages().iterator(); it.hasNext();) {
                        printableReport.addPage(it.next());

                    }
                }
                ASReportViewer viewer = new ASReportViewer(printableReport);
                viewer.setTitle(" MAC Report Viewer");
                viewer.setVisible(true);
                viewer.setFitPageZoomRatio();
//                return JasperExportManager.exportReportToPdf(printableReport);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private JasperPrint getPrint(JasperReport report, boolean original) throws Exception {

        HashMap params = getParametersForPrint(original);
        if (params == null) {
            MessageDisplay.showErrorDialog(Controller.getDefault().getParent(), "Company Information Not Found");
            return null;
        }
        int lines = data.size();
        while (lines < 12) {
            SalePurchaseDetailClass model = new SalePurchaseDetailClass();
            data.add(model);
            lines++;
        }
        ModelSaleUi Smodel = new ModelSaleUi(data);
//                params.put("inv_no", info.getInvoiceNo());
//                params.put("Date", info.getDt());
//                params.put("ledger", info.getName());
//                params.put("address", info.getAddress());
//                params.put("tax1", info.getTaxid_1());
//                params.put("tax2", info.getTaxid_2());
//                params.put("other", info.getOther());
//                params.put("agent", info.getAgent());
//                params.put("DMode", info.getDestination());

        return JasperFillManager.fillReport(
                report, params, Smodel);
    }

    @SuppressWarnings("unchecked")
    public HashMap getParametersForPrint(boolean original) {
        Utility func = new Utility();
        HashMap params = new HashMap();
        companyInfoClass compInfo = FileUtility.getDefault().getCompanyInfo();
        if (compInfo == null) {
            return null;
        }
        try {
            params.put("compname", compInfo.getCompName());
            params.put("compaddress", compInfo.getCompAddress());
            params.put("contact", "Mobile No. " + compInfo.getMobileNo());
            params.put("invoice_heading", (vat ? "Vat " : "Retail ") + compInfo.getInvoiceHeading());
            params.put("rep_heading", original ? "Orignal" : "Duplicate");
            params.put("invoice_no", sResp.getInvoiceNo());
            params.put("i_date", Utility.getFormatedDate(sResp.getDate()));
            if (sResp.getNameAddress() == null || sResp.getNameAddress().trim().length() == 0) {
                params.put("name", Controller.getDefault().getLedgerById().get(sResp.getLed_id()).getName());
                params.put("address", Controller.getDefault().getLedgerById().get(sResp.getLed_id()).getAddress());
            } else {
                String[] nAdd = sResp.getNameAddress().split("\n");
                params.put("name", nAdd[0]);
                params.put("address", (nAdd.length > 1 ? sResp.getNameAddress().substring(nAdd[0].length() + 1, sResp.getNameAddress().length()).replace("\n", "<br>") : ""));
            }

            params.put("packs", false);
            params.put("logoBorder", true);
            params.put("partyname", sResp.getNameAddress() != null ? sResp.getNameAddress()
                    : Controller.getDefault().getLedgerById().get(sResp.getLed_id()).getName());
            params.put("Seprator", true);
            params.put("discount", sResp.getDiscount());
            params.put("grand_total", sResp.getAmount());
            params.put("Tax_1", (compInfo.getTaxName() == null || compInfo.getTaxName().trim().length() == 0)
                    ? (sResp.getTax_led_id() > 0 ? Controller.getDefault().getTaxById().get(sResp.getTax_led_id()).getName()
                    : "Tax") : compInfo.getTaxName());
            params.put("tax_amount", sResp.getTax());
            params.put("Amt_word", new GetAmountInWords().getWords(sResp.getAmount(), false));
            params.put("logo", "Logo.png");
            params.put("totalPages", 1);
//            params.put("name", sResp.getKey() + agent );
            params.put("totalEntries", data.size());

            params.put("taxes", compInfo.getCompTinVAt());
            params.put("notes", compInfo.getTerCondition().replace("\n", "<br>"));
            params.put("forStamp", compInfo.getCompName());
            params.put("printVatNote", vat);
            params.put("notelogo", System.getProperty("netbeans.user") + "\\note.jpg");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public String getAmountInWorlds(BigDecimal amount) {
//	   GetAmountInWords amountInWords = new GetAmountInWords();
//	   return amountInWords.getWordOfAmount(amount, false);
        return "";
    }
}
