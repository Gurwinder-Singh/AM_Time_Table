/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mac.common.messages;

import java.io.Serializable;

/**
 *
 * @author Gurwinder Singh
 */
public class ExtendedMessage implements Serializable{
    private String key;
    private String module;
    private boolean delete;
    private boolean modify;
    private int responseId;
    private String failMessage;

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the module
     */
    public String getModule() {
        return module;
    }

    /**
     * @param module the module to set
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * @return the delete
     */
    public boolean isDelete() {
        return delete;
    }

    /**
     * @param delete the delete to set
     */
    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    /**
     * @return the modify
     */
    public boolean isModify() {
        return modify;
    }

    /**
     * @param modify the modify to set
     */
    public void setModify(boolean modify) {
        this.modify = modify;
    }

    /**
     * @return the responseId
     */
    public int getResponseId() {
        return responseId;
    }

    /**
     * @param responseId the responseId to set
     */
    public void setResponseId(int responseId) {
        this.responseId = responseId;
    }

    /**
     * @return the failMessage
     */
    public String getFailMessage() {
        return failMessage;
    }

    /**
     * @param failMessage the failMessage to set
     */
    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }
   
}
