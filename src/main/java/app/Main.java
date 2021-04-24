package app;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/*
Authors: Team Delta: Zachary Pesons, Nathan Wray, Dustin Cook, David Solan
Purpose: contains the main method and the GUI logic.
Date: March 18, 2021
 */

public class Main extends JFrame {

    // Variables declaration
    private final JButton addMenuItemBtn = new JButton();
    private final JButton editInventoryBtn = new JButton();
    private final JButton exportOrderBtn = new JButton();
    private final JTable inventoryTable = new JTable();
    private final JScrollPane jScrollPane1 = new JScrollPane();
    private final JButton loadSalesBtn = new JButton();
    private final JButton predictOrderBtn = new JButton();
    private JFileChooser chooser;
    //table objects
    private DefaultTableModel model;
    private String[][] inventoryTableInput;
    private Object[][] tableData;
    //custom objects
    private Inventory inventory;
    private ArrayList<MenuItem> menu;
    private SaleItem[] sales;
    private HashMap<MenuItem, Integer> averageSales;
    private HashMap<Ingredient, Integer> ingredientSales;
    // End of variables declaration

    public Main() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="GUI">
    private void initComponents() {

        //create inventory
        inventory = new Inventory();

        //initialize table data to length of inventory and 4 columns
        inventoryTableInput = new String[inventory.getInventoryMap().size()][4];

        /*
DELETE BELOW
         */
        inventoryTableInput = new String[10][4];//test
        for (int i = 0; i < inventoryTableInput.length; i++) {
            inventoryTableInput[i][0] = "chicken";
            inventoryTableInput[i][1] = Integer.toString(4);
            inventoryTableInput[i][2] = Integer.toString(10);
            inventoryTableInput[i][3] = Integer.toString(6);
        }
        /*
DELETE ABOVE
         */

        tableData = inventoryTableInput.clone();

        
        model = new DefaultTableModel(tableData,
                new String[]{
                    "Item", "Inventory", "Needed", "To Order"
                }) {
            boolean[] canEdit = new boolean[]{false, true, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        loadInventory();
        
        //Action listeners for buttons
        addMenuItemBtn.setText("Add Menu Item");
        addMenuItemBtn.addActionListener(e -> {
            addMenuItemBtnActionPerformed();
        });

        editInventoryBtn.setText("Edit Inventory");
        editInventoryBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editInventoryBtnActionPerformed(evt);
            }
        });

        loadSalesBtn.setText("Load Last Week Sales");
        loadSalesBtn.addActionListener(e -> {
            loadSalesBtnActionPerformed();
        });

        predictOrderBtn.setText("Predict Next Week's Order");
        predictOrderBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                predictOrderBtnActionPerformed(evt);
            }
        });

        exportOrderBtn.setText("Export Order");
        exportOrderBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exportOrderBtnActionPerformed(evt);
            }
        });

        //JTable that displays the inventory and the order
        inventoryTable.setModel(model);
        jScrollPane1.setViewportView(inventoryTable);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(loadSalesBtn)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(predictOrderBtn))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(addMenuItemBtn)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(editInventoryBtn))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(exportOrderBtn)
                                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 451, GroupLayout.PREFERRED_SIZE))))
                                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(addMenuItemBtn)
                                        .addComponent(editInventoryBtn))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(loadSalesBtn)
                                        .addComponent(predictOrderBtn))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(exportOrderBtn)
                                .addContainerGap(32, Short.MAX_VALUE))
        );
        pack();
    }//end of initComponents // </editor-fold>

    private void loadInventory(){
        //find inventory file and create inventory object based on ingredient and amount
        //for each line, new ingredient is instantiated and added to inventory map        
    }
    
    private void addMenuItemBtnActionPerformed() {
        chooser = new JFileChooser(".");
        chooser.setDialogTitle("Select Menu Item");
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File input = chooser.getSelectedFile();
            //parse menu item file to create new menu item
        }

    }//end addMenuBtn

    private void predictOrderBtnActionPerformed(ActionEvent evt) {
        //populates fourth column of table with either zero for dont order
        //OR number of items to order
        
    }//end predictOrder

    private void editInventoryBtnActionPerformed(ActionEvent evt) {
        //pop up window that edits the Inventory map by it's getter/setter
        //alternately this could be done in the table - not yet implemented
        
    }//end editInventory

    private void loadSalesBtnActionPerformed() {
        chooser = new JFileChooser(".");
        chooser.setDialogTitle("Select Sales File");
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File input = chooser.getSelectedFile();
            //parse menu item file to create new menu item
        }
    }//end loadSales

    private void exportOrderBtnActionPerformed(ActionEvent evt) {
        //take the tableData and print it in CSV or other readable format
        //button should either be hidden until predict order is pressed OR 
        //pop up stating that there is no order predicted and to continue anyway

    }//end exportOrder

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }//end of main

}
