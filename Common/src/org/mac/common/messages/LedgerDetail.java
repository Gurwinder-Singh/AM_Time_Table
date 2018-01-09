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
public class LedgerDetail extends ExtendedMessage{
    private long tran_id;
    private String name;
    private String mobile;
    private String contact;
    private long pin;
    private String address;
    private String groupType;

    private BigDecimal op_bal;
    private BigDecimal c_bal;
    private int group_id;

    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the pin
     */
    public long getPin() {
        return pin;
    }

    /**
     * @param pin the pin to set
     */
    public void setPin(long pin) {
        this.pin = pin;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the groupType
     */
    public String getGroupType() {
        return groupType;
    }

    /**
     * @param groupType the groupType to set
     */
    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    /**
     * @return the op_bal
     */
    public BigDecimal getOp_bal() {
        return op_bal;
    }

    /**
     * @param op_bal the op_bal to set
     */
    public void setOp_bal(BigDecimal op_bal) {
        this.op_bal = op_bal;
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
     * @return the c_bal
     */
    public BigDecimal getC_bal() {
        return c_bal;
    }

    /**
     * @param c_bal the c_bal to set
     */
    public void setC_bal(BigDecimal c_bal) {
        this.c_bal = c_bal;
    }

    /**
     * @return the group_id
     */
    public int getGroup_id() {
        return group_id;
    }

    /**
     * @param group_id the group_id to set
     */
    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    
}
