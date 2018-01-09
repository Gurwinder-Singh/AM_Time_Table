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
public class ITSTrackDetail {
    private long id;
    private Date dt;
    private long item_id;
    private long type_id;
    private long style_id;
    private long led_id;
    
    private BigDecimal qty;
    
    private String type;
    private String inv_no;

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
     * @return the dt
     */
    public Date getDt() {
        return dt;
    }

    /**
     * @param dt the dt to set
     */
    public void setDt(Date dt) {
        this.dt = dt;
    }

    /**
     * @return the item_id
     */
    public long getItem_id() {
        return item_id;
    }

    /**
     * @param item_id the item_id to set
     */
    public void setItem_id(long item_id) {
        this.item_id = item_id;
    }

    /**
     * @return the type_id
     */
    public long getType_id() {
        return type_id;
    }

    /**
     * @param type_id the type_id to set
     */
    public void setType_id(long type_id) {
        this.type_id = type_id;
    }

    /**
     * @return the style_id
     */
    public long getStyle_id() {
        return style_id;
    }

    /**
     * @param style_id the style_id to set
     */
    public void setStyle_id(long style_id) {
        this.style_id = style_id;
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
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
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
    
    
}
