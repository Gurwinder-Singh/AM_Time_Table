/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mac.common.messages;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author SourceThink Developers
 */
public class SalePurchaseDetailClass extends ExtendedMessage{

   private long id;
    private Date date;
    private long itemId;
    private long typeId;
    private long styleId;
    private long tran_id;
    private long tax_led_id;
    private BigDecimal qty;
    private BigDecimal rate;
    private BigDecimal amount;
    private BigDecimal tax_amount;
    private String remarks;
    private String barcode;
    

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
     * @return the itemId
     */
    public long getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    /**
     * @return the typeId
     */
    public long getTypeId() {
        return typeId;
    }

    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    /**
     * @return the styleId
     */
    public long getStyleId() {
        return styleId;
    }

    /**
     * @param styleId the styleId to set
     */
    public void setStyleId(long styleId) {
        this.styleId = styleId;
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
     * @return the rate
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
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
     * @return the tran_id
     */
    public long getTran_id() {
        return tran_id;
    }

    /**
     * @param tran_id the tran_id to set
     */
    public void setTran_id(long tran_id) {
        this.tran_id = tran_id;
    }

    /**
     * @return the barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * @param barcode the barcode to set
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
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
     * @return the tax_amount
     */
    public BigDecimal getTax_amount() {
        return tax_amount;
    }

    /**
     * @param tax_amount the tax_amount to set
     */
    public void setTax_amount(BigDecimal tax_amount) {
        this.tax_amount = tax_amount;
    }
    
}
