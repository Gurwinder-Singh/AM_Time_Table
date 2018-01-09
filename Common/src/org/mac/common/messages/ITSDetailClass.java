/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mac.common.messages;

import java.math.BigDecimal;

/**
 *
 * @author Gurwinder Singh
 */
public class ITSDetailClass extends ExtendedMessage {

    private long itemId;
    private long typeId;
    private long styleId;
    private long c_qty;
    private long op_qty;
    private long id;
    private BigDecimal rate;
    private String barcode;

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
     * @return the c_qty
     */
    public long getC_qty() {
        return c_qty;
    }

    /**
     * @param c_qty the c_qty to set
     */
    public void setC_qty(long c_qty) {
        this.c_qty = c_qty;
    }

    /**
     * @return the op_qty
     */
    public long getOp_qty() {
        return op_qty;
    }

    /**
     * @param op_qty the op_qty to set
     */
    public void setOp_qty(long op_qty) {
        this.op_qty = op_qty;
    }
}
