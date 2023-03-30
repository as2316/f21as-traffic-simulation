package Controller.phase_simulation;
import Controller.Report_Generator;
import models.Vehicle;

import javax.swing.*;

/*
 wherever you want to use this class, call it as such:
 Thread thread = new Thread(new Random_Vheicles_Caller());
 thread.start();
 */
public class Random_Vehicles_Caller implements Runnable {
    // This flag becomes false when the user presses the Exit button.
    // Make sure to add it there.
    public Boolean exit_flag = false;
    public PhaseManager phaseManager;
    public Random_Vehicles_Caller(PhaseManager phaseManager) {
        this.phaseManager = phaseManager;
    }

    @Override
    public void run() {
        // generate random cars as long as the program is alive
        Random_Vehicle rand_vehicle = new Random_Vehicle();
        while (exit_flag == false){
            try {
                Vehicle random_veh = rand_vehicle.getRandomVehicle();
                phaseManager.addVehicle(random_veh);
                System.out.println("New random vehicle created " + random_veh);
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
}