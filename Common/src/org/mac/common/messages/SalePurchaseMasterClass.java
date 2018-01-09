/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mac.common.messages;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Vector;

/**
 *
 * @author SourceThink Developers
 */
public class SalePurchaseMasterClass extends ExtendedMessage{

    private Date date;
    
    private long led_id;
    private long orignal_led_id;
    private long categoryId;
    private long id;
    private long tax_led_id;
    private long vou_id;
    
    private BigDecimal qty;
    private BigDecimal tax;
    private BigDecimal amount;
    private BigDecimal discount;
    private BigDecimal originalAmount;
    private BigDecimal vou_amt;
    
    
    private String remarks;
    private String invoiceNo;
    private String nameAddress;
    
    private Vector<SalePurchaseDetailClass> spDetail;

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    

    /**
     * @return the categoryId
     */
    public long getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the qty
     */
    public BigDecimal getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    /**
     * @return the tax
     */
    public BigDecimal getTax() {
        return tax;
    }

    /**
     * @param tax the tax to set
     */
    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the invoiceNo
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * @param invoiceNo the invoiceNo to set
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * @return the nameAddress
     */
    public String getNameAddress() {
        return nameAddress;
    }

    /**
     * @param nameAddress the nameAddress to set
     */
    public void setNameAddress(String nameAddress) {
        this.nameAddress = nameAddress;
    }

    /**
     * @return the spDetail
     */
    public Vector<SalePurchaseDetailClass> getSpDetail() {
        return spDetail;
    }

    /**
     * @param spDetail the spDetail to set
     */
    public void setSpDetail(Vector<SalePurchaseDetailClass> spDetail) {
        this.spDetail = spDetail;
    }

    /**
     * @return the discount
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * @return the led_id
     */
    public long getLed_id() {
        return led_id;
    }

    /**
     * @param led_id the led_id to set
     */
    public void setLed_id(long led_id) {
        this.led_id = led_id;
    }

    /**
     * @return the orignal_led_id
     */
    public long getOrignal_led_id() {
        return orignal_led_id;
    }

    /**
     * @param orignal_led_id the orignal_led_id to set
     */
    public void setOrignal_led_id(long orignal_led_id) {
        this.orignal_led_id = orignal_led_id;
    }

    /**
     * @return the originalAmount
     */
    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    /**
     * @param originalAmount the originalAmount to set
     */
    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    /**
     * @return the tax_led_id
     */
    public long getTax_led_id() {
        return tax_led_id;
    }

    /**
     * @param tax_led_id the tax_led_id to set
     */
    public void setTax_led_id(long tax_led_id) {
        this.tax_led_id = tax_led_id;
    }

    /**
     * @return the vou_id
     */
    public long getVou_id() {
        return vou_id;
    }

    /**
     * @param vou_id the vou_id to set
     */
    public void setVou_id(long vou_id) {
        this.vou_id = vou_id;
    }

    /**
     * @return the vou_amt
     */
    public BigDecimal getVou_amt() {
        return vou_amt;
    }

    /**
     * @param vou_amt the vou_amt to set
     */
    public void setVou_amt(BigDecimal vou_amt) {
        this.vou_amt = vou_amt;
    }
    
}
