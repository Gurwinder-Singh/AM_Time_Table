/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mac.common.messages;

/**
 *
 * @author SourceThink Developers
 */
public class StyleDetails extends ExtendedMessage {

    private long Id;
    private String name;
    private String Remarks;

    /**
     * @return the Id
     */
    public long getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(long Id) {
        this.Id = Id;
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
     * @return the Remarks
     */
    public String getRemarks() {
        return Remarks;
    }

    /**
     * @param Remarks the Remarks to set
     */
    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }
}
