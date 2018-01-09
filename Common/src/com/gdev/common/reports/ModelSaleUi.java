package com.gdev.common.reports;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import com.gdev.common.Controller;
import org.mac.common.messages.SalePurchaseDetailClass;
import org.mac.common.messages.SalePurchaseMasterClass;

//import com.as.common.SaleDetails;
//import com.as.common.utils.Constants;
public class ModelSaleUi implements JRDataSource {

    private Iterator iterator;
    private Controller controller;
    private SalePurchaseDetailClass currentObject;
    private BigDecimal nRate = new BigDecimal(0);

    @Override
    public Object getFieldValue(JRField field) throws JRException {
        if (field.getName().equals("i_name")) {
            return currentObject.getItemId() >0 ? (setItemName(controller.getItemById().get(currentObject.getItemId()).getName(),
                    controller.getTypeById().get(currentObject.getTypeId()).getName(),
                    controller.getStyleById().get(currentObject.getStyleId()).getName())):null;
        } else if (field.getName().equals("remarks")) {
            return currentObject.getRemarks();
        } else if (field.getName().equals("qty")) {
            return currentObject.getQty();
        } else if (field.getName().equals("rate")) {
            return currentObject.getRate();
        } else if (field.getName().equals("amount")) {
            return currentObject.getAmount();
        }else if (field.getName().equals("tax")) {
            return currentObject.getTax_amount();
        } else {
            return null;
        }
    }

    @Override
    public boolean next() throws JRException {
        if (iterator.hasNext()) {
            currentObject = (SalePurchaseDetailClass) iterator.next();
            return true;
        } else {
            return false;
        }
    }

    public ModelSaleUi(Vector objectList) {
        controller = Controller.getDefault();
        iterator = objectList.listIterator();

    }

    public String setItemName(String item, String type, String style) {
        if (item != null && item.trim().length() > 0) {
            if (style == null || style.trim().equals("General")) {
                style = null;
            }
            if (type != null && !type.trim().equals("General")) {
                return item + " [ " + type + (style == null ? "" : " " + style) + " ]";
            } else {
                return item;
            }
        } else {
            return " ";
        }
    }
}
