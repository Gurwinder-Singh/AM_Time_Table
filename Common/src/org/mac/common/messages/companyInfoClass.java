/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mac.common.messages;

/**
 *
 * @author SourceThink Developers
 */
public class companyInfoClass extends ExtendedMessage{

    private String compName;
    private String mobileNo;
    private String compAddress;
    private String compTinVAt;
    private String TerCondition;
    private String invoiceHeading;
    private String taxName;

    /**
     * @return the compName
     */
    public String getCompName() {
        return compName;
    }

    /**
     * @param compName the compName to set
     */
    public void setCompName(String compName) {
        this.compName = compName;
    }

    /**
     * @return the mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * @param mobileNo the mobileNo to set
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * @return the compAddress
     */
    public String getCompAddress() {
        return compAddress;
    }

    /**
     * @param compAddress the compAddress to set
     */
    public void setCompAddress(String compAddress) {
        this.compAddress = compAddress;
    }

    /**
     * @return the compTinVAt
     */
    public String getCompTinVAt() {
        return compTinVAt;
    }

    /**
     * @param compTinVAt the compTinVAt to set
     */
    public void setCompTinVAt(String compTinVAt) {
        this.compTinVAt = compTinVAt;
    }

    /**
     * @return the TerCondition
     */
    public String getTerCondition() {
        return TerCondition;
    }

    /**
     * @param TerCondition the TerCondition to set
     */
    public void setTerCondition(String TerCondition) {
        this.TerCondition = TerCondition;
    }

    /**
     * @return the invoiceHeading
     */
    public String getInvoiceHeading() {
        return invoiceHeading;
    }

    /**
     * @param invoiceHeading the invoiceHeading to set
     */
    public void setInvoiceHeading(String invoiceHeading) {
        this.invoiceHeading = invoiceHeading;
    }

    /**
     * @return the taxName
     */
    public String getTaxName() {
        return taxName;
    }

    /**
     * @param taxName the taxName to set
     */
    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }
}
