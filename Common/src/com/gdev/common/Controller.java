/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common;

import java.awt.Frame;
import org.mac.common.messages.TypeDetails;
import org.mac.common.messages.StyleDetails;
import org.mac.common.messages.ItemDetails;
import java.util.Hashtable;
import java.util.Vector;
import com.gdev.common.interfaces.ComponentUpdate;
import com.gdev.common.interfaces.MacTableListener;
import com.gdev.common.interfaces.ReportListener;
import com.gdev.common.interfaces.ResponseListener;
import org.mac.common.messages.CategoryDetails;
import org.mac.common.messages.ExtendedMessage;
import org.mac.common.messages.ITSDetailClass;
import org.mac.common.messages.LedgerDetail;
import org.mac.common.messages.TaxDetailClass;
import com.gdev.common.other.ReportPanel;
import com.gdev.common.other.ReportPanelTopComponent;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Gurwinder Singh
 */
public class Controller implements ReportListener{

    private static Controller mInstance;
    private boolean register = false;
//    private 
    private Hashtable<Long, ItemDetails> itemById;
    private Hashtable<Long, TypeDetails> typeById;
    private Hashtable<Long, StyleDetails> styleById;
    private Hashtable<Long, CategoryDetails> categoryById;
    private Hashtable<Long, LedgerDetail> ledgerById;
    private Hashtable<Long, TaxDetailClass> taxById;
    private Hashtable<String, ItemDetails> itemByName;
    private Hashtable<String, TypeDetails> typeByName;
    private Hashtable<String, StyleDetails> styleByName;
    private Hashtable<String, CategoryDetails> categoryeByName;
    private Hashtable<String, LedgerDetail> ledgerByName;
    private Hashtable<String, ITSDetailClass> ITSList;
    private Hashtable<String, TaxDetailClass> taxByName;
    private Vector itemsInfo;
    private Vector typesInfo;
    private Vector stylesInfo;
    private Vector categoryInfo;
    private Vector taxInfo;
    private Vector<LedgerDetail> ledgerInfo;
    private Hashtable<Integer, ResponseListener> responseListener;
    private Hashtable<Integer, ComponentUpdate> componentUpdateListener;
    private Hashtable<Integer, SubController> subController;
    private Hashtable<String, TopComponent> resportListener;
    private String inv_no;
    private String payment_vou_no;

    public static Controller getDefault() {
        if (mInstance == null) {
            mInstance = new Controller();
        }
        return mInstance;
    }

    public Frame getParent() {
        return WindowManager.getDefault().getMainWindow();
    }

    public Hashtable<Integer, SubController> getSubController() {
        if (subController == null) {
            subController = new Hashtable<Integer, SubController>();
        }
        return subController;
    }

    public void sendMessage(ExtendedMessage message) {
        if (message.getModule().equals("FIN")) {
            subController.get(Constants.FINANCE).handleMessage(message);
        }
    }

    public Hashtable<Integer, ResponseListener> getResponseListener() {
        if (responseListener == null) {
            responseListener = new Hashtable<Integer, ResponseListener>();
        }
        return responseListener;
    }

    public void removeResponseListener(int id) {
        responseListener.remove(id);
    }

    public Hashtable<Integer, ComponentUpdate> getComponentUpdateListener() {
        if (componentUpdateListener == null) {
            componentUpdateListener = new Hashtable<Integer, ComponentUpdate>();
        }
        return componentUpdateListener;
    }

    public void removeComponentUpdateListener(int id) {
        componentUpdateListener.remove(id);
    }

    /**
     * @return the itemById
     */
    public Hashtable<Long, ItemDetails> getItemById() {
        if (itemById == null) {
            setItemById(new Hashtable<Long, ItemDetails>());
        }
        return itemById;
    }

    /**
     * @param itemById the itemById to set
     */
    public void setItemById(Hashtable<Long, ItemDetails> itemById) {
        this.itemById = itemById;
    }

    /**
     * @return the typeById
     */
    public Hashtable<Long, TypeDetails> getTypeById() {
        if (typeById == null) {
            typeById = new Hashtable<Long, TypeDetails>();
        }
        return typeById;
    }

    /**
     * @param typeById the typeById to set
     */
    public void setTypeById(Hashtable<Long, TypeDetails> typeById) {
        this.typeById = typeById;
    }

    /**
     * @return the styleById
     */
    public Hashtable<Long, StyleDetails> getStyleById() {
        if (styleById == null) {
            styleById = new Hashtable<Long, StyleDetails>();
        }
        return styleById;
    }

    /**
     * @param styleById the styleById to set
     */
    public void setStyleById(Hashtable<Long, StyleDetails> styleById) {
        this.styleById = styleById;
    }

    /**
     * @return the categoryById
     */
    public Hashtable<Long, CategoryDetails> getCategoryById() {
        if (categoryById == null) {
            categoryById = new Hashtable<Long, CategoryDetails>();
        }
        return categoryById;
    }

    /**
     * @return the itemByName
     */
    public Hashtable<String, ItemDetails> getItemByName() {
        if (itemByName == null) {
            itemByName = new Hashtable<String, ItemDetails>();
        }
        return itemByName;
    }

    /**
     * @param itemByName the itemByName to set
     */
    public void setItemByName(Hashtable<String, ItemDetails> itemByName) {
        this.itemByName = itemByName;
    }

    /**
     * @return the typeByName
     */
    public Hashtable<String, TypeDetails> getTypeByName() {
        if (typeByName == null) {
            typeByName = new Hashtable<String, TypeDetails>();
        }
        return typeByName;
    }

    /**
     * @param typeByName the typeByName to set
     */
    public void setTypeByName(Hashtable<String, TypeDetails> typeByName) {
        this.typeByName = typeByName;
    }

    /**
     * @return the styleByName
     */
    public Hashtable<String, StyleDetails> getStyleByName() {
        if (styleByName == null) {
            styleByName = new Hashtable<String, StyleDetails>();
        }
        return styleByName;
    }

    /**
     * @param styleByName the styleByName to set
     */
    public void setStyleByName(Hashtable<String, StyleDetails> styleByName) {
        this.styleByName = styleByName;
    }

    /**
     * @param categoryeByName the categoryeByName to set
     */
    public void setCategoryeByName(Hashtable<String, CategoryDetails> categoryeByName) {
        this.categoryeByName = categoryeByName;
    }

    public Hashtable<String, ITSDetailClass> getITSList() {
        if (ITSList == null) {
            ITSList = new Hashtable<String, ITSDetailClass>();
        }
        return ITSList;
    }

    /**
     * @return the itemsInfo
     */
    public Vector getItemsInfo() {
        if (itemsInfo == null) {
            itemsInfo = new Vector();
        }
        return itemsInfo;
    }

    /**
     * @param itemsInfo the itemInfo to set
     */
    public void setItemsInfo(Vector itemsInfo) {
        this.itemsInfo = itemsInfo;
    }

    /**
     * @return the typesInfo
     */
    public Vector getTypesInfo() {
        if (typesInfo == null) {
            typesInfo = new Vector();
        }
        return typesInfo;
    }

    /**
     * @param typesInfo the typesInfo to set
     */
    public void setTypesInfo(Vector typesInfo) {
        this.typesInfo = typesInfo;
    }

    /**
     * @return the stylesInfo
     */
    public Vector getStylesInfo() {
        if (stylesInfo == null) {
            stylesInfo = new Vector();
        }
        return stylesInfo;
    }

    /**
     * @param stylesInfo the styleInfo to set
     */
    public void setStylesInfo(Vector stylesInfo) {
        this.stylesInfo = stylesInfo;
    }

    /**
     * @param categoryById the categoryById to set
     */
    public void setCategoryById(Hashtable<Long, CategoryDetails> categoryById) {
        this.categoryById = categoryById;
    }

    /**
     * @return the categoryeByName
     */
    public Hashtable<String, CategoryDetails> getCategoryeByName() {
        if (categoryeByName == null) {
            categoryeByName = new Hashtable<String, CategoryDetails>();
        }
        return categoryeByName;
    }

    /**
     * @return the categoryInfo
     */
    public Vector getCategoryInfo() {
        if (categoryInfo == null) {
            categoryInfo = new Vector();
        }
        return categoryInfo;
    }

    /**
     * @param categoryInfo the categoryInfo to set
     */
    public void setCategoryInfo(Vector categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    /**
     * @return the ledgerById
     */
    public Hashtable<Long, LedgerDetail> getLedgerById() {
        if (ledgerById == null) {
            ledgerById = new Hashtable<Long, LedgerDetail>();
        }
        return ledgerById;
    }

    /**
     * @param ledgerById the ledgerById to set
     */
    public void setLedgerById(Hashtable<Long, LedgerDetail> ledgerById) {
        this.ledgerById = ledgerById;
    }

    /**
     * @return the ledgerByName
     */
    public Hashtable<String, LedgerDetail> getLedgerByName() {
        if (ledgerByName == null) {
            ledgerByName = new Hashtable<String, LedgerDetail>();
        }
        return ledgerByName;
    }

    /**
     * @param ledgerByName the ledgerByName to set
     */
    public void setLedgerByName(Hashtable<String, LedgerDetail> ledgerByName) {
        this.ledgerByName = ledgerByName;
    }

    /**
     * @return the ledgerInfo
     */
    public Vector<LedgerDetail> getLedgerInfo() {
        if (ledgerInfo == null) {
            ledgerInfo = new Vector<LedgerDetail>();
        }
        return ledgerInfo;
    }

    /**
     * @param ledgerInfo the ledgerInfo to set
     */
    public void setLedgerInfo(Vector<LedgerDetail> ledgerInfo) {
        this.ledgerInfo = ledgerInfo;
    }

    /**
     * @return the taxById
     */
    public Hashtable<Long, TaxDetailClass> getTaxById() {
        if (taxById == null) {
            taxById = new Hashtable<Long, TaxDetailClass>();
        }
        return taxById;
    }

    /**
     * @param taxById the taxById to set
     */
    public void setTaxById(Hashtable<Long, TaxDetailClass> taxById) {
        this.taxById = taxById;
    }

    /**
     * @return the taxByName
     */
    public Hashtable<String, TaxDetailClass> getTaxByName() {
        if (taxByName == null) {
            taxByName = new Hashtable<String, TaxDetailClass>();
        }
        return taxByName;
    }

    /**
     * @param taxByName the taxByName to set
     */
    public void setTaxByName(Hashtable<String, TaxDetailClass> taxByName) {
        this.taxByName = taxByName;
    }

    /**
     * @return the taxInfo
     */
    public Vector getTaxInfo() {
        if (taxInfo == null) {
            taxInfo = new Vector();
        }
        return taxInfo;
    }

    /**
     * @param taxInfo the taxInfo to set
     */
    public void setTaxInfo(Vector taxInfo) {
        this.taxInfo = taxInfo;
    }

    /**
     * @return the register
     */
    public boolean isRegister() {
        return register;
    }

    /**
     * @param register the register to set
     */
    public void setRegister(boolean register) {
        this.register = register;
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

    /**
     * @return the resportListener
     */
    public TopComponent getResportListener(String name) {
        if (resportListener == null) {
            resportListener = new Hashtable<String, TopComponent>();
        }
        return resportListener.get(name);
    }

    /**
     * @param resportListener the resportListener to set
     */
    public void setResportListener(String name, TopComponent component) {
        if (resportListener == null) {
            resportListener = new Hashtable<String, TopComponent>();
        }
        resportListener.put(name, component);
    }

    @Override
    public MacTableListener getReportPanel(String name, ReportPanelTopComponent panel) {

    return subController.get(Constants.FINANCE).getReportPanel(name, panel);
    }
}
