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
public class VouMaster extends ExtendedMessage{

    private long id;
    private Date dt;
    private long led_id;
    private String vou_no;
     private String pending_tran;
    private Vector<VouDetail> detail;
    private long to_led_id;
    private String ag_type;
    private long ag_vou_id;
    private String chequeNo;
    private BigDecimal amount;

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
     * @return the vou_no
     */
    public String getVou_no() {
        return vou_no;
    }

    /**
     * @param vou_no the vou_no to set
     */
    public void setVou_no(String vou_no) {
        this.vou_no = vou_no;
    }

  

    /**
     * @return the to_led_id
     */
    public long getTo_led_id() {
        return to_led_id;
    }

    /**
     * @param to_led_id the to_led_id to set
     */
    public void setTo_led_id(long to_led_id) {
        this.to_led_id = to_led_id;
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

    /**
     * @return the detail
     */
    public Vector<VouDetail> getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(Vector<VouDetail> detail) {
        this.detail = detail;
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
    
    
}
