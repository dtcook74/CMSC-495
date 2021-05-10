package app;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/*
Authors: Team Delta: Zachary Pesons, Nathan Wray, Dustin Cook, David Solan
Purpose: contains the main method and the GUI logic.
Date: May 10, 2021
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
    private OrderManager manager = new OrderManager();
    private List<MenuItem> menu;
    //private HashMap<MenuItem, List<Ingredient>> menu;
    private SaleItem[] sales;
    private HashMap<String, Integer> averageSales = new HashMap<>();
    private HashMap<Ingredient, Integer> ingredientSales;
    private boolean updating = false;
    private final CsvReader reader = new CsvReader();
    private int numWeeks = 0;
    // End of variables declaration

    public Main() {
        //Inventory has to load before Menu
        try {
            initInventory();
            initMenu();
        } catch (MenuNotFoundException | InventoryNotFoundException | FileNotFoundException
                | InvalidCsvFormatException e) {
            System.out.println(e.getMessage());
        }
        exportOrderBtn.setEnabled(false);
        predictOrderBtn.setEnabled(false);
        initComponents();
        // Visual presentation
        setSize(580,540);
        setLocationRelativeTo(null); // Center window on screen
        setResizable(false);
    }
    
    @SuppressWarnings("unchecked")

    private void initComponents() {
// <editor-fold defaultstate="collapsed" desc="GUI and builder method">
        this.setTitle("Magic Pro-Order V9000");

        try {
        	tableData = inventoryTableInput.clone();
        }
        catch (Exception ex1) {
        	System.out.println("Inventory data not loaded.");
        }
        model = new DefaultTableModel(tableData,
                new String[]{
                    "Item", "Inventory", "Needed", "To Order"
                }) {
            boolean[] canEdit = new boolean[]{false, true, false, false};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        
        // Listen for changes made on the inventory column
        // and update to order column accordingly
        model.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                // your code goes here, whatever you want to do when something changes in the table

            	if(e.getColumn() != 1)
            	{
            		// do nothing
            	}
            	else if (updating == false) 
            	{
                    doUpdate(e);
                }
            }

            // Prevents stack overflow error when inserting new
            // table values. "Locks" table so tableChanged method cannot
            // affect it while table is being updated
            public void doUpdate(TableModelEvent e) {
                updating = true; // Lock  
                try {
	                //int col = e.getColumn();
	                int row = e.getFirstRow();
	                if(model.getValueAt(row, 2) != null)
	                {
	                	String inv = (String) model.getValueAt(row, 1); // value in inventory column
	                	int need = (int) model.getValueAt(row, 2); // value in need column
		                int newVal = need - Integer.parseInt(inv);
		                String nv = String.valueOf(newVal);
		                model.setValueAt(nv, row, 3); // set new to order value
	                }
                }
                catch (Exception ex2) {
                	// Null pointer exception if column data is not filled in
                }
	            updating = false; // Unlock
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
                                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 550, GroupLayout.PREFERRED_SIZE))))
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
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(exportOrderBtn)
                                .addContainerGap(32, Short.MAX_VALUE))
        );
        pack();
    }//end of initComponents // </editor-fold>

    private void addMenuItemBtnActionPerformed() {
        chooser = new JFileChooser(".");
        chooser.setDialogTitle("Select Menu Item");
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File input = chooser.getSelectedFile();
            //parse menu item file to create new menu item
        }

    }//end addMenuBtn

    private void predictOrderBtnActionPerformed(ActionEvent evt) {
        //set average sales based on number of weeks added
        for (HashMap.Entry element : averageSales.entrySet()) {
            element.setValue((int) element.getValue() / numWeeks);
        }
        
        //use the averageSales and menu to populate ingredientSales
        //use that amount to populate inventoryTableInput[ingredient name][column 3
        manager.predictOrder(inventory.getInventoryMap());
        HashMap<String, Integer> glist = manager.getGroceryList();
        //System.out.println("\nTo Order");
        for(String h : glist.keySet())
        {
        	//System.out.println(h + " " + glist.get(h));
        	// Find the ingredient that matches and fill in the needed column
        	for (int i = 0; i < model.getRowCount(); i++) {
        		if(model.getValueAt(i, 0).equals(h))
        			model.setValueAt(glist.get(h), i, 3);
        	}
        }
   		
        tableData = inventoryTableInput.clone();
        model.fireTableDataChanged();
//        for (HashMap.Entry ele : averageSales.entrySet()) {
//            System.out.println(ele.getKey() + " " + ele.getValue());
//        }
        exportOrderBtn.setEnabled(true);
    }//end predictOrder

    private void editInventoryBtnActionPerformed(ActionEvent evt) {
        //pop up window that edits the Inventory map by it's getter/setter
        //alternately this could be done in the table - not yet implemented

    }//end editInventory

    private void loadSalesBtnActionPerformed() {
        chooser = new JFileChooser(".");
        chooser.setDialogTitle("Select Sales File");
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                File input = chooser.getSelectedFile();
                Sales temp = reader.getSales(input);
                numWeeks++;
                for (SaleItem sale : temp.getSaleItems()) {
                    if (averageSales.containsKey(sale.getName())) {
                        //System.out.println("entering " + sale.getName());
                        averageSales.put(sale.getName(),
                                sale.getSales() + averageSales.get(sale.getName()));
                    } else {
                        averageSales.put(sale.getName(), sale.getSales());
                    }
                }
                
                // Once average sales is calculated, send a copy to
                // the order manager.
                manager.setAverageSales(averageSales);
                
                // Uses primarily the same code from predictOrderBtnActionPerformed() to fill in the 'Needed' column of model table
                for(MenuItem m : menu)
                {
                	if(m == null || m.getName().equals("") || m.getIngredients()==null)
                	{
                		// do not add
                	}
                	else
                	{
                		manager.addMenuItem(m.getName(), m.getIngredients());
                	}
                }
                manager.calcIngredientNeeds();
                HashMap<String, Integer> glist = manager.getNeeds();
                for(String h : glist.keySet())
                {
                	// Find the ingredient that matches and fill in the needed column
                	for (int i = 0; i < model.getRowCount(); i++) {
                		if(model.getValueAt(i, 0).equals(h))
                			model.setValueAt(glist.get(h), i, 2);
                	}
                }
            } catch (FileNotFoundException | InvalidCsvFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        predictOrderBtn.setEnabled(true);
    }//end loadSales

    private void exportOrderBtnActionPerformed(ActionEvent evt) {
        //take the tableData and print it in CSV or other readable format
        //button should either be hidden until predict order is pressed OR
        //pop up stating that there is no order predicted and to continue anyway
    	
    	// Output model table to output file OrderOut.csv
    	try {
    	    File fileOut = new File("OrderOut.csv");
    	    if (fileOut.createNewFile()) {
    	    	// Write table to output csv file
    	    	FileWriter fw = new FileWriter("OrderOut.csv");
    	    	for (int i = 0; i < model.getRowCount(); i++) {
        	    	for (int j = 0; j < model.getColumnCount(); j++) {
        	    		if(model.getValueAt(i, j) == null)
        	    		{
        	    			fw.write("0" + ",");
        	    		}
        	    		else
        	    		{
        	    			fw.write(model.getValueAt(i, j) + ",");
        	    		}
        	    	}
        	    	fw.write("\n");
    	    	}
    	    	fw.close();
    	    	
    	    	System.out.println("\nFile created: " + fileOut.getName());
       	    }
    	    else {
    	    	// Write table to output csv file
    	    	FileWriter fw = new FileWriter("OrderOut.csv");
    	    	for (int i = 0; i < model.getRowCount(); i++) {
        	    	for (int j = 0; j < model.getColumnCount(); j++) {
        	    		if(model.getValueAt(i, j) == null)
        	    		{
        	    			fw.write("0" + ",");
        	    		}
        	    		else
        	    		{
        	    			fw.write(model.getValueAt(i, j) + ",");
        	    		}
        	    	}
        	    	fw.write("\n");
    	    	}
    	    	fw.close();
    	    	
    	    	System.out.println("\nFile overwritten.");
    	    }
    	    System.out.println(fileOut.getAbsolutePath());
    	} catch (Exception ioEx1) {
    	    System.out.println("IO Exception thrown");
    	}

    }//end exportOrder

    private void initInventory() throws InventoryNotFoundException, FileNotFoundException, InvalidCsvFormatException {
        inventory = new Inventory();
        inventory = reader.getInventory("Inventory.csv");
        inventoryTableInput = new String[inventory.getInventoryMap().size()][4];

        int i = 0;
        for (Map.Entry<Ingredient, Integer> set : inventory.getInventoryMap().entrySet()) {
            inventoryTableInput[i][0] = set.getKey().getName();
            inventoryTableInput[i][1] = String.valueOf(set.getValue());
            i++;
        }

    }//end initInventory

    private void initMenu() throws MenuNotFoundException, FileNotFoundException, InvalidCsvFormatException {
        //scan menu file, create MenuItem object based on name and list
        //of Ingredient objects
        menu = reader.getMenuItems("menu.csv");

        /*for (MenuItem men : menu) {
            System.out.println(men.getName());
            for (Ingredient ing : men.getIngredients()) {
                System.out.println(ing.getName());
            }
        } */

        //If ingredient is not found, create it and add zero to inventory
        //sort of like this
        inventory.getInventoryMap().putIfAbsent(new Ingredient("name", 0), 0);
        //or like this
        int i = 0;
        Ingredient temp = new Ingredient("name", 0);
        for (Ingredient name : inventory.getInventoryMap().keySet()) {
            //or name.getName.equals(temp.getName) if compareTo doesnt work
            if (name.compareTo(temp) == 0) {
                i++;
            }
        }
        if (i > 0) {
        }//ingredient already exists
        
    }//end initMenu

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
