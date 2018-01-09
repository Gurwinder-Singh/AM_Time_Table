/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MacTable.java
 *
 *
 */
package com.gdev.common.other;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import com.gdev.common.interfaces.ITotalTableFooter;
import com.gdev.common.interfaces.TextFieldCellEditor;
import com.gdev.common.interfaces.MacTableImplementPanelClass;
import java.awt.*;
import java.io.File;
import java.util.*;
import javax.swing.*;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import sun.swing.SwingUtilities2;

/**
 * Re
 *
 * @author SourceThink Dev
 */
public class MacTable extends javax.swing.JPanel implements ComponentListiner {

    private static final long serialVersionUID = -1648226755833848987L;
    private TableModel model;
    private TableRowSorter<TableModel> sorter;
    private MacTableImplementPanelClass repListner = null;
    private Vector reportListeners = new Vector();
    private JPopupMenu popupMenu = new JPopupMenu();
    private final String HIDE = "Hide(Del)";
    private final String CLEAR = "UnHide(Esc)";
    private final String DEFINE_FLAG = "Define Flag";
    private final String REMOVE_FLAG = "Remove Flag";
    private RowFilter<TableModel, Object> userFilter = null;
    private ArrayList<Integer> hidden = new ArrayList<Integer>();
    private ArrayList<TableColumn> columns = new ArrayList<TableColumn>();
    private ArrayList<String> flag = new ArrayList<String>();
    private JPopupMenu columnsPopupMenu = new JPopupMenu();
    private JPopupMenu flagPopupMenu = new JPopupMenu();
    private JPopupMenu printPopupMenue = new JPopupMenu();
    private JButton columnSelection;
    private Boolean[] visible;
    private String pro_value;
    private Boolean[] module_columns = null;
    private ColumnHeaderAdapter columnHeaderAdapter;
    private int stretchWidth;
    private int headerheight = 21;
    private int defaultVisibleColumns = 15;
    private Hashtable<String, String> _cols = null;
    private Vector flagData = null;
//    private ExportToExcel export;
    private MenuScroller Scroller = null;
    private ArrayList<Component> currComponent = new ArrayList<Component>();
    private boolean ToolTipForTable = true;

    /**
     * Creates new form MacTable
     */
    public MacTable() {
        initComponents();
//        dropOptionComponent1.setComponentListner(this, true);
//        dropOptionComponent2.setComponentListner(this, false);
////        generatePopUpMenus(false);
//        btnRefresh.setIcon(IconFactoryClass.setIconForButton(IconFactoryClass.getRefreshIcon(), 20, 20));
        btnRefresh.setToolTipText("Refresh Table");
//        btnRefresh.setContentAreaFilled(false);

//        btnExpotExcel.setIcon(IconFactoryClass.getExportToExcelIcon(25, 20));
        btnExpotExcel.setToolTipText("Export To Excel(Ctrl+E)");
        btnExpotExcel.setContentAreaFilled(false);
        btnExpotExcel.setVisible(false);
        btnExpotExcel.setEnabled(false);

//        btnAdd.setIcon(IconFactoryClass.setIconForButton(IconFactoryClass.getAddPictureIcon(), 20, 20));
        btnAdd.setToolTipText("Add New");
//        btnAdd.setContentAreaFilled(false);

//        btnChart.setIcon(IconFactoryClass.setIconForButton(IconFactoryClass.getChartPictureIcon(), 20, 20));
        btnChart.setToolTipText("Show Chart");
        btnChart.setContentAreaFilled(false);
        btnChart.setVisible(false);
        btnChart.setEnabled(false);

//        btnCrossTab.setIcon(IconFactoryClass.getCrossTabPictureIcon());
        btnCrossTab.setToolTipText("Create Tabular Report");
        btnCrossTab.setContentAreaFilled(false);
        btnCrossTab.setVisible(false);
        btnCrossTab.setEnabled(false);

//        btnClear.setIcon(IconFactoryClass.setIconForButton(IconFactoryClass.getClearIcon(), 20, 20));
        btnClear.setToolTipText("Clear");
//        btnClear.setContentAreaFilled(false);

        MouseListener popupListener = new PopupListener();
        table.addMouseListener(popupListener);
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        columnHeaderAdapter = new ColumnHeaderAdapter();
        table.getTableHeader().addMouseMotionListener(columnHeaderAdapter);
        table.getTableHeader().addMouseListener(columnHeaderAdapter);
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jScrollPane1.setViewportBorder(new TableFooter(table));


        columnSelection = new JButton("C");
        columnSelection.setMargin(new java.awt.Insets(2, 1, 2, 1));

        jScrollPane1.setCorner(JScrollPane.UPPER_RIGHT_CORNER, columnSelection);

        columnSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                columnsPopupMenu.show(columnSelection, 5, 5);
            }
        });
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateTotals();
            }
        });

        value.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }
        });

        KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK, false);
        table.registerKeyboardAction(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "Copy", copy, JComponent.WHEN_FOCUSED);


        KeyStroke k1 = KeyStroke.getKeyStroke(KeyEvent.VK_H,
                InputEvent.CTRL_MASK);
        ActionListener HideListener = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hideActionPerformed();
            }
        };
        table.registerKeyboardAction(HideListener, k1, WHEN_IN_FOCUSED_WINDOW);



        KeyStroke k2 = KeyStroke.getKeyStroke(KeyEvent.VK_P,
                InputEvent.ALT_MASK, false);
        ActionListener PrintListener = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    printTable(((JCheckBoxMenuItem) printPopupMenue.getComponent(0)).isSelected());
                } catch (Exception e) {
                    e.printStackTrace();
                    MessageDisplay.showErrorDialog(table.getParent(), "Error in table printing.");
                }
            }
        };
        table.registerKeyboardAction(PrintListener, k2, WHEN_IN_FOCUSED_WINDOW);

        KeyStroke k3 = KeyStroke.getKeyStroke(KeyEvent.VK_U,
                InputEvent.CTRL_MASK);
        ActionListener UnHideListener = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unHideActionPerformed();
            }
        };
        table.registerKeyboardAction(UnHideListener, k3, WHEN_IN_FOCUSED_WINDOW);

        KeyStroke k4 = KeyStroke.getKeyStroke(KeyEvent.VK_E,
                InputEvent.CTRL_MASK);
        ActionListener ExportListener = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    openExportToExcelDailog();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        table.registerKeyboardAction(ExportListener, k4, WHEN_IN_FOCUSED_WINDOW);

        KeyStroke k5 = KeyStroke.getKeyStroke(KeyEvent.VK_CONTEXT_MENU,
                InputEvent.CTRL_MASK);
        ActionListener ContextMenuListener = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    showPopup(evt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        table.registerKeyboardAction(ContextMenuListener, k5, WHEN_IN_FOCUSED_WINDOW);

        KeyStroke k6 = KeyStroke.getKeyStroke(KeyEvent.VK_F,
                InputEvent.CTRL_MASK);
        ActionListener TextFocus = new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    value.requestFocus();
                    value.selectAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        table.registerKeyboardAction(TextFocus, k6, WHEN_IN_FOCUSED_WINDOW);
//
//        table.addKeyListener(new KeyListener() {
//
//            @Override
//            public void keyTyped(KeyEvent e) {
//                getKeyEvent(e);
//            }
//
//            @Override
//            public void keyPressed(KeyEvent e) {
//                getKeyEvent(e);
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//                getKeyEvent(e);
//            }
//        });
        value.setToolTipText(getToolTipForTextField());
        addKeyListener();
    }

    private void getKeyEvent(KeyEvent e) {
        System.out.println(e);
//        new KeyEvent(table, KeyEvent.KEY_TYPED, new Date().getTime(), KeyEvent.KEY_FIRST,
//                KeyEvent.VK_DOWN, '\uffff', 1);
    }

    private void showPopup(java.awt.event.ActionEvent event) {
        try {
            int row = table.getSelectedRow();
            Point result = null;
            if (row > -1) {
                Rectangle rect = table.getCellRect(row, 0, true);
                result = new Point(rect.x + 40, rect.y);
            } else {
                row = 0;
                result = table.getMousePosition();
                if (result == null) {
                    Rectangle rect = table.getVisibleRect();
                    result = new Point(rect.x + 40, rect.y);
                }
            }
            if (!table.getVisibleRect().contains(result)) {
                Rectangle rect = table.getVisibleRect();
                result = new Point(rect.x + 40, rect.y);
            }

            MouseEvent leftEvent = new MouseEvent(table, MouseEvent.MOUSE_PRESSED,
                    event.getWhen(), event.getModifiers(),
                    result.x, result.y,
                    1,
                    false,
                    MouseEvent.BUTTON1);

            MouseEvent rightEvent = new MouseEvent(table, MouseEvent.MOUSE_PRESSED,
                    event.getWhen(), event.getModifiers(),
                    result.x, result.y,
                    1,
                    true,
                    MouseEvent.BUTTON3);
            //            popupMenu.show(table, result.x, result.y);
            table.getSelectionModel().setSelectionInterval(table.rowAtPoint(result), table.rowAtPoint(result));
            SwingUtilities2.adjustFocus(table);
            table.getSelectionModel().setValueIsAdjusting(true);
            table.getColumnModel().getSelectionModel().setValueIsAdjusting(true);

            MouseListener[] mouseListeners = table.getMouseListeners();
            PopupListener popuplis = null;
            for (MouseListener lis : mouseListeners) {
                lis.mouseEntered(leftEvent);
                lis.mousePressed(leftEvent);
                if (lis instanceof PopupListener) {
                    popuplis = (PopupListener) lis;
                }
            }
            if (popuplis != null) {
                popuplis.mousePressed(rightEvent);
            }
            update();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setFlagOptions() {
        deafultFlag();
        setCreatedFlag();
        JMenuItem btnShow = new JMenuItem("Show");
        btnShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flagPopupMenu.setVisible(false);
                saveFlagAction();
            }

            private void saveFlagAction() {
                printPopupMenue.setVisible(false);
                showFlagEntry(showFlagOptions());
            }
        });
        flagPopupMenu.add(btnShow);
    }

    public void setPrintOption() {
        printPopupMenue.removeAll();

        final JCheckBoxMenuItem hPrinting = new JCheckBoxMenuItem("Horizontal Printing", false);
        hPrinting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printPopupMenue.setVisible(true);
            }
        });
        printPopupMenue.add(hPrinting);

        final JCheckBoxMenuItem wWrap = new JCheckBoxMenuItem("Word Wrap", false);
        wWrap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printPopupMenue.setVisible(true);
            }
        });

        printPopupMenue.add(wWrap);

        final JCheckBoxMenuItem pagination = new JCheckBoxMenuItem("Pagination", false);
        pagination.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printPopupMenue.setVisible(true);
            }
        });
        printPopupMenue.add(pagination);

        final JCheckBoxMenuItem compAddress = new JCheckBoxMenuItem("Company Address", false);
        compAddress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Properties myProp = ReadPropertiesFile.getDefault().setSmtableProperties();;
//                ReadPropertiesFile.getDefault().saveSmtableProperties(myProp, new Boolean(compAddress.isSelected()).toString(), "Company_Address");
                printPopupMenue.setVisible(false);
            }
        });
        printPopupMenue.add(compAddress);

        final JCheckBoxMenuItem printTotal = new JCheckBoxMenuItem("Print Total", true);
        printTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Properties myProp = ReadPropertiesFile.getDefault().setSmtableProperties();;
//                ReadPropertiesFile.getDefault().saveSmtableProperties(myProp, new Boolean(printTotal.isSelected()).toString(), "Print_Total");
                printPopupMenue.setVisible(false);
            }
        });
        printPopupMenue.add(printTotal);

        final JCheckBoxMenuItem displayFontSize = new JCheckBoxMenuItem("Display Font Size", false);
        displayFontSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Properties myProp = ReadPropertiesFile.getDefault().setSmtableProperties();;
//                ReadPropertiesFile.getDefault().saveSmtableProperties(myProp, new Boolean(displayFontSize.isSelected()).toString(), "Display_Font_Size");
                printPopupMenue.setVisible(true);
            }
        });
        printPopupMenue.add(displayFontSize);

        final JMenuItem btnApply = new JMenuItem("Apply");
        btnApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Properties myProp = ReadPropertiesFile.getDefault().setSmtableProperties();;
//                ReadPropertiesFile.getDefault().saveSmtableProperties(myProp, setPtintInfo(), "SMTablePrint");
                printPopupMenue.setVisible(false);
            }
        });
        printPopupMenue.add(btnApply);
//        Properties tabProp = ReadPropertiesFile.getDefault().setSmtableProperties();
//        Boolean[] value = getColumsVisibility(tabProp, "SMTablePrint");
//        if (value != null) {
//            hPrinting.setSelected(value[0] != null ? value[0] : false);
//            wWrap.setSelected(value[1] != null ? value[1] : false);
//            pagination.setSelected(value[2] != null ? value[2] : false);
//            displayFontSize.setSelected(value[5] != null ? value[5] : false);
//
//        }
//        String Company_Address = ReadPropertiesFile.getDefault().setSmtableProperties().getProperty("Company_Address", Boolean.FALSE.toString());
//        compAddress.setSelected(Company_Address != null ? Boolean.parseBoolean(Company_Address) : false);
//        String Print_Total = ReadPropertiesFile.getDefault().setSmtableProperties().getProperty("Print_Total", Boolean.TRUE.toString());
//        printTotal.setSelected(Print_Total != null ? Boolean.parseBoolean(Print_Total) : true);
//        String Display_Font_Size = ReadPropertiesFile.getDefault().setSmtableProperties().getProperty("Display_Font_Size", Boolean.TRUE.toString());
//        displayFontSize.setSelected(Display_Font_Size != null ? Boolean.parseBoolean(Display_Font_Size) : true);
//        if (value == null) {
//            JCheckBoxMenuItem hPrinting = new JCheckBoxMenuItem("Horizontal Printing", value[0]);
//            hPrinting.addActionListener(new ActionListener() {
//
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    printPopupMenue.setVisible(true);
//                }
//            });
//            printPopupMenue.add(hPrinting);
//
//            JCheckBoxMenuItem wWrap = new JCheckBoxMenuItem("word Wrap", value[1]);
//            wWrap.addActionListener(new ActionListener() {
//
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    printPopupMenue.setVisible(true);
//                }
//            });
//            printPopupMenue.add(wWrap);
//
//            JCheckBoxMenuItem pagination = new JCheckBoxMenuItem("Pagination", value[2]);
//            pagination.addActionListener(new ActionListener() {
//
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    printPopupMenue.setVisible(true);
//                }
//            });
//            printPopupMenue.add(pagination);
//
//            String property = ReadPropertiesFile.getDefault().setSmtableProperties().getProperty("Company_Address", Boolean.FALSE.toString());
//            final JCheckBoxMenuItem compAddress = new JCheckBoxMenuItem("Company Address", Boolean.parseBoolean(property));
//            compAddress.addActionListener(new ActionListener() {
//
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    Properties myProp = ReadPropertiesFile.getDefault().setSmtableProperties();;
//                    ReadPropertiesFile.getDefault().saveSmtableProperties(myProp, new Boolean(compAddress.isSelected()).toString(), "Company_Address");
//                    printPopupMenue.setVisible(false);
//                }
//            });
//            printPopupMenue.add(compAddress);
//
//            String property = ReadPropertiesFile.getDefault().setSmtableProperties().getProperty("Print_Total", Boolean.TRUE.toString());
//            final JCheckBoxMenuItem printTotal = new JCheckBoxMenuItem("Print Total", Boolean.parseBoolean(property));
//            printTotal.addActionListener(new ActionListener() {
//
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    Properties myProp = ReadPropertiesFile.getDefault().setSmtableProperties();;
//                    ReadPropertiesFile.getDefault().saveSmtableProperties(myProp, new Boolean(printTotal.isSelected()).toString(), "Print_Total");
//                    // printPopupMenue.setVisible(false);
//                }
//            });
//            printPopupMenue.add(printTotal);
//        }
    }

    private String showFlagOptions() {
        String value = "";
        Component[] comp = flagPopupMenu.getComponents();
        for (int i = 0; i < comp.length; i++) {
            if (comp[i] instanceof JCheckBoxMenuItem) {
                if ( //                ((JCheckBoxMenuItem) comp[i]).getText().equals("Show All")
                        //                        || ((JCheckBoxMenuItem) comp[i]).getText().equals("Un Flagged")
                        //                        || ((JCheckBoxMenuItem) comp[i]).getText().equals("Flagged Entry"))
                        ((JCheckBoxMenuItem) comp[i]).isSelected()) {
                    return ((JCheckBoxMenuItem) comp[i]).getText();
                }
            }
        }
        return "";
    }
//        for (int i = 0; i < flagPopupMenu.getComponentCount() - 1; i++) {
//            if(((JCheckBoxMenuItem) flagPopupMenu.getComponent(i)) instanceof JCheckBoxMenuItem){
//            if (((JCheckBoxMenuItem) flagPopupMenu.getComponent(i)).isSelected()
//                    && (((JCheckBoxMenuItem) flagPopupMenu.getComponent(i)).getName().equals("Show All")
//                    || ((JCheckBoxMenuItem) flagPopupMenu.getComponent(i)).getName().equals("Un Flagged"))) {
//                return ((JCheckBoxMenuItem) flagPopupMenu.getComponent(i)).getName();
//            }
//            }
//        }

    public String setPtintInfo() {
        String value = "";
        for (int i = 0; i < printPopupMenue.getComponentCount() - 1; i++) {
            if (i == 0) {
                value = "" + ((JCheckBoxMenuItem) printPopupMenue.getComponent(i)).isSelected();
            } else {
                value = value + "," + ((JCheckBoxMenuItem) printPopupMenue.getComponent(i)).isSelected();
            }
        }
        return value;
    }

    protected void setColumnWidthInBundle() {
        String value = null;
//        Properties myProp = ReadPropertiesFile.getDefault().setSmtableProperties();
        visible = null;//getColumsVisibility(myProp, getreportListner().getParentName());

        if (visible == null) {
            updateColumnView();
            return;
//            saveProperties(loadFile(), myProp, pro_value, getreportListner().getParentName());
//            ReadPropertiesFile.getDefault().saveSmtableProperties(myProp, pro_value, getreportListner().getParentName());
//            visible = getColumsVisibility(myProp, getreportListner().getParentName());
        }
        ArrayList visibleColumns = new ArrayList();
        for (int i = 0; i < visible.length; i++) {
            if (visible[i]) {
                visibleColumns.add(visible[i]);
            }
        }
        for (int i = 0; i < visibleColumns.size(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                value = "" + column.getWidth();
            } else {
                value = value + "," + column.getWidth();
            }

            TableCellRenderer cellRenderer = column.getCellRenderer();
            if (cellRenderer != null) {
//                if (cellRenderer.getClass().equals(DateCellRenderer.class)) {
//                    ReadPropertiesFile.getDefault().setRendererMinimumWidth("DateCellRenderer", "" + column.getWidth());
//                } else if (cellRenderer.getClass().equals(AmountDrCrCellRenderer.class)) {
//                    ReadPropertiesFile.getDefault().setRendererMinimumWidth("AmountDrCrCellRenderer", "" + column.getWidth());
//                } else if (cellRenderer.getClass().equals(ImageCellRender.class)) {
//                    ReadPropertiesFile.getDefault().setRendererMinimumWidth("ImageCellRender", "" + column.getWidth());
                if (cellRenderer.getClass().equals(SerialNumberClass.class)) {
//                    ReadPropertiesFile.getDefault().setRendererMinimumWidth("SerialNumberClass", "" + column.getWidth());
                }
            }
        }
//        ReadPropertiesFile.getDefault().saveSmtableProperties(myProp, value, getreportListner().getParentName() + "_WIDTH");
    }

    protected void setColumnNamesInBundle() {
        String value = null;
//        Properties myProp = ReadPropertiesFile.getDefault().setSmtableProperties();
//        visible = getColumsVisibility(myProp, getreportListner().getParentName());

        if (visible == null) {
            updateColumnView();
            return;
//            saveProperties(loadFile(), myProp, pro_value, getreportListner().getParentName());
//            ReadPropertiesFile.getDefault().saveSmtableProperties(myProp, pro_value, getreportListner().getParentName());
//            visible = getColumsVisibility(myProp, getreportListner().getParentName());
        }
        ArrayList visibleColumns = new ArrayList();
        for (int i = 0; i < visible.length; i++) {
            if (visible[i]) {
                visibleColumns.add(visible[i]);
            }
        }
        for (int i = 0; i < visibleColumns.size(); i++) {
            if (i == 0) {
                value = "" + table.getColumnModel().getColumn(i).getHeaderValue().toString();
            } else {
                value = value + "," + table.getColumnModel().getColumn(i).getHeaderValue().toString();
            }
        }
//        saveProperties(loadFile(), myProp, value, getreportListner().getParentName() + "_COL_NAMES");
//        ReadPropertiesFile.getDefault().saveSmtableProperties(myProp, value, getreportListner().getParentName() + "_COL_NAMES");
    }

    private void getColumnWidthFromBundle(String key) {
//        try {
////            Properties tabProp = ReadPropertiesFile.getDefault().setSmtableProperties();
//            String[] ColWidth = null;
//            key = key + "_WIDTH";
//            visible = getColumsVisibility(tabProp, getreportListner().getParentName());
//
//            if (tabProp.getProperty(key) != null) {
//                ColWidth = tabProp.getProperty(key).split(",");
//                for (int i = 0; i < ColWidth.length; i++) {
//                    table.getColumnModel().getColumn(i).setPreferredWidth(Integer.parseInt(ColWidth[i]));
//                }
//            }
////            else {
////            if (ClientController.getDefault().isStretech()) {
//                setHorizontalScrollBar();
////            }
////            }
//
//        } catch (ArrayIndexOutOfBoundsException ex) {
//            ex.printStackTrace();
//            setHorizontalScrollBar();
//            updateColumnView();
////            saveProperties(loadFile(), loadProperties(loadFile()), pro_value, getreportListner().getParentName());
////            ReadPropertiesFile.getDefault().saveSmtableProperties(ReadPropertiesFile.getDefault().setSmtableProperties(),
////                    pro_value, getreportListner().getParentName());
//            setColumnWidthInBundle();
//        }
    }

    private void getColumnNamesFromBundle(String key) {
//        try {
////            Properties tabProp = ReadPropertiesFile.getDefault().setSmtableProperties();
//            String[] ColNames = null;
//            key = key + "_COL_NAMES";
//            visible = getColumsVisibility(tabProp, getreportListner().getParentName());
//
//            if (tabProp.getProperty(key) != null) {
//                ColNames = tabProp.getProperty(key).split(",");
//                for (int i = 0; i < ColNames.length; i++) {
//                    if (ColNames[i] != null && !ColNames[i].isEmpty()) {
//                        for (int j = 0; j < table.getColumnModel().getColumnCount(); j++) {
//                            TableColumn col = table.getColumnModel().getColumn(j);
//                            if (col != null && ColNames[i] != null && ColNames[i].equals(col.getHeaderValue().toString())) {
//                                table.getColumnModel().moveColumn(j, i);
//                                break;
//                            }
//                        }
//                    }
//
//                }
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            updateColumnView();
////            saveProperties(loadFile(), loadProperties(loadFile()), pro_value, getreportListner().getParentName());
//            ReadPropertiesFile.getDefault().saveSmtableProperties(ReadPropertiesFile.getDefault().setSmtableProperties(), pro_value, getreportListner().getParentName());
//            setColumnNamesInBundle();
//        }
    }

    public void printTable(boolean horz) {
//        try {
//            ReportExecutor executeReport = new ReportExecutor();
////            TableDataSourse ds = new TableDataSourse(this.getModelData(), this.getTable());
//            Vector data = getSelectedModelData();
//            TableDataSourse ds = new TableDataSourse(data, this.getTable());
//            executeReport.setPagination(((JCheckBoxMenuItem) printPopupMenue.getComponent(2)).isSelected());
//            JasperPrint printableReport = executeReport.executeTableReport(this, getreportListner().getReportTitle(), ds,
//                    !horz, !((JCheckBoxMenuItem) printPopupMenue.getComponent(1)).isSelected(),
//                    ((JCheckBoxMenuItem) printPopupMenue.getComponent(5)).isSelected(),
//                    controller.getCompInfo().getNameOfCompany(), data);
//
//            ASReportViewer viewer = new ASReportViewer(controller.canPrint("R"), controller.canSave("R"), printableReport);
//            viewer.setTitle(getreportListner().getReportTitle());
//            viewer.setVisible(true);
////            viewer.setFitPageZoomRatio();
//            viewer.setPageSize(Constants.FIT_WIDTH);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void generatePopUpMenus(boolean withDisplay) {
        if (popupMenu != null) {
            popupMenu.removeAll();
        }
        JMenuItem menuItem = new JMenuItem(HIDE);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideActionPerformed();
            }
        });
        popupMenu.add(menuItem);

        menuItem = new JMenuItem(CLEAR);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unHideActionPerformed();
            }
        });
        popupMenu.add(menuItem);


        //flag options
//        menuItem = new JMenuItem(DEFINE_FLAG);
//        menuItem.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
////                if (ClientController.getDefault().getUserType().equals("A")
////                        || controller.getUserSettings().isCan_flag()) {
//                    defineFlagActionPerformed();
////                } else {
////                    MessageDisplay.showErrorDialog(null, "Not Authorised.");
////                    return;
////                }
//            }
//        });
//        popupMenu.add(menuItem);
//        menuItem = new JMenuItem(REMOVE_FLAG);
//        menuItem.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                deleteFlagActionPerformed();
//            }
//        });
////        if (controller.getUserType().equals("A")) {
//            popupMenu.add(menuItem);
//        }
        popupMenu.add(new JSeparator());

        if (withDisplay) {
            setImagePopup();
        }
    }

    public void setImagePopup() {
//
//        JMenu imageOptions = new JMenu("Show Image");
//        JMenuItem menuItem = new JMenuItem("Item");
//        menuItem.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (getDataVector().firstElement() instanceof ItemInformation) {
//                    ItemInformation inf = (ItemInformation) getDataVector().elementAt(getSelectedRow());
//                    CreateItem itm = controller.getItemInfoById().get(inf.getItem_id());
//                    ADCImageDisplay.showImage(itm.getImgPath());
//                }
//            }
//        });
//        imageOptions.add(menuItem);
//
//        JMenuItem menuType = new JMenuItem("Type");
//        menuType.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (getDataVector().firstElement() instanceof ItemInformation) {
//                    ItemInformation inf = (ItemInformation) getDataVector().elementAt(getSelectedRow());
//                    TypeDetails typ = controller.getTypesInfoById().get(inf.getType_id());
//                    ADCImageDisplay.showImage(typ.getImg());
//                }
//            }
//        });
//        imageOptions.add(menuType);
//
//        JMenuItem menuStyle = new JMenuItem("Style");
//        menuStyle.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (getDataVector().firstElement() instanceof ItemInformation) {
//                    ItemInformation inf = (ItemInformation) getDataVector().elementAt(getSelectedRow());
//                    StyleDetails sty = controller.getStylesInfoById().get(inf.getStyle_id());
//                    ADCImageDisplay.showImage(sty.getImg());
//                }
//            }
//        });
//        imageOptions.add(menuStyle);
//        popupMenu.add(imageOptions);
//        popupMenu.add(new JSeparator());
    }

    public void hideActionPerformed() {
        ListSelectionModel selection = table.getSelectionModel();
        for (int i = selection.getMinSelectionIndex(); i <= selection.getMaxSelectionIndex(); i++) {
            if (selection.isSelectedIndex(i)) {
                hidden.add(sorter.convertRowIndexToModel(i));
            }
        }
        //If hidden size is equal to data size then no data is selected
        if (hidden.size() != getModelData().size()) {
            if (getModelData().size() == getDataVector().size() && selection.getMaxSelectionIndex() > -1) {
                table.changeSelection(table.convertRowIndexToView(selection.getMaxSelectionIndex()), 0, true, false);
            } else {
                try {
                    table.changeSelection(selection.getMaxSelectionIndex() + 1, 0, true, false);
                } catch (Exception e) {
                }
            }
        }
        update();
    }

    public void unHideActionPerformed() {
        hidden.clear();
        update();
    }

    public void defineFlagActionPerformed() {
//        AllocateFlagDialog dialog = new AllocateFlagDialog(allocateFlagDialogListener);
//        dialog.setParent(this);
//        dialog.setVisible(true);
//        dialog.setLocation(Utility.getCenterLocation(dialog.getSize()));
////        dialog.setModal(true);
//        return;
    }

    public void deleteFlagActionPerformed() {
//        if (allocateFlagDialogListener != null) {
//            allocateFlagDialogListener.removeFlag();
//        }
    }

    private void showFlagEntry(String sel) {
//        if (flagData == null || flagData.size() <= 0) {
//            flagData = new Vector();
//            flagData.addAll(model.dataVector);
//        }
//        if (model.dataVector == null) {
//            model.dataVector = new Vector();
//        } else {
//            model.dataVector.clear();
//        }
//        if (sel.equals("Flagged Entry")) {
//
//            for (int i = 0; i < flagData.size(); i++) {
//                Object row = (Object) flagData.elementAt(i);
//                if (row instanceof ExtendedMessage) {
//                    ExtendedMessage data = (ExtendedMessage) row;
//                    if (data.getFlag_id() > 0) {
//                        model.dataVector.add(row);
//                    }
//                }
//
//            }
//            setTableModel(model.dataVector);
//            //flagData=null;
//            return;
//        } else if (sel.equals("Show All")) {
//            model.dataVector.addAll(flagData);
//            setTableModel(model.dataVector);
//            model.dataVector = flagData;
//            flagData = null;
//            return;
//        } else if (sel.equals("Un Flagged")) {
//            for (int i = 0; i < flagData.size(); i++) {
//                Object row = (Object) flagData.elementAt(i);
//                if (row instanceof ExtendedMessage) {
//                    ExtendedMessage data = (ExtendedMessage) row;
//                    if (data.getFlag_id() == 0) {
//                        model.dataVector.add(row);
//                    }
//                }
//            }
//            setTableModel(model.dataVector);
////                 flagData=null;
//            return;
//        } else {
//            for (int i = 0; i < getFlagIds().size(); i++) {
//                if (getFlagIds().get(i) != null) {
//                    CreateType type = controller.getFlagInfoByName().get(getFlagIds().get(i).trim());
//                    for (int j = 0; j < flagData.size(); j++) {
//                        Object row = (Object) flagData.elementAt(j);
//                        if (row instanceof ExtendedMessage) {
//                            ExtendedMessage data = (ExtendedMessage) row;
//                            if (data.getFlag_id() == type.getId()) {
//                                model.dataVector.add(row);
//                            }
//                        }
//
//
//                    }
//                }
//            }
//            setTableModel(model.dataVector);
////                 flagData=null;
//            return;
//        }
    }

    private void buildChart() {
//        if (chartdialog == null) {
//            chartdialog = new BuildChartDialog(getModelData(), getTable(), getreportListner().getReportTitle());
//            chartdialog.addWindowListener(new WindowAdapter() {
//
//                @Override
//                public void windowClosed(WindowEvent e) {
//                    chartdialog = null;
//                }
//            });
//        }
//        chartdialog.setKey("Chart");
//        chartdialog.setParent(this);
//        chartdialog.setVisible(true);
//        return;
    }

    private void buildCrossTab() {
//        if (crossTabDialog == null) {
//            crossTabDialog = new BuildCrossTabDialog(getModelData(), getTable(), getreportListner().getReportTitle(), ((JCheckBoxMenuItem) printPopupMenue.getComponent(3)).isSelected());
//            crossTabDialog.addWindowListener(new WindowAdapter() {
//
//                @Override
//                public void windowClosed(WindowEvent e) {
//                    crossTabDialog = null;
//                }
//            });
//            crossTabDialog.setKey("CrossTab");
//            crossTabDialog.setParent(this);
//            crossTabDialog.setVisible(true);
//            return;
//        }
    }

    private void setHorizontalScrollBar() {
        FontMetrics fm = table.getFontMetrics(new java.awt.Font("Tahoma", 1, 11));
        stretchWidth = 0;
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            int w = SwingUtilities.computeStringWidth(fm, table.getColumnModel().getColumn(i).getHeaderValue().toString());

            if (w < 75) {
                w = 80;
            }
            table.getColumnModel().getColumn(i).setPreferredWidth(w);
            stretchWidth = stretchWidth + w;
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        if (table.getSize().width >= stretchWidth) {
            table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
//            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        }
    }

    private void flagOtions(boolean state) {
//        if (controller.getFlagInfo() != null && !controller.getFlagInfo().isEmpty()) {
//            for (int j = 0; j < controller.getFlagInfo().size(); j++) {
//                CreateType flg = controller.getFlagInfo().elementAt(j);
//                if (flg != null && flg.getId() > 0) {
//                    Component[] comp = flagPopupMenu.getComponents();
//                    for (int i = 0; i < comp.length; i++) {
//                        if (comp[i] instanceof JCheckBoxMenuItem) {
//                            if (((JCheckBoxMenuItem) comp[i]).getText().equals(controller.getFlagInfoById().get(flg.getId()).getTypeStyleValue())) {
//                                ((JCheckBoxMenuItem) comp[i]).setSelected(state);
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }

    private void flagOtions() {
        Component[] comp = flagPopupMenu.getComponents();
        for (int i = 0; i < comp.length; i++) {
            if (comp[i] instanceof JCheckBoxMenuItem) {
                if (((JCheckBoxMenuItem) comp[i]).getText().equals("Show All")
                        || ((JCheckBoxMenuItem) comp[i]).getText().equals("Flagged Entry")
                        && ((JCheckBoxMenuItem) comp[i]).isSelected()) {
                    flagOtions(((JCheckBoxMenuItem) comp[i]).isSelected());
                }
            }
        }
    }

    private void deafultFlag() {
        flagPopupMenu.removeAll();
        ButtonGroup menuGrp = new ButtonGroup();
        JCheckBoxMenuItem fAll = new JCheckBoxMenuItem("Show All", false);
        fAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flagPopupMenu.setVisible(true);
                flagOtions();
            }
        });
        menuGrp.add(fAll);
        flagPopupMenu.add(fAll);

        JCheckBoxMenuItem DWrap = new JCheckBoxMenuItem("Un Flagged", false);
        DWrap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                flagPopupMenu.setVisible(true);
                flagOtions(false);
            }
        });
        menuGrp.add(DWrap);
        flagPopupMenu.add(DWrap);
        JCheckBoxMenuItem onlyFlagged = new JCheckBoxMenuItem("Flagged Entry", false);
        onlyFlagged.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                flagPopupMenu.setVisible(true);
                flagOtions();
            }
        });

        menuGrp.add(onlyFlagged);
        flagPopupMenu.add(onlyFlagged);

//        JCheckBoxMenuItem allFlag = new JCheckBoxMenuItem("All Flagged", false);
//        allFlag.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                flagPopupMenu.setVisible(true);
//                flagOtions();
//            }
//        });
//        menuGrp.add(allFlag);
//        flagPopupMenu.add(allFlag);

        //adding Separator
        JSeparator rep = new JSeparator();
        flagPopupMenu.add(rep);
    }

    private void setCreatedFlag() {
//        if (controller.getFlagInfo() != null && !controller.getFlagInfo().isEmpty()) {
//            for (int j = 0; j < controller.getFlagInfo().size(); j++) {
//                CreateType flg = controller.getFlagInfo().elementAt(j);
//                if (flg != null && flg.getId() > 0) {
//
//                    JCheckBoxMenuItem wWrap = new JCheckBoxMenuItem(controller.getFlagInfoById().get(flg.getId()).getTypeStyleValue(), false);
////                    wWrap.setBackground(Utility.getColor(flg.getImgPath()));
////                    wWrap.setForeground(Utility.getColor(flg.getImgPath()));
//                    wWrap.addActionListener(new ActionListener() {
//
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            flagPopupMenu.setVisible(true);
//                            customeFlags();
//
//                        }
//                    });
//                    flagPopupMenu.add(wWrap);
//                }
//
//            }
//
//        }
    }

    private void clearAction() {
        if (table.getSelectedRowCount() > 0) {
            table.clearSelection();
        } else {
            table.selectAll();
        }
        updateTotals();
    }

    class PopupListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            showPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            try {
                if (!(table.getSelectedRowCount() > 1)) {
                    table.clearSelection();
                    table.addRowSelectionInterval(table.rowAtPoint(e.getPoint()), table.rowAtPoint(e.getPoint()));
                }
                showPopup(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private void showPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        value = new javax.swing.JTextField();
        btnRefresh = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnExpotExcel = new javax.swing.JButton();
        btnChart = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnCrossTab = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        chkAdvance = new javax.swing.JCheckBox();

        value.setText(org.openide.util.NbBundle.getMessage(MacTable.class, "smTable.value.text_1")); // NOI18N
        value.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueActionPerformed(evt);
            }
        });

        btnRefresh.setText(org.openide.util.NbBundle.getMessage(MacTable.class, "smTable.btnRefresh.text_1_1")); // NOI18N
        btnRefresh.setMaximumSize(new java.awt.Dimension(45, 25));
        btnRefresh.setMinimumSize(new java.awt.Dimension(45, 25));
        btnRefresh.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                btnRefreshStateChanged(evt);
            }
        });
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        jLabel1.setText(org.openide.util.NbBundle.getMessage(MacTable.class, "smTable.jLabel1.text_1")); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.setGridColor(new java.awt.Color(102, 102, 102));
        table.setIntercellSpacing(new java.awt.Dimension(4, 1));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        btnExpotExcel.setText(org.openide.util.NbBundle.getMessage(MacTable.class, "smTable.btnExpotExcel.text_1")); // NOI18N
        btnExpotExcel.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                btnExpotExcelStateChanged(evt);
            }
        });
        btnExpotExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExpotExcelActionPerformed(evt);
            }
        });

        btnChart.setText(org.openide.util.NbBundle.getMessage(MacTable.class, "smTable.btnChart.text_1_1")); // NOI18N
        btnChart.setMaximumSize(new java.awt.Dimension(45, 25));
        btnChart.setMinimumSize(new java.awt.Dimension(45, 25));
        btnChart.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                btnChartStateChanged(evt);
            }
        });
        btnChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChartActionPerformed(evt);
            }
        });

        btnAdd.setText(org.openide.util.NbBundle.getMessage(MacTable.class, "smTable.btnAdd.text_1_1")); // NOI18N
        btnAdd.setMaximumSize(new java.awt.Dimension(45, 25));
        btnAdd.setMinimumSize(new java.awt.Dimension(45, 25));
        btnAdd.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                btnAddStateChanged(evt);
            }
        });
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnCrossTab.setText(org.openide.util.NbBundle.getMessage(MacTable.class, "smTable.btnCrossTab.text_1_1")); // NOI18N
        btnCrossTab.setMaximumSize(new java.awt.Dimension(45, 25));
        btnCrossTab.setMinimumSize(new java.awt.Dimension(45, 25));
        btnCrossTab.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                btnCrossTabStateChanged(evt);
            }
        });
        btnCrossTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrossTabActionPerformed(evt);
            }
        });

        btnClear.setText(org.openide.util.NbBundle.getMessage(MacTable.class, "smTable.btnClear.text_1_1_1")); // NOI18N
        btnClear.setMaximumSize(new java.awt.Dimension(45, 25));
        btnClear.setMinimumSize(new java.awt.Dimension(45, 25));
        btnClear.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                btnClearStateChanged(evt);
            }
        });
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        chkAdvance.setText(org.openide.util.NbBundle.getMessage(MacTable.class, "smTable.chkAdvance.text_1")); // NOI18N
        chkAdvance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAdvanceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(value, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkAdvance)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnCrossTab, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChart, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExpotExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCrossTab, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChart, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExpotExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkAdvance)
                    .addComponent(jLabel1)
                    .addComponent(value, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAdd, btnChart});

    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        this.btnRefresh.setEnabled(false);
        hidden.clear();
        if (flagData != null) {
            flagData.clear();
//            flagData.addAll(getreportListner().getDataVector());
        }
//        clear();
        update();
        getreportListner().refreshTable();

}//GEN-LAST:event_btnRefreshActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        // TODO add your handling code here:
        getPopupMenu();
        getreportListner().tableMouseClickedAction(evt);
        if (!ToolTipForTable) {
            setToolTipForTable(true);
        }
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            int nCol = this.getTable().convertColumnIndexToModel(this.getTable().columnAtPoint(evt.getPoint()));
            int nRow = this.getTable().convertRowIndexToModel(this.getTable().rowAtPoint(evt.getPoint()));
            if (nCol == 0) {
                MessageDisplay.showErrorDialog(null, "Error in line ");
//                MessageDisplay.showInformationDialog(this,
//                        ((SerialNumberClassCellRenderer) table.getColumnModel().getColumn(0).getCellRenderer()).getTooltipTextOfRow(nRow));
            }
        }
        updateTotals();

    }//GEN-LAST:event_tableMouseClicked

    private void tableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseReleased
        // TODO add your handling code here:
        if (evt.getClickCount() != 2) {
            getreportListner().tableMouseClickedAction(evt);
            updateTotals();
        }
    }//GEN-LAST:event_tableMouseReleased

    public void openExportToExcelDailog() {
//        System.out.println("---->"+((Asui_Report_Panel)getParent().getParent()).getReportType());
//        String outFile = null;
//        try {
//            outFile = (((Asui_Report_Panel) getParent().getParent()).getReportType().trim() + ".xls");
//        } catch (Exception e) {
//            outFile = ("output.xls");
//        }
//        ExportExcelDailog dailog = ClientController.getDefault().getDialogManager().getExportExcelDailog(true);
//        dailog.setParent(this);
//        dailog.setLocation(Utility.getCenterLocation(dailog.getSize()));
//        dailog.setTable(this);
//        dailog.setOutFile(outFile);
//        dailog.setVisible(true);
    }

    public boolean exportToExcel(String outFile, MacTable t, File sFile,
            boolean inclCol, int sheet, int row) {
//        try {
//            export = new ExportToExcel(t.getTable());
//            export.setOutputFile(outFile);
//            export.setFileName(sFile.getAbsolutePath());
//
//            export.setSheet(sheet);
//            export.setRowIndex(row);
//            export.setIncl_header(inclCol);
//            try {
//                if (export.exportToExcel()) {
//                    AdcMessageDisplay.showSuccessDialog(this, "Successfully Exported To Excel.");
//
//                    export = null;
//                    return true;
//                } else {
//                    AdcMessageDisplay.showErrorDialog(this, "Export To Excel Failed.");
//
//                    export = null;
//                    return false;
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                AdcMessageDisplay.showErrorDialog(table, "File format is corrupted.\n " + e);
//                export = null;
//                return false;
//            }
//
//        } catch (Exception e) {
//            export = null;
        return false;
//        }
    }
    private void btnExpotExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExpotExcelActionPerformed
        // TODO add your handling code here:
//        if (ClientController.getDefault().getUserType().equals("A")) {
//            String exportFile = ReadPropertiesFile.getDefault().getExportExcelFileProperties("xlsDefaultFile");
//            if (exportFile != null) {
//                String outFile = null;
//                try {
//                    outFile = (((Asui_Report_Panel) getParent().getParent()).getReportType().trim() + ".xls");
//                } catch (Exception e) {
//                    outFile = ("output.xls");
//                }
//                if (!exportToExcel(outFile, this, new File(exportFile), true, 0, 0)) {
//                    openExportToExcelDailog();
//                }
//
//            } else {
//                openExportToExcelDailog();
//            }
//        } else {
//            if (ClientController.getDefault().getUserType().equals("M") || ClientController.getDefault().getUserType().equals("O")) {
//                if (controller.getUserSettings().isSave_report()) {
//                    String exportFile = ReadPropertiesFile.getDefault().getExportExcelFileProperties("xlsDefaultFile");
//                    if (exportFile != null) {
//                        String outFile = null;
//                        try {
//                            outFile = (((Asui_Report_Panel) getParent().getParent()).getReportType().trim() + ".xls");
//                        } catch (Exception e) {
//                            outFile = ("output.xls");
//                        }
//                        if (!exportToExcel(outFile, this, new File(exportFile), true, 0, 0)) {
//                            openExportToExcelDailog();
//                        }
//                    } else {
//                        openExportToExcelDailog();
//                    }
//                } else {
//                    AdcMessageDisplay.showErrorDialog(getParent(), "Please contact the Administrator");
//                    return;
//                }
//
//            }
//        }
    }//GEN-LAST:event_btnExpotExcelActionPerformed

    private void btnChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChartActionPerformed
        // TODO add your handling code here:
        buildChart();
    }//GEN-LAST:event_btnChartActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        getreportListner().getAddBtnAction();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnCrossTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrossTabActionPerformed
        // TODO add your handling code here:
        buildCrossTab();
    }//GEN-LAST:event_btnCrossTabActionPerformed

    private void btnRefreshStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_btnRefreshStateChanged
// TODO add your handling code here:
        btnStateChanged(evt);
    }//GEN-LAST:event_btnRefreshStateChanged

    private void btnAddStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_btnAddStateChanged
// TODO add your handling code here:
        btnStateChanged(evt);
    }//GEN-LAST:event_btnAddStateChanged

    private void btnCrossTabStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_btnCrossTabStateChanged
// TODO add your handling code here:
        btnStateChanged(evt);
    }//GEN-LAST:event_btnCrossTabStateChanged

    private void btnChartStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_btnChartStateChanged
// TODO add your handling code here:
        btnStateChanged(evt);
    }//GEN-LAST:event_btnChartStateChanged

    private void btnExpotExcelStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_btnExpotExcelStateChanged
// TODO add your handling code here:
        btnStateChanged(evt);
    }//GEN-LAST:event_btnExpotExcelStateChanged

    private void btnClearStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_btnClearStateChanged
        // TODO add your handling code here:
        btnStateChanged(evt);
    }//GEN-LAST:event_btnClearStateChanged

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        clearAction();

    }//GEN-LAST:event_btnClearActionPerformed

    private void chkAdvanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAdvanceActionPerformed
        // TODO add your handling code here:
        if (chkAdvance.isSelected()) {
//            setHeaderRenderer();
            headerheight = 42;
            setHeaderAdvance(true);
            for(int i =0; i <table.getColumnCount(); i++){
                sorter.setSortable(i, false);
            }
        } else {
            clear_Cols();
            headerheight = 21;
//            setTableModel(model.dataVector);
            update();
            setHeaderAdvance(false);
               for(int i =0; i <table.getColumnCount(); i++){
                sorter.setSortable(i, true);
            }
        }
    }//GEN-LAST:event_chkAdvanceActionPerformed

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        // TODO add your handling code here:
        jLabel1.setToolTipText(getColorToolTip());

        ToolTipManager.sharedInstance().setInitialDelay(10);
        ToolTipManager.sharedInstance().setDismissDelay(10000);
        setToolTipForTable(false);
    }//GEN-LAST:event_jLabel1MouseEntered

    private void valueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valueActionPerformed

    public void btnStateChanged(javax.swing.event.ChangeEvent e) {

        JButton btn = (JButton) e.getSource();
        if (btn.isEnabled()) {
            if (btn.getModel().isRollover()) {
                if (btn.getIcon() != null) {
                    btn.setContentAreaFilled(true);
                }
            } else {
                if (btn.getIcon() != null) {
                    btn.setContentAreaFilled(false);
                    btn.setBorderPainted(false);
                }
            }

        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnChart;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnCrossTab;
    private javax.swing.JButton btnExpotExcel;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JCheckBox chkAdvance;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private javax.swing.JTextField value;
    // End of variables declaration//GEN-END:variables
    private BigDecimal[] columnTotals;

    public JTable getTable() {
        return this.table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public TableRowSorter<TableModel> getSorter() {
        return sorter;
    }

    public void setSorter(TableRowSorter<TableModel> sorter) {
        this.sorter = sorter;
    }

    public void addReportListener(MacTableImplementPanelClass listener) {
        this.reportListeners.add(listener);
    }

    public void removeReportListener(MacTableImplementPanelClass listener) {
        this.reportListeners.remove(listener);
    }

//    public void addColorListener(TableCellColorInterface listener) {
//        this.colorListner = listener;
//    }
//    public void addFlagListener(AllocateFlagDialogListener listener) {
//        this.allocateFlagDialogListener = listener;
//    }
    private void update() {
        sorter = getfilterSorter();
        updateTotals();
    }

    public void updateTotals() {

        for (int col = 0; col < model.getColumnCount(); col++) {
            if (getreportListner().showTotalForColumn(col)) {
                columnTotals[col] = getTotal(col);
            }
        }

    }

    private BigDecimal getTotal(int c) {
        BigDecimal v = null;
        v = new BigDecimal(0.0);
        if (table.getSelectedRowCount() > 1) {
            int rows[] = table.getSelectedRows();
            for (int i = 0; i < rows.length; i++) {
                if (c == 0 && this.model.getColumnClass(c).equals(SerialNumberClass.class)) {
                    v = new BigDecimal(rows.length);
                } else {
                    int row = sorter.convertRowIndexToModel(rows[i]);
                    v = ((BigDecimal) v).add(new BigDecimal(model.getValueAt(row, c).toString()));
                }
            }
        } else {
            if (c == 0 && this.model.getColumnClass(c).equals(SerialNumberClass.class)) {
                v = new BigDecimal(this.getModelData().size());
            } else {
                for (int i = 0; i < sorter.getViewRowCount(); i++) {
                    int row = sorter.convertRowIndexToModel(i);
                    v = ((BigDecimal) v).add(new BigDecimal(model.getValueAt(row, c).toString()));
                }
            }
        }
        return v;
    }

    /*
     * this method use to perform filter action for all reports
     */
    private TableRowSorter<TableModel> filterNormal(String val, TableRowSorter<TableModel> sorter, int column, boolean all) {
        if (column > 0) {
            column--;
        }
        RowFilter<TableModel, Object> rf = null;
        if (val.startsWith("!") && val.length() > 0) {
            try {
                if (!all) {
                    rf = RowFilter.regexFilter("(?i)" + val.substring(1), column);
                } else {
                    rf = RowFilter.regexFilter("(?i)" + val.substring(1));
                }
                rf = RowFilter.notFilter(rf);
            } catch (PatternSyntaxException pse) {
                pse.printStackTrace();
                return sorter;
            }
        } else if (!val.startsWith("!")) {
            try {
                if (!all) {
                    rf = RowFilter.regexFilter("(?i)" + val, column);
                } else {
                    rf = RowFilter.regexFilter("(?i)" + val);
                }
            } catch (PatternSyntaxException pse) {
                pse.printStackTrace();
                return sorter;
            }
        }
        ArrayList<RowFilter<TableModel, Object>> filters = new ArrayList<RowFilter<TableModel, Object>>();
        filters.add(rf);
        filters.add(getUserSelectedRowFilter());
        RowFilter andFilter = RowFilter.andFilter(filters);
        sorter.setRowFilter(andFilter);
        return sorter;
    }

    public TableRowSorter<TableModel> getfilterSorter() {
        if (sorter == null) {
            sorter = new TableRowSorter<TableModel>(model);
        }
        if (chkAdvance.isSelected()) {
            sorter = filterAdvance(sorter);
        } else {
            sorter = filterNormal(value.getText(), this.sorter, 0, true);
        }
        return sorter;
    }

    public TableRowSorter<TableModel> filterAdvance(TableRowSorter<TableModel> sorter) {

        RowFilter<TableModel, Object> rf = null;

        ArrayList<RowFilter<TableModel, Object>> filters = new ArrayList<RowFilter<TableModel, Object>>();

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            String val = "";
            val = get_Cols().get(column.getHeaderValue().toString());
            if (val != null && !val.isEmpty()) {
//                if (val.startsWith("!") && val.length() > 0) {
//                    rf = RowFilter.regexFilter("(?i)" + val.substring(1), table.convertColumnIndexToModel(i));
//                } else {
//                    rf = RowFilter.regexFilter("(?i)" + val, table.convertColumnIndexToModel(i));
//                }
                rf = getRowFilter(val, i, column);
                filters.add(rf);
            }
        }
        filters.add(getUserSelectedRowFilter());
        RowFilter andFilter = RowFilter.andFilter(filters);
        sorter.setRowFilter(andFilter);
        return sorter;

    }

    private RowFilter<TableModel, Object> getRowFilter(String val, int viewIndex, TableColumn column) {
        RowFilter<TableModel, Object> rf = null;
        Class columnClass = table.getColumnClass(viewIndex);

        int colIndex = table.convertColumnIndexToModel(viewIndex);
        if (column != null) {
            if (columnClass == BigDecimal.class || columnClass == Long.class || columnClass == Integer.class
                    || columnClass == Double.class || columnClass == SerialNumberClass.class) {
                try {
                    if (val.startsWith("<=")) {
                        String subVal = val.substring(2).trim();
//                        Long number = Long.valueOf(subVal);
                        rf = RowFilter.numberFilter(ComparisonType.BEFORE, Long.valueOf(subVal) + 1, colIndex);
                    } else if (val.startsWith(">=")) {
                        String subVal = val.substring(2).trim();
                        rf = RowFilter.numberFilter(ComparisonType.AFTER, Long.valueOf(subVal) - 1, colIndex);
                    } else if (val.startsWith("<")) {
                        String subVal = val.substring(1).trim();
                        rf = RowFilter.numberFilter(ComparisonType.BEFORE, Long.valueOf(subVal), colIndex);
                    } else if (val.startsWith(">")) {
                        String subVal = val.substring(1).trim();
                        rf = RowFilter.numberFilter(ComparisonType.AFTER, Long.valueOf(subVal), colIndex);
                    } else if (val.startsWith("!=")) {
                        String subVal = val.substring(2).trim();
                        rf = RowFilter.numberFilter(ComparisonType.NOT_EQUAL, Long.valueOf(subVal), colIndex);
                    } else if (val.startsWith("==")) {
                        String subVal = val.substring(2).trim();
                        rf = RowFilter.numberFilter(ComparisonType.EQUAL, Long.valueOf(subVal), colIndex);
                    } else {
                        if (val.startsWith("!") && val.length() > 0) {
                            rf = RowFilter.regexFilter("(?i)" + val.substring(1), colIndex);
                        } else {
                            rf = RowFilter.regexFilter("(?i)" + val, colIndex);
                        }
                    }
                } catch (Exception e) {
                    rf = RowFilter.regexFilter("(?i)" + val, colIndex);
                }
//            } else if (columnClass == java.util.Date.class || columnClass == java.sql.Date.class) {
            } else {
                if (val.startsWith("!") && val.length() > 0) {
                    rf = RowFilter.regexFilter("(?i)" + val.substring(1), colIndex);
                } else {
                    rf = RowFilter.regexFilter("(?i)" + val, colIndex);
                }
            }
        }
        return rf;
    }

    private RowFilter<TableModel, Object> getUserSelectedRowFilter() {
        if (userFilter == null) {
            userFilter = new RowFilter<TableModel, Object>() {
                @Override
                public boolean include(Entry<? extends TableModel, ? extends Object> entry) {
                    if (hidden.contains(entry.getIdentifier())) {
                        return false;
                    }
                    return true;
                }
            };
        }
        return userFilter;
    }

    /*
     * This method sets the model & the vector as data in accordance with the
     * get value at method The Refresh button is also enabled here the model is
     * cleared before entering the data The atrributes/renderrer provided in set
     * attributes method is called here after setting the data
     *
     */
    public void setTableModel(Vector data) {
        if (model == null) {
            model = new TableModel();
        }
        if (flag != null) {
            flag.clear();
        }
//        flagData.addAll(getreportListner().getDataVector());
        table.setRowHeight(20);
        chkAdvance.setSelected(false);
//        table.setFont(new Font("Tahoma", Font.PLAIN, controller.getMasterSettings().getTableFont()));
        //  flagData = data;
        this.model.dataVector = data;
        model.setColumns(getreportListner().getColumnNames());
        model.setVisibleColumnCount(getreportListner().getVisibleColumnCount());
        this.columnTotals = new BigDecimal[model.getColumnCount()];
        table.setModel(model);
        sorter = new TableRowSorter<TableModel>(model);
        sorter.setMaxSortKeys(3);
        table.setRowSorter(sorter);
//        fieldsList.removeAllItems();
//        fieldsList.addItem("All");
//        for (int columnIndex = 0; columnIndex < model.getColumnCount(); columnIndex++) {
//            fieldsList.addItem(model.getColumnName(columnIndex));
//        }
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                updateTotals();
            }
        });
        model.TableChanged();
        this.value.setText("");
//        this.table.setToolTipText("");
        this.btnRefresh.setEnabled(true);
        columnHeaderAdapter.setToolTipForColumns();
        setDefaultCellEditor();
        setDefaultCellRenderer();
        getreportListner().setTableAttributes();
        hidden.clear();
        setTableColumns();
        setPrintOption();
        setFlagOptions();
        setHeaderRenderer();
        setHeaderAdvance(false);
        getColumnNamesFromBundle(getreportListner().getParentName());
        getColumnWidthFromBundle(getreportListner().getParentName());
//        getreportListner().setTableAttributes();
        setStrechableColumn();

    }

    private void setToolTipForTable(boolean b) {
        ToolTipForTable = b;
        if (b) {
            ToolTipManager.sharedInstance().setInitialDelay(50);
            ToolTipManager.sharedInstance().setDismissDelay(3000);

        }
    }

    private String getColorToolTip() {
//        if (colorListner != null) {
//            Vector<ColorDetail> v = colorListner.getToolTipForColor();
//            if (v != null) {
//                StringBuffer toolTip = new StringBuffer();
//                toolTip.append("<html><b>Color Description</b><body><table border=1>");
//                toolTip.append("<tr><td><b>Column Name</b></td><td><b>Color</b></td><td><b>Description</b></td></tr>");
//                for (ColorDetail c : v) {
//                    toolTip.append("<tr><td><b>" + getreportListner().getColumnNames()[c.getColumnNo()] + "</b></td>"
//                            + "<td bgcolor='" + c.getColor() + "'></td>"
//                            + "<td><b>" + c.getMessage() + "</b></td></tr>");
//                }
//                toolTip.append("</table></body></html>");
//                return toolTip.toString();
//            }
//        }
        return null;
    }
//    private String getColorToolTip() {
//        if (colorListner != null) {
//            Vector<ColorDetail> v = colorListner.ToolTipForColor();
//            StringBuffer toolTip = new StringBuffer();
//            toolTip.append("<html><b>Color Description</b><body><table border=1>");
//            toolTip.append("<tr><td><b>Column Name</b></td><td><b>Color</b></td><td><b>Description</b></td></tr>");
//
//            for (ColorDetail c : v) {
//                toolTip.append("<tr><td><b>" + getreportListner().getColumnNames()[c.getColumnNo()] + "</b></td>"
//                        + "<td><b>" + c.getColor()+ "</b></td>"
//                        + "<td><b>" +c.getMessage()+ "</b></td></tr>");
//            }
//            toolTip.append("</table></body></html>");
//            return toolTip.toString();
////            return colorListner.ToolTipForColor();
//
//        }
//        return null;
//    }

    public void setTableColumns() {
        try {
            columns.clear();
//            Properties tabProp = ReadPropertiesFile.getDefault().setSmtableProperties();;
//            visible = getColumsVisibility(tabProp, getreportListner().getParentName());
            columnsPopupMenu.removeAll();
            currComponent.clear();
            if (Scroller == null) {
//                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//                int inScroll = 20;
//                if (table.getColumnModel().getColumnCount() < 30) {
//                    if (screenSize.height < 700) {
//                    }
//                }
                Scroller = new MenuScroller(columnsPopupMenu, 20, 115, 2, 2);
            }
            TableColumnModel columnModel = table.getColumnModel();
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                columns.add(columnModel.getColumn(i));
                JCheckBoxMenuItem col = null;
                if (visible != null && visible.length > i && visible[i] != null) {
                    col = new JCheckBoxMenuItem(model.getColumnName(i), visible[i]);
                    col.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            columnsPopupMenu.setVisible(true);
                        }
                    });
                } else {
                    col = new JCheckBoxMenuItem(model.getColumnName(i), i < defaultVisibleColumns);
                    col.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            columnsPopupMenu.setVisible(true);
                        }
                    });
                }
                columnsPopupMenu.add(col);
                currComponent.add(col);

            }

            JMenuItem btnApply = new JMenuItem("Apply");
            btnApply.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

//                    Properties myProp = ReadPropertiesFile.getDefault().setSmtableProperties();
                    boolean allRemoved = true;
                    for (int i = 0; i < columns.size(); i++) {
                        if (((JCheckBoxMenuItem) currComponent.get(i)).isSelected()) {
//                        if (((JCheckBoxMenuItem) columnsPopupMenu.getComponent(i)).isSelected()) {
                            allRemoved = false;
                        }
                    }
                    if (allRemoved) {
//                        com.as.common.utils.Utility.makeErrorSound();
                        return;
                    }

                    updateColumnView();
                    if (chkAdvance.isSelected()) {
                        setHeaderRenderer();
                    }
//                    saveProperties(loadFile(), myProp, pro_value, getreportListner().getParentName());
//                    ReadPropertiesFile.getDefault().saveSmtableProperties(myProp, pro_value, getreportListner().getParentName());
                    setColumnWidthInBundle();
                    setColumnNamesInBundle();
                    columnsPopupMenu.setVisible(false);
////                 
                }
            });
            columnsPopupMenu.add(btnApply);
            KeyStroke k2 = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
            btnApply.registerKeyboardAction(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
//                    Properties myProp = ReadPropertiesFile.getDefault().setSmtableProperties();
                    boolean allRemoved = true;
                    for (int i = 0; i < columns.size(); i++) {
                        if (((JCheckBoxMenuItem) currComponent.get(i)).isSelected()) {
                            allRemoved = false;
                        }
//                        if (((JCheckBoxMenuItem) columnsPopupMenu.getComponent(i)).isSelected()) {
//                            allRemoved = false;
//                        }
                    }
                    if (allRemoved) {
//                        com.as.common.utils.Utility.makeErrorSound();
                        return;
                    }
                    updateColumnView();
//                    saveProperties(loadFile(), myProp, pro_value, getreportListner().getParentName());
//                    ReadPropertiesFile.getDefault().saveSmtableProperties(myProp, pro_value, getreportListner().getParentName());
                    setColumnWidthInBundle();
                    setColumnNamesInBundle();
                    columnsPopupMenu.setVisible(false);
                    if (chkAdvance.isSelected()) {
                        setHeaderRenderer();
                    }
                }
            }, k2, WHEN_IN_FOCUSED_WINDOW);
            columnsPopupMenu.add(btnApply);
            updateColumnView();
            columnsPopupMenu.setVisible(false);
//            columnsPopupMenu.addPopupMenuListener(new DeputyDawgPopupArrester());

        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
            updateColumnView();
//            saveProperties(loadFile(), loadProperties(loadFile()), pro_value, getreportListner().getParentName());
//            ReadPropertiesFile.getDefault().saveSmtableProperties(
//                    ReadPropertiesFile.getDefault().setSmtableProperties(), pro_value, getreportListner().getParentName());
            setColumnWidthInBundle();
        }
    }

    public void updateColumnView() {

        table.setIgnoreRepaint(true);
        TableColumnModel colModel = table.getColumnModel();
        for (int i = colModel.getColumnCount() - 1; i >= 0; i--) {
            TableColumn col = colModel.getColumn(i);
            table.removeColumn(col);
        }
        int i = 0;
        boolean noCol = false;
        // In this columnsPopupMenu.getComponent(i) replace with currComponent.get(i)
        for (TableColumn col : columns) {
            if (i == 0) {
//                pro_value = "" + ((JCheckBoxMenuItem) columnsPopupMenu.getComponent(i)).isSelected();
                pro_value = "" + ((JCheckBoxMenuItem) currComponent.get(i)).isSelected();
            } else {
                pro_value = pro_value + "," + ((JCheckBoxMenuItem) currComponent.get(i)).isSelected();
//                pro_value = pro_value + "," + ((JCheckBoxMenuItem) columnsPopupMenu.getComponent(i)).isSelected();
            }
            if (((JCheckBoxMenuItem) currComponent.get(i++)).isSelected()) {
//            if (((JCheckBoxMenuItem) columnsPopupMenu.getComponent(i++)).isSelected()) {
                table.addColumn(col);
                noCol = true;
            }
        }
        if (!noCol) {
            i = 0;
            for (TableColumn ncol : columns) {
                table.addColumn(ncol);
            }
        }
        table.setIgnoreRepaint(false);
        table.repaint();

    }

    /**
     * @return the columnsvisible
     */
    public Boolean[] getColumsVisibility(Properties tabProp, String key) {

        String[] visibleCol = null;
        if (tabProp.getProperty(key) != null) {
            visibleCol = tabProp.getProperty(key).split(",");
            visible = new Boolean[visibleCol.length];
//        Convert String To Boolean
            for (int i = 0; i < visibleCol.length; i++) {
                if (visibleCol[i] != null && visibleCol[i].trim().length() > 0) {
                    this.visible[i] = Boolean.parseBoolean(visibleCol[i]);
                } else {
                    this.visible[i] = true;
                }
            }
        } else {
//            this.visible = null;
            visible = module_columns;
        }
//        
        return this.visible;
    }

    public void setTextForTxtValue(String s) {
        this.value.setText(s);
    }

    public void setBtnEnabled(boolean b) {
        this.btnRefresh.setEnabled(b);
    }

    private String getToolTipForTextField() {
        return "<html><b> Table's Filter Tips </b>"
                + "<br><b> '^' - For matching exactly starting with the characters </b> Ex- ^abc "
                + "<br><b> '!' - For matching except using the characters </b> Ex- !abc"
                + "<br><b> '*' - For matching words using the characters as suffix & prefix </b>Ex- a*c"
                + "<br><b> '$' - For matching the exact end of the characters </b>Ex- abc$"
                + "<br><b> For Date filter use  YYYY-MM-DD  format."
                + "<p></html>";
    }

    public MacTableImplementPanelClass getreportListner() {

        if (this.repListner == null) {
            for (int i = 0; i < reportListeners.size(); i++) {
                this.repListner = (MacTableImplementPanelClass) reportListeners.elementAt(i);
                if (repListner != null) {
                    return repListner;
                }
            }
        }
        return repListner;
    }

    @Override
    public void performButtonAction(boolean b) {
        try {
            if (b) {
//                clearAction();
                printTable(((JCheckBoxMenuItem) printPopupMenue.getComponent(0)).isSelected());
            } else {
//                if (ClientController.getDefault().getUserType().equals("A")
//                        || controller.getUserSettings().isCan_flag()) {
//                    FlagCreationDialog dialog = controller.getDialogManager().getFlagCreationDialog(true);
//                    dialog.setKey("FIN");
//                    dialog.setLocation(Utility.getCenterLocation(dialog.getSize()));
//                    dialog.setVisible(true);
//                }
            }

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public JPopupMenu getOptionList(boolean b) {
        if (b) {
            setPrintOption();
            return printPopupMenue;
        } else {
//            if (ClientController.getDefault().getUserType().equals("A")
//                    || controller.getUserSettings().isCan_flag()) {
            setFlagOptions();
//            }
            return flagPopupMenu;
        }

    }

    private class TableModel extends AbstractTableModel implements ITotalTableFooter {

        private String[] columns;
        private int visibleColumnCount;
        private Vector dataVector;

        @Override
        public boolean isCellEditable(int nRow, int nCol) {
            return getreportListner().isCellEditable(nRow, nCol);
        }

        public void clear() {
            if (dataVector != null) {
                dataVector.clear();
            }
            table.clearSelection();
            sorter.setRowFilter(null);
        }

        public void setColumns(String[] colms) {
            this.columns = colms;

        }

        public void ShowToolTipForColor() {
            Hashtable<String, String> toolTip = new Hashtable<String, String>();
            try {
                for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
                    TableColumn column = table.getColumnModel().getColumn(i);
                    toolTip.put(column.getHeaderValue().toString(), "This is used for " + column.getHeaderValue());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void setVisibleColumnCount(int vcc) {
            this.visibleColumnCount = vcc;
        }

        @Override
        public int getRowCount() {
            return dataVector == null ? 0 : dataVector.size();

        }

        @Override
        public Class getColumnClass(int c) {
            return getreportListner().getcolumnClass(c);

        }

        @Override
        public String getColumnName(int col) {
            if (col < columns.length) {
                return columns[col];
            } else {
                return null;
            }
        }

        @Override
        public int getColumnCount() {
            if (visibleColumnCount > -1) {
                return visibleColumnCount;
            } else {
                return columns.length;
            }

        }

        public void addRow(int i, Object row) {
            if (dataVector == null) {
                dataVector = new Vector();
            }
            dataVector.add(i, row);
            // fire the event to the ui informing it that the row has been added
            // to the model.
//            fireTableRowsInserted(dataVector.size(), dataVector.size());
        }

        public void addRow(Object row) {
            if (dataVector == null) {
                dataVector = new Vector();
            }
            dataVector.add(row);
            // fire the event to the ui informing it that the row has been added
            // to the model.
//			fireTableRowsInserted(dataVector.size(), dataVector.size());
        }

        @Override
        public Object getValueAt(int nrow, int ncol) {
            if (getreportListner().getcolumnClass(ncol) == SerialNumberClass.class) {
                return getDisplayRowNumberForTable(nrow) + 1;
            }
            return getreportListner().getValueAt(nrow, ncol);
        }

        public Object getRow(int rowNum) {
            if (dataVector == null || rowNum > dataVector.size()) {
                return null;
            } else {
                return dataVector.elementAt(rowNum);
            }
        }

        public void TableChanged() {
            fireTableChanged(null);
        }

        @Override
        public boolean showTotalForColumn(int column) {
            return getreportListner().showTotalForColumn(column);
        }

        @Override
        public Object getTotal(int c) {
            return columnTotals[c];
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            getreportListner().setValueAt(aValue, rowIndex, columnIndex);
            TableChanged();
        }
    }

    public void clear() {
        if (model != null) {
            this.model.clear();
        }
    }
    /*
     * returns the filtered data shown in the table adn not the orignal
     * dataVector this method to be used for returning the info as vector when
     * either selection model is being used or only the tble information shown
     * is required.
     *
     */

    public Vector getSelectedModelData() {
        if (table.getSelectedRowCount() > 0) {
            Vector temp = new Vector();
            int[] indexes = table.getSelectedRows();
            Vector v = getModelData();
            for (int j = 0; j < indexes.length; j++) {
                temp.add(v.elementAt(indexes[j]));
            }
            return temp;
        } else {
            int result = MessageDisplay.showConfirmDialog(this.getParent(),
                    "No Row Selected. \n Do you want to continue with all rows.", "Cancel");
            if (result == MessageDisplay.YES_OPTION) {
                return getModelData();
            } else {
                return new Vector();
            }
        }
    }

    public Vector getModelData() {
        return getSortedDataVector(this.model.dataVector);
    }

    private Vector getSortedDataVector(Vector v) {
        Vector data = new Vector();
        for (int i = 0; i < sorter.getViewRowCount(); i++) {
            data.add(v.elementAt(sorter.convertRowIndexToModel(i)));
        }
        return data;
    }
    /*
     * this method returns the actual selected row in the orignal dataVector not
     * in the view of the table
     *
     */

    public int getSelectedRow() {
        if (table.getSelectedRowCount() > 0) {
            return table.convertRowIndexToModel(table.getSelectedRow());
        }
        return -1;
    }

    public int getDisplayRowNumberForTable(int r) {

//        if (ReadPropertiesFile.getDefault().getTableSerialNumberProperties()) {

//        return table.convertRowIndexToView(r);

        return r;
    }

    public void setCellRenderer(int col, TableCellRenderer r) {
        try {
            TableColumn tcol = table.getColumnModel().getColumn(col);
            tcol.setCellRenderer(r);
//            if (r.getClass().equals(DateCellRenderer.class)) {
//                String width = ReadPropertiesFile.getDefault().getRendererMinimumWidth("DateCellRenderer");
//                int wid = Integer.parseInt(width);
//                if (wid > 0) {
//                    tcol.setMinWidth(75);
//                    tcol.setWidth(wid);
//                    tcol.setPreferredWidth(wid);
//                } else {
////                    tcol.setMaxWidth(75);
//                    tcol.setMinWidth(75);
//                }
//            } else if (r.getClass().equals(AmountDrCrCellRenderer.class)) {
//                String width = ReadPropertiesFile.getDefault().getRendererMinimumWidth("AmountDrCrCellRenderer");
//                int wid = Integer.parseInt(width);
//                if (wid > 0) {
//                    tcol.setMinWidth(115);
//                    tcol.setWidth(wid);
//                    tcol.setPreferredWidth(wid);
//                } else {
////                    tcol.setMaxWidth(150);
//                    tcol.setMinWidth(115);
//                }
//            } else if (r.getClass().equals(ImageCellRender.class)) {
//                String width = ReadPropertiesFile.getDefault().getRendererMinimumWidth("ImageCellRender");
//                int wid = Integer.parseInt(width);
//                if (wid > 0) {
//                    tcol.setMinWidth(30);
//                    tcol.setWidth(wid);
//                    tcol.setPreferredWidth(wid);
//                } else {
//                    tcol.setMaxWidth(30);
//                    tcol.setMinWidth(30);
//                }
            if (r.getClass().equals(SerialNumberClass.class)) {
                String width = "40";//ReadPropertiesFile.getDefault().getRendererMinimumWidth("SerialNumberClass");
                int wid = Integer.parseInt(width);
                if (wid > 0) {
                    tcol.setMinWidth(50);
                    tcol.setWidth(wid);
                    tcol.setPreferredWidth(wid);
                } else {
//                    tcol.setMaxWidth(50);
                    tcol.setMinWidth(50);
                }
            }
        } catch (Exception ex) {
        }
    }

    public void setColumnWidth(int col, int min, int max) {
        TableColumn tcol = this.table.getColumnModel().getColumn(col);
        if (max > 0) {
            tcol.setMaxWidth(max);
        }
        if (min > 0) {
            tcol.setMinWidth(min);
        }

    }

    public Vector getDataVector() {
        return model.dataVector;
    }

    public TableModel getModel() {
        return this.model;
    }

    public JPopupMenu getPopupMenu() {
        return getPopupMenu(false);
    }

    public JPopupMenu getPopupMenu(boolean withDisplay) {
        if (popupMenu == null || popupMenu.getComponents().length == 0) {
            generatePopUpMenus(withDisplay);
        }
        return popupMenu;
    }

    public void clearPopupMenu(boolean withDisplay) {
        this.popupMenu.removeAll();
        generatePopUpMenus(withDisplay);
    }

    public void tableChange() {
        this.model.TableChanged();
    }

    public void tableCellUpdated(int row, int col) {
        model.fireTableCellUpdated(row, col);
    }

    public void setbtnExpotExcelVisibility(boolean b) {
        this.btnExpotExcel.setVisible(b);
    }

    public void setDropOptionComponent1Visibility(boolean b) {
//        this.dropOptionComponent1.setVisible(b);
    }

    public void setAddButtonVisiblity(boolean b) {
        this.btnAdd.setVisible(b);
    }

    public JButton getRefreshButton() {
        return btnRefresh;
    }

    public void setModularColumns(Boolean[] s) {
        module_columns = s;
    }

    public JButton getAddButton() {
        return btnAdd;
    }

    public void setToolTipForColumnHeader(int column, String tooltip) {
        columnHeaderAdapter.setToolTip(column, tooltip);
    }

    public void clear_Cols() {
        try {
            get_Cols().clear();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Hashtable<String, String> get_Cols() {
        if (_cols == null) {
            _cols = new Hashtable<String, String>();
        }
        return _cols;
    }

    private void setDefaultCellEditor() {
        TextFieldCellEditor txteditor = new TextFieldCellEditor();
        for (int i = 0; i < table.getColumnCount(); i++) {
            try {
                TableColumn column = table.getColumnModel().getColumn(i);
                Class columnClass = table.getColumnClass(i);
                if (column != null && columnClass == BigDecimal.class) {

                    column.setCellEditor(txteditor);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setDefaultCellRenderer() {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(row % 2 == 0 ? Utility.getDefault().getRowBgColor() : Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
                return this;
            }
        });

        table.setDefaultRenderer(Number.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(row % 2 == 0 ? Utility.getDefault().getRowBgColor() : Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
                return this;
            }
        });
        SerialNumberClassCellRenderer cellRenderer = new SerialNumberClassCellRenderer();
        if (this.model.dataVector != null && !this.model.dataVector.isEmpty()) {
            // cellRenderer.setColorArray(this.model.dataVector.size());
            for (int row = 0; row < this.model.dataVector.size(); row++) {
                try {
//                    if (this.model.dataVector.elementAt(row) instanceof ExtendedMessage) {
//                        ExtendedMessage msg = (ExtendedMessage) this.model.dataVector.elementAt(row);
//                        CreateType flag = ClientController.getDefault().getFlagInfoById().get(msg.getFlag_id());
//                        if (flag != null) {
////                        cellRenderer.setBackColor(row, Utility.getColor(flag != null ? flag.getImgPath() : "FFFFCC"));
//                            cellRenderer.setBackColor(row, Utility.getColor(flag.getImgPath()),
//                                    Utility.getSmTableToolTipText(flag, msg.getFlagMessage()));
//                        } else {
//                    Utility.getColor("FFFFCC")
//                    cellRenderer.setBackColor(row, Utility.getColor("FFFFCC"), "No Flag Specified");
                    cellRenderer.setBackColor(row, row % 2 == 0 ? Utility.getDefault().getRowBgColor() : Color.WHITE);
//                        }
                    TableColumn column = table.getColumnModel().getColumn(0);
                    Class columnClass = table.getColumnClass(0);
                    if (column != null && columnClass == SerialNumberClass.class) {
                        column.setCellRenderer(cellRenderer);
                    }

//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * This Function Hide the table row using Del key and unhide the table row
     * using Escape key via Keyboard
     */
    private void addKeyListener() {
        KeyStroke k2 = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
        table.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideActionPerformed();
            }
        }, k2, WHEN_IN_FOCUSED_WINDOW);

        KeyStroke k3 = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        table.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unHideActionPerformed();
            }
        }, k3, WHEN_IN_FOCUSED_WINDOW);
    }

    private void setStrechableColumn() {
//        if (ClientController.getDefault().isStretech()) {
//            SmTableHorizontalScrollBar policy = new SmTableHorizontalScrollBar(this, jScrollPane1, table);
//        }
    }

    private class ColumnHeaderAdapter extends MouseAdapter {

        private Hashtable<String, String> toolTips = new Hashtable<String, String>();
        private int curCol;

        public void setToolTipForColumns() {
            try {
                for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
                    TableColumn column = table.getColumnModel().getColumn(i);
                    toolTips.put(column.getHeaderValue().toString(), "This is used for " + column.getHeaderValue());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void mouseMoved(MouseEvent evt) {
            try {
                if (curCol != table.columnAtPoint(evt.getPoint()) && evt.getSource() instanceof JTableHeader) {

                    JTableHeader header = (JTableHeader) evt.getSource();
                    TableColumn column = table.getColumnModel().getColumn(table.columnAtPoint(evt.getPoint()));
                    header.setToolTipText(toolTips.get(column.getHeaderValue().toString()));
                    curCol = table.columnAtPoint(evt.getPoint());
//
//                    setColumnWidthInBundle();
//                    setColumnNamesInBundle();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void mouseDragged(MouseEvent evt) {
            setColumnWidthInBundle();
            setColumnNamesInBundle();
        }

        private void setToolTip(int col, String tooltip) {
            if (col != -1 && tooltip != null) {
                try {
                    TableColumn column = table.getColumnModel().getColumn(col);
                    toolTips.put(column.getHeaderValue().toString(), tooltip);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        private JPopupMenu MSArenamePopup;
        private TableColumn MSAcolumn;
        private TableHeaderRendererPanel MSApanel = new TableHeaderRendererPanel();

        public ColumnHeaderAdapter() {
            MSApanel.getTextField().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (chkAdvance.isSelected()) {
                        search();
                    }
                }
            });

            MSArenamePopup = new JPopupMenu();
//            MSArenamePopup.setBorder(new MatteBorder(0, 1, 1, 1, Color.DARK_GRAY));
            MSArenamePopup.add(MSApanel);
        }

        @Override
        public void mouseClicked(MouseEvent event) {
            if (chkAdvance.isSelected() && event.getClickCount() == 2) {
                editColumnAt(event.getPoint());
            }


        }

        private void editColumnAt(Point p) {
            int columnIndex = table.getTableHeader().columnAtPoint(p);
            if (columnIndex != -1) {
                MSAcolumn = table.getTableHeader().getColumnModel().getColumn(columnIndex);
                Rectangle columnRectangle = table.getTableHeader().getHeaderRect(columnIndex);
                MSArenamePopup.setBorderPainted(false);
                MSApanel.getLabel().setText(MSAcolumn.getHeaderValue().toString());
                MSArenamePopup.setPreferredSize(new Dimension(columnRectangle.width, columnRectangle.height - 1));
                MSArenamePopup.show(table.getTableHeader(), columnRectangle.x, 0);

                MSApanel.getTextField().setText(get_Cols().get(MSAcolumn.getHeaderValue().toString()));
                MSApanel.getTextField().requestFocusInWindow();
                MSApanel.getTextField().selectAll();
            }
        }

        private void search() {
            if (MSAcolumn != null) {
                get_Cols().put(MSAcolumn.getHeaderValue().toString(), MSApanel.getTextField().getText());
                getTable().getTableHeader().repaint();
                getfilterSorter();
                updateTotals();
            }
        }
    }

    private void setHeaderRenderer() {
//        headerheight = 42;
//        table.getTableHeader().setSize(new Dimension(4000, headerheight));
//        table.getTableHeader().setPreferredSize(new Dimension(4000, headerheight));
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableHeaderRenderer headerRenderer = new TableHeaderRenderer(table.getColumnModel().getColumn(i), this);
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        table.getTableHeader().repaint();
        headerheight = 21;
    }

    private void setHeaderAdvance(boolean adv) {
        table.getTableHeader().setSize(new Dimension(4000, headerheight));
        table.getTableHeader().setPreferredSize(new Dimension(4000, headerheight));
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            ((TableHeaderRenderer) table.getColumnModel().getColumn(i).getHeaderRenderer()).setAdvance(adv);
        }
    }
//    private class ComponentAdapterImpl extends MouseAdapter {
//
//        @Override
//        public void mouseReleased(MouseEvent e) {
//            if (table.getWidth() < jScrollPane1.getViewport().getWidth()) {
//                table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
//                table.setPreferredScrollableViewportSize(new Dimension(jScrollPane1.getViewport().getWidth(), 0));
//            }
//
//            setColumnWidthInBundle();
//            setColumnNamesInBundle();
//
//        }
//
//        @Override
//        public void mousePressed(MouseEvent e) {
//            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//            jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        }
//    }   

    public ArrayList<String> getFlagIds() {
        return flag;
    }

    private void customeFlags() {
        String value = "";
        Component[] comp = flagPopupMenu.getComponents();
        for (int i = 0; i < comp.length; i++) {
            if (comp[i] instanceof JCheckBoxMenuItem) {
                if (((JCheckBoxMenuItem) comp[i]).isSelected()) {
                    if (!flag.contains(((JCheckBoxMenuItem) comp[i]).getText())) {
                        flag.add(((JCheckBoxMenuItem) comp[i]).getText());
                    }
                }
            }
        }
    }
//     public static class DeputyDawgPopupArrester implements PopupMenuListener {  
//   
//        public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {  
//        }  
//   
//        public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {  
//            try {  
//                Thread.sleep(3000);  
//            } catch (InterruptedException ex) {  
//                 
//            }  
//        }  
//   
//        public void popupMenuCanceled(PopupMenuEvent arg0) {  
//        }  
//    }  
}
