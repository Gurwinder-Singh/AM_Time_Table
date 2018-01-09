/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mac.common.messages;

import java.util.Vector;

/**
 *
 * @author Gurwinder Singh
 */
public class ClientUpdate extends ExtendedMessage{
    private Vector items;
    private Vector types;
    private Vector styles;
    private Vector category;
    private Vector taxAndDiscount;
    private Vector ledgers;
    private Vector ITSList;
    private String inv_no;
    private String payment_vou_no;
    

    /**
     * @return the items
     */
    public Vector getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(Vector items) {
        this.items = items;
    }

    /**
     * @return the types
     */
    public Vector getTypes() {
        return types;
    }

    /**
     * @param types the types to set
     */
    public void setTypes(Vector types) {
        this.types = types;
    }

    /**
     * @return the styles
     */
    public Vector getStyles() {
        return styles;
    }

    /**
     * @param styles the styles to set
     */
    public void setStyles(Vector styles) {
        this.styles = styles;
    }

    /**
     * @return the category
     */
    public Vector getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Vector category) {
        this.category = category;
    }

    /**
     * @return the taxAndDiscount
     */
    public Vector getTaxAndDiscount() {
        return taxAndDiscount;
    }

    /**
     * @param taxAndDiscount the taxAndDiscount to set
     */
    public void setTaxAndDiscount(Vector taxAndDiscount) {
        this.taxAndDiscount = taxAndDiscount;
    }

    /**
     * @return the ledgers
     */
    public Vector getLedgers() {
        return ledgers;
    }

    /**
     * @param ledgers the ledgers to set
     */
    public void setLedgers(Vector ledgers) {
        this.ledgers = ledgers;
    }

    /**
     * @return the ITSList
     */
    public Vector getITSList() {
        return ITSList;
    }

    /**
     * @param ITSList the ITSList to set
     */
    public void setITSList(Vector ITSList) {
        this.ITSList = ITSList;
    }

    /**
     * @return the inv_no
     */
    public String getInv_no() {
        return inv_no;
    }

    /**
     * @param inv_no the inv_no to set
     */
    public void setInv_no(String inv_no) {
        this.inv_no = inv_no;
    }

    /**
     * @return the payment_vou_no
     */
    public String getPayment_vou_no() {
        return payment_vou_no;
    }

    /**
     * @param payment_vou_no the payment_vou_no to set
     */
    public void setPayment_vou_no(String payment_vou_no) {
        this.payment_vou_no = payment_vou_no;
    }

   
    
    
}
