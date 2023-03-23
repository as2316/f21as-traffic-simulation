package Controller.Random_Utilities;
import Controller.Report_Generator;
import models.Vehicle;

import javax.swing.*;
import java.util.Random;

/*
 wherever you want to use this class, call it as such:
 Thread thread = new Thread(new Random_Vheicles_Caller());
 thread.start();
 */
public class Random_Vehicles_Caller implements Runnable {
    // This flag becomes false when the user presses the Exit button.
    // Make sure to add it there.
    public Boolean exit_flag = false;
    private JTable vehicles_table;
    private Report_Generator report_generator;
    public Random_Vehicles_Caller(JTable vehiclesTable, Report_Generator report_generator) {
        this.vehicles_table = vehiclesTable;
        this.report_generator = report_generator;
    }

    @Override
    public void run() {
        // generate random cars as long as the program is alive
        while (exit_flag == false){
            try {
                Random_Vehicle rand_vehicle = new Random_Vehicle(vehicles_table, report_generator);
                System.out.println("New random vehicle created");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
}