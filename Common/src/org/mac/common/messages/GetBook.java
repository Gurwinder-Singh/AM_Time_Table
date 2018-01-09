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
 * @author Gurwinder Singh
 */
public class GetBook  extends ExtendedMessage{
    private long id;
    private Date fromDate;
    private Date toDate;
    private Vector book;
    private long val;

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
     * @return the fromDate
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the book
     */
    public Vector getBook() {
        return book;
    }

    /**
     * @param book the book to set
     */
    public void setBook(Vector book) {
        this.book = book;
    }

    /**
     * @return the val
     */
    public long getVal() {
        return val;
    }

    /**
     * @param val the val to set
     */
    public void setVal(long val) {
        this.val = val;
    }
    
    
    
}
