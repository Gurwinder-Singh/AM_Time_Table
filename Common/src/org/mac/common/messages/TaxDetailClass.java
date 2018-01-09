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
public class TaxDetailClass extends ExtendedMessage{
    private long id;
    private boolean percent;
    private String name;
    private BigDecimal value;

    
    /**
     * @return the percent
     */
    public boolean isPercent() {
        return percent;
    }

    /**
     * @param percent the percent to set
     */
    public void setPercent(boolean percent) {
        this.percent = percent;
    }

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
     * @return the value
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

  
    
}
