package view;

import exceptions.GUI_Manager_Exception;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
public class GUIManager {

    String[][] vehiclesList;
    String[][] phasesList;
    String[][] statisticsList;
    String[][] emissionList;

    public GUIManager(String[][] vehicles, String[][] phases, String[][] statistics, String[][] emission) throws GUI_Manager_Exception {
        if (vehicles == null)
            throw new GUI_Manager_Exception("Vehicles table cannot be NULL");
        if (phases == null)
            throw new GUI_Manager_Exception("Phases table cannot be NULL");
        if (statistics == null)
            throw new GUI_Manager_Exception("Statistics table cannot be NULL");
        if (emission == null)
            throw new GUI_Manager_Exception("Emission table cannot be NULL");

        vehiclesList = vehicles;
        phasesList = phases;
        statisticsList = statistics;
        emissionList = emission;
    }

    private JFrame frame;
    private JTable vehicles_table;
    private JTable phases_table;
    private JTable statistics_table;
    private JTable add_vehicles_table;
    private JTable emission_table;

    public void initialize_GUI(){
        // Step 1: Create the JFrame
        frame = new JFrame();
        frame.setSize(1270,500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Step 2: Crate the JPanel
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JPanel panel2 = new JPanel();
        frame.getContentPane().add(panel2, BorderLayout.SOUTH);
        panel2.setLayout(null);

        // Step 4: Create the JTables:
        /* 1. Write the table data
         * 2. Initialize the table with the data
         * 3. Set the table model to be uneditable if necessary
         * 4. Add a scroll panel initialized with the table just created
         * 5. Set the bounds of the scroll panel so that it appears on the window frame
         * 6. Set the scroll pane borders (to include the table title)
         * 7. Add the scroll pane to the main GUI panel
         */

        String[] vehicles_table_cols_titles = {"Vehicle", "Type", "Crossing Time", "Direction", "Length", "Emission", "Status", "Segment"};

        vehicles_table = new JTable(vehiclesList, vehicles_table_cols_titles);
        DefaultTableModel vehicles_table_model = new DefaultTableModel(vehiclesList, vehicles_table_cols_titles) {

            @Override
            public boolean isCellEditable(int row, int column) {
                // All cells are uneditable
                return false;
            }
        };
        vehicles_table.setModel(vehicles_table_model);
        
        JScrollPane vehicles_scroll_pane = new JScrollPane(vehicles_table);
        vehicles_scroll_pane.setBounds(30, 30, 560, 300);
        vehicles_scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        vehicles_scroll_pane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Vehicles", TitledBorder.CENTER, TitledBorder.TOP));
        panel.add(vehicles_scroll_pane);
        vehicles_scroll_pane.setViewportView(vehicles_table);

        String[] phases_table_cols_titles = {"Phase", "Duration (secs)"};
        phases_table = new JTable(phasesList, phases_table_cols_titles);

        DefaultTableModel phases_table_model = new DefaultTableModel(phasesList, phases_table_cols_titles) {

            @Override
            public boolean isCellEditable(int row, int column) {
                // All cells are uneditable
                return false;
            }
        };
        phases_table.setModel(phases_table_model);

        JScrollPane phases_scroll_pane = new JScrollPane(phases_table);
        phases_scroll_pane.setBounds(600, 30, 300, 150);
        phases_scroll_pane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Phases", TitledBorder.CENTER, TitledBorder.TOP));
        panel.add(phases_scroll_pane);
        phases_scroll_pane.setViewportView(phases_table);


        // Statistics Table
        String[] statistics_table_cols_title = {"Segment", "Waiting Time", "Waiting Length", "Cross Time"};

        statistics_table = new JTable(statisticsList, statistics_table_cols_title);
        DefaultTableModel statistics_table_model = new DefaultTableModel(statisticsList, statistics_table_cols_title) {

            @Override
            public boolean isCellEditable(int row, int column) {
                // All cells are uneditable
                return false;
            }
        };
        statistics_table.setModel(statistics_table_model);

        JScrollPane statistics_scroll_pane = new JScrollPane(statistics_table);
        statistics_scroll_pane.setBounds(900, 30, 350, 150);
        panel.add(statistics_scroll_pane);
        statistics_scroll_pane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Statistics", TitledBorder.CENTER, TitledBorder.TOP));
        statistics_scroll_pane.setViewportView(statistics_table);

        // Add Vehicle Table
//        String[][] add_vehicles_data = { {"", "", "", "", "", "", "", ""}   };
//        String[] add_vehicles_cols_title = {"Vehicle", "Type", "Crossing Time", "Direction", "Length", "Emission", "Status", "Segment"};
//
//        add_vehicles_table = new JTable(add_vehicles_data, add_vehicles_cols_title);
//        JScrollPane add_vehicles_scroll_pane = new JScrollPane(add_vehicles_table);
//        add_vehicles_scroll_pane.setBounds(30, 350, 650, 100);
//        panel.add(add_vehicles_scroll_pane);
//        add_vehicles_scroll_pane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Add Vehicle", TitledBorder.CENTER, TitledBorder.TOP));
//        add_vehicles_scroll_pane.setViewportView(add_vehicles_table);
//
        // Emission Table
        String[] emission_table_cols_title = {"CO2 (kg)"};

        emission_table = new JTable(emissionList, emission_table_cols_title);
        JScrollPane emission_scroll_pane = new JScrollPane(emission_table);
        emission_scroll_pane.setBounds(700, 250, 200, 50);
        panel.add(emission_scroll_pane);
        emission_scroll_pane.setViewportView(emission_table);

// ---------------------------------------------------------------------------------------------------------------------

        // Step 5: Add Buttons
        /*
         * 1. Initialize the button (Title & bounds)
         * 2. Add the button to the panel
         * 3. Add the button's action listener
         */

        // Exit Button:
        JButton exit_button = new JButton("EXIT");
        exit_button.setBounds(1100, 350, 80, 30);

        panel.add(exit_button);

        exit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Add Button:
        JButton add_button = new JButton("ADD");
        add_button.setBounds(50, 350, 80, 30);

        panel.add(add_button);

        add_button.addActionListener(event -> 
        {
      	   frame.setEnabled(false);
      	   AddVehicleForm form = new AddVehicleForm(frame);
         });

        // Cancel Button:
        JButton cancel_button = new JButton("CANCEL");
        cancel_button.setBounds(250, 350, 80, 30);
        panel.add(cancel_button);
        frame.setVisible(true);
    }

}

