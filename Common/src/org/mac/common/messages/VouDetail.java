/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mac.common.messages;

import java.math.BigDecimal;

/**
 *
 * @author SourceThink Developers
 */
public class VouDetail {
    
    private long id;
    private long led_id;
    private String ag_type;
    private String pending_tran;
    private long ag_vou_id;
    private BigDecimal amount;
    private String chequeNo;

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
     * @return the ag_vou_id
     */
    public long getAg_vou_id() {
        return ag_vou_id;
    }

    /**
     * @param ag_vou_id the ag_vou_id to set
     */
    public void setAg_vou_id(long ag_vou_id) {
        this.ag_vou_id = ag_vou_id;
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
     * @return the chequeNo
     */
    public String getChequeNo() {
        return chequeNo;
    }

    /**
     * @param chequeNo the chequeNo to set
     */
    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    /**
     * @return the pending_tran
     */
    public String getPending_tran() {
        return pending_tran;
    }

    /**
     * @param pending_tran the pending_tran to set
     */
    public void setPending_tran(String pending_tran) {
        this.pending_tran = pending_tran;
    }

    /**
     * @return the ag_type
     */
    public String getAg_type() {
        return ag_type;
    }

    /**
     * @param ag_type the ag_type to set
     */
    public void setAg_type(String ag_type) {
        this.ag_type = ag_type;
    }
    
    
}
