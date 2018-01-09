/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdev.common.other;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import com.gdev.common.Constants;
import com.gdev.common.RightClickMenusClass;

/**
 *
 * @author admin
 */
public class RightClickMenusFactory {

    private static RightClickMenusFactory instance;
//    private ClientController controller = ClientController.getDefault();

    public static RightClickMenusFactory getDefault() {
        if (instance == null) {
            instance = new RightClickMenusFactory();
        }
        return instance;
    }

    /*
     * ...........Get Nodes Names for Adminstrator .................
     //     */
//    public String getNodesData(int type) {
//        switch (type) {
//            case ExplorerConstant.BOOK_DETAILS:
//                return "On Challan:"
//                        + "On Purchase:"
//                        + "On Purchase Return:"
//                        + "On Sales:"
//                        + "Pending Despatch Sales:"
//                        + "On Sales Return";
//             default:
//                return getData(type);
//        }
//    }

    /*
     * ...........Get Menu Names for Adminstrator .................
     */
//    public String getData(int type) {
//        String data = MenusDataFactory.getFactory().getData(type);
//        MenusDataFactory.setFactoryNull();
//        return data;
//    }

    /*
     * ...........Get Menu Names for User According to User
     * Permission.................
     */
//    public String getUserPermissionData(Integer formId) {
//        if (controller.getUserType() != null && controller.getUserType().equals("A")) {
//            return getData(formId);
//        } else {
//            UserPermissionData data = controller.getUserPermisssionDataById().get(formId);
//            if (data != null) {
//                return data.getRemarks();
//            } else {
//                return "";
//            }
//        }
//    }

    /*
     * ...........Get OptionList for Node .................
     */
//    public ArrayList getUserPermissionOptionList(int type) {
//        ArrayList arrayList = new ArrayList();
//        String str = getNodesData(type);
//        String[] sArray = str.split(":");
//        if (sArray != null) {
//            for (String s : sArray) {
//                int nodeConstant = getNodeConstant(s);
//                if (controller.isNodePermitted(nodeConstant)) {
//                    arrayList.add(s);
//                }
//            }
//        }
//        return arrayList;
//    }
    /**
     * ...........Add Menu & MenuItems According to User
     * Permission.................
     */
//    public void setPopupMenu(String data, JPopupMenu popupMenu, ActionListener aThis) {
    public void setPopupMenu(int data1, JPopupMenu popupMenu, ActionListener aThis) {
//        String data = getUserPermissionData(type);
        String data = RightClickMenusClass.getDefault().getMenus(data1);
        if (data != null) {
            String[] arr = data.split(";");
            for (int i = 0; i < arr.length; i++) {
                String s = arr[i].trim();
                String parent = "";
                if (s.contains("[") && s.contains("]")) {
                    parent = s.substring(0, s.indexOf("["));
                    String child = s.substring(s.indexOf("[") + 1, s.indexOf("]"));
                    String[] childs = child.split(",");
                    JMenu par = new JMenu(parent.trim());
                    for (int j = 0; j < childs.length; j++) {
                        JMenuItem ch = new JMenuItem(childs[j].trim());
                        ch.addActionListener(aThis);
                        par.add(ch);
                    }
                    popupMenu.add(par);
                } else {
                    parent = s;
                    JMenuItem par = new JMenuItem(parent.trim());
                    par.addActionListener(aThis);
                    popupMenu.add(par);
                }
            }
        }
    }

    /**
     * ...........Add Menu & MenuItems According to User
     * Permission.................
     */
    public ArrayList<String> getMenuList(JPopupMenu popupMenu) {
        ArrayList<String> list = new ArrayList<String>();
        for (MenuElement m : popupMenu.getSubElements()) {
            if (m instanceof JMenu) {
                JMenu par = (JMenu) m;
                for (MenuElement ch : par.getSubElements()) {
                    if (ch instanceof JMenuItem) {
                        list.add(((JMenuItem) ch).getText());
                    }
                }
            } else if (m instanceof JMenuItem) {
                list.add(((JMenuItem) m).getText());
            }
        }
        return list;
    }

    /**
     * ...........Get Action Listeners .................
     */
    public ActionListener[] getActionListeners(JPopupMenu popupMenu) {
        ActionListener[] actionListeners = null;
        for (MenuElement m : popupMenu.getSubElements()) {
            if (m instanceof JMenu) {
            } else if (m instanceof JMenuItem) {
                actionListeners = ((JMenuItem) m).getActionListeners();
            }
        }
        return actionListeners;
    }

    /*
     * ...........Get Reference of Menu Element Accoding to @param@
     * Name.................
     */
    public MenuElement getMenuElement(String name, MenuElement pop) {
        MenuElement[] subElements = pop.getSubElements();
        MenuElement ch = null;
        for (int i = 0; i < subElements.length; i++) {
            if (subElements[i] instanceof JMenu) {
                JMenu child = (JMenu) subElements[i];
                MenuElement[] childSubElements = child.getSubElements();
                if (childSubElements != null && childSubElements.length > 0) {
                    for (int j = 0; j < childSubElements.length; j++) {
                        ch = getMenuElement(name, childSubElements[j]);
                        if (ch != null) {
                            return ch;
                        }
                    }
                }
                if (child.getText().equalsIgnoreCase(name)) {
                    return child;
                }
            } else if (subElements[i] instanceof JMenuItem) {
                JMenuItem child = (JMenuItem) subElements[i];
                MenuElement[] childSubElements = child.getSubElements();
                if (childSubElements != null && childSubElements.length > 0) {
                    for (int j = 0; j < childSubElements.length; j++) {
                        ch = getMenuElement(name, childSubElements[j]);
                        if (ch != null) {
                            return ch;
                        }
                    }
                }
                if (child.getText().equalsIgnoreCase(name)) {
                    return child;
                }
            }
        }
        return null;
    }

    /*
     * ...........Add Menus/Reports as Tree Node in Usermaster
     * Setting.................
     */
//    public ASTreeNode setASTreeNode(int constant, ASTreeNode selNode) {
//        String data = getNodesData(constant);
//
//        if (data != null && !data.trim().isEmpty()) {
//            if (data.contains(":")) {
//                String[] arr = data.split(":");
//                for (int i = 0; i < arr.length; i++) {
//                    String parent = arr[i];
//                    if (!parent.trim().isEmpty()) {
//                        ASTreeNode par = new ASTreeNode(parent);
//                        par.setForeground(Color.RED);
//                        par.setType(ExplorerConstant.PARENT_MENU);
//                        selNode.add(par);
//                        int repCons = getNodeConstant(parent);
//                        par.setNodeConstant(repCons);
//                        setASTreeNode(par.getNodeConstant(), par);
//                    }
//                }
//            } else {
//                String[] arr = data.split(";");
//                for (int i = 0; i < arr.length; i++) {
//                    String s = arr[i];
//                    String parent = "";
//                    if (s.contains("[") && s.contains("]")) {
//                        parent = s.substring(0, s.indexOf("["));
//                        String child = s.substring(s.indexOf("[") + 1, s.indexOf("]"));
//                        String[] childs = child.split(",");
//                        ASTreeNode par = new ASTreeNode(parent);
//                        par.setForeground(Color.BLUE);
//                        par.setType(ExplorerConstant.PARENT_MENU);
//                        for (int j = 0; j < childs.length; j++) {
//                            ASTreeNode ch = new ASTreeNode(childs[j]);
//                            ch.setForeground(Color.BLUE);
//                            ch.setType(ExplorerConstant.CHILD_MENU);
//                            par.add(ch);
//                        }
//                        selNode.add(par);
//                    } else {
//                        parent = s;
//                        ASTreeNode par = new ASTreeNode(parent);
//                        par.setForeground(Color.BLUE);
//                        par.setType(ExplorerConstant.PARENT_MENU);
//                        selNode.add(par);
//                    }
//                }
//            }
//        }
//        return selNode;
//    }
//
//    /*
//     * ...........Set Node Select & deselect According to Permitted
//     * Data.................
//     */
//    public ASTreeNode setUserPermissionDataNodes(ASTreeNode report, String permittedData) {
//        ArrayList<String> args = getNodesData(permittedData);
//        for (String s : args) {
//            ASTreeNode node = getNode(report, s);
//            if (node != null) {
//                node.setSelected(true);
//            }
//        }
//        return report;
//    }
//
//    /*
//     * ...........Making a pattern of nodes that select by the end
//     * user.................
//     */
//    public String setPatternData(int nodeConstant, ASTreeNode node) {
//        StringBuffer saver = new StringBuffer();
//        if (node.isSelected()) {
//            ASTreeNode snode = null;
//            Enumeration children = node.children();
//            String pattern = "";
//
//            while (children.hasMoreElements()) {
//                snode = (ASTreeNode) children.nextElement();
//                if (snode.isSelected()) {
//                    if (snode.getType() == ExplorerConstant.PARENT_MENU) {
//                        ASTreeNode Cnode = null;
//                        Enumeration children1 = snode.children();
//                        if (children1.hasMoreElements()) {
//                            while (children1.hasMoreElements()) {
//                                Cnode = (ASTreeNode) children1.nextElement();
//                                if (Cnode.isSelected() && Cnode.getType() == ExplorerConstant.CHILD_MENU) {
//                                    pattern = pattern + Cnode + ",";
//                                }
//                            }
//                            if (pattern.lastIndexOf(",") > -1) {
//                                pattern = pattern.substring(0, pattern.lastIndexOf(","));
//                                pattern = snode + "[" + pattern + "]";
//                            } else {
//                                pattern = pattern + snode;
//                            }
//                            if (pattern.contains("[")) {
//                                saver.append(pattern).append(";");
//                            }
//                        } else {
//                            pattern = pattern + snode;
//                            saver.append(pattern).append(";");
//                        }
//                        pattern = "";
//                    }
//                }
//            }
//        }
//        return saver.toString();
//
//    }

    /*
     * ........... Return The Number of nodes.................
     */
    private ArrayList getNodesData(String data) {
        ArrayList list = new ArrayList();
        if (data != null && !data.trim().isEmpty()) {
            String[] arr = data.split(";");
            for (int i = 0; i < arr.length; i++) {
                String s = arr[i];
                String parent = "";
                if (s.contains("[") && s.contains("]")) {
                    parent = s.substring(0, s.indexOf("["));
                    String child = s.substring(s.indexOf("[") + 1, s.indexOf("]"));
                    String[] childs = child.split(",");
                    list.add(parent);
                    for (int j = 0; j < childs.length; j++) {
                        list.add(childs[j]);
                    }
                } else {
                    parent = s;
                    list.add(parent);
                }
            }
        }
        return list;
    }

    /*
     * ...........Return The Particular Node in This Report
     * Node.................
     */
//    private ASTreeNode getNode(ASTreeNode report, String s) {
//        ASTreeNode node = null;
//        Enumeration enu = report.breadthFirstEnumeration();
//        while (enu.hasMoreElements()) {
//            // get the node
//            node = (ASTreeNode) enu.nextElement();
//            if (node.toString().equals(s)) {
//                return node;
//            }
//        }
//        return node;
//    }
    public void printData(MenuElement pop) {
        String s = getPrintData(pop);
        System.out.println(s);
        System.out.println("private ActionListener lis = new ActionListener() {");
        System.out.println("@Override");
        System.out.println("public void actionPerformed(ActionEvent e) {");
        System.out.println("if (e.getSource() instanceof JMenuItem && e.getActionCommand() != null){");
        ArrayList<String> n = getNodesData(s);
        if (n.size() > 0) {
            for (int i = 0; i < n.size(); i++) {
                String s1 = n.get(i);
                if (i == 0) {
                    System.out.println("if (e.getActionCommand().equalsIgnoreCase(\"" + s1 + "\")){");
                } else {
                    System.out.println("}else if (e.getActionCommand().equalsIgnoreCase(\"" + s1 + "\")){");
                }
                if (i == n.size() - 1) {
                    System.out.println("}");
                }
            }
        }
        System.out.println("}");
        System.out.println("}");
        System.out.println("};");
    }

    private String getPrintData(MenuElement pop) {
        MenuElement[] subElements = pop.getSubElements();
        StringBuffer saver = new StringBuffer();
        for (int i = 0; i < subElements.length; i++) {
            if (subElements[i] instanceof JMenu) {
                JMenu menu = (JMenu) subElements[i];
                saver.append(menu.getText());
                MenuElement[] menuElements = menu.getSubElements();
                if (menuElements.length > 0) {
                    for (int j = 0; j < menuElements.length; j++) {
                        if (j == 0) {
                            saver.append("[");
                        }
                        saver.append(getText(menuElements[j]));
                        if (j == menuElements.length - 1) {
                            saver.append("]");
                        }
                    }
                }
                saver.append(";");
            } else if (subElements[i] instanceof JMenuItem) {
                JMenuItem menu = (JMenuItem) subElements[i];
                saver.append(menu.getText());
                MenuElement[] menuElements = menu.getSubElements();
                if (menuElements.length > 0) {
                    for (int j = 0; j < menuElements.length; j++) {
                        if (j == 0) {
                            saver.append("[");
                        }
                        saver.append(getText(menuElements[j]));
                        if (j == menuElements.length - 1) {
                            saver.append("]");
                        }
                    }
                }
                saver.append(";");
            }
        }
        return saver.toString();
    }

    private String getText(MenuElement e) {
        if (e instanceof JMenu) {
            return (((JMenu) e).getText()) + ",";
        } else if (e instanceof JMenuItem) {
            return ((JMenuItem) e).getText() + ",";
        } else if (e instanceof JPopupMenu) {
            String s = "";
            if (e.getSubElements().length > 0) {
                MenuElement[] menuElements = e.getSubElements();
                for (int i = 0; i < menuElements.length; i++) {
                    if (i == menuElements.length - 1) {
                        s += ((JMenuItem) menuElements[i]).getText() + "";
                    } else {
                        s += ((JMenuItem) menuElements[i]).getText() + ",";
                    }
                }
            }
            return s;
        }
        return "";
    }
//    private int getNodeConstant(String type) {
//        if (type.equalsIgnoreCase("On Challan")) {
////            return ExplorerConstant.ON_CHALLAN;
//            return ExplorerConstant.CHALLAN_BO0K;
//        } else if (type.equalsIgnoreCase("On Purchase")) {
//            return ExplorerConstant.ON_PURCHASE;
//        } else if (type.equalsIgnoreCase("On Purchase Return")) {
//            return ExplorerConstant.ON_PURCHASE_RETURN;
//        } else if (type.equalsIgnoreCase("On Sales")) {
//            return ExplorerConstant.ON_SALES;
//       } else if (type.equalsIgnoreCase("Pending Despatch Sales")) {
//            return ExplorerConstant.SALES_DESPATCH_REPORT;
//         } else if (type.equalsIgnoreCase("On Sales Return")) {
//            return ExplorerConstant.ON_SALES_RETURN;
//        }
//        return TestTree.getDefault().getNodeConstantForUserSetting(type);
//    }
}
