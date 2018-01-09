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
public class BarcodeDetail extends ExtendedMessage{
    private long id;
    private String barcode;
    private Vector detail;

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
     * @return the detail
     */
    public Vector getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(Vector detail) {
        this.detail = detail;
    }
    
}
