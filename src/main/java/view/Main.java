package view;
import Controller.csv_reader.CSVReader;
import exceptions.GUI_Manager_Exception;
import models.Phase;
import models.PhaseWithDuration;
import models.Status;
import models.Vehicle;
import Controller.StatisticsCalculator;
import view.GUIManager;

import java.util.Iterator;
import java.util.List;

import static Controller.GUIRowUtils.*;

public class Main extends Thread {

    public static final String INTERSECTION_FILE_PATH = "src/main/java/controller/csv_reader/files/intersection.csv";
    public static final String VEHICLES_FILE_PATH = "src/main/java/controller/csv_reader/files/vehicles.csv";
    // Method to run a phase
    public void run(List<PhaseWithDuration> phasesList, List<Vehicle> vehicleList, Phase phase) throws InterruptedException {
        // Used to safely remove the vehicles once they crossed
        Iterator<Vehicle> i = vehicleList.iterator();
        System.out.println(phase);
        try {
            while (i.hasNext()) {
                Vehicle v = i.next();
                if (v.getVehiclePhase() == phase) {
                    sleep(v.getCrossingTime() * 1000);
                    v.setStatus(Status.CROSSED);
                    System.out.println(v.toString() + "dropped");
                    i.remove();
                }
            }
        }
        catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }
    //Main entry point of the system
    public static void main(String[] args) throws InterruptedException {

        //Step 1: Read data from CSV
        CSVReader csvReader = new CSVReader();
        List<PhaseWithDuration> phasesList = csvReader.readIntersectionData(INTERSECTION_FILE_PATH);
        List<Vehicle> vehicleList = csvReader.readVehicleData(VEHICLES_FILE_PATH);

        Main phase1 = new Main();
        Main phase2 = new Main();
        Main phase3 = new Main();
        Main phase4 = new Main();
        Main phase5 = new Main();
        Main phase6 = new Main();
        Main phase7 = new Main();
        Main phase8 = new Main();

        phase1.join(phasesList.get(0).getDuration());
        phase2.join(phasesList.get(1).getDuration());
        phase3.join(phasesList.get(2).getDuration());
        phase4.join(phasesList.get(3).getDuration());
        phase5.join(phasesList.get(4).getDuration());
        phase6.join(phasesList.get(5).getDuration());
        phase7.join(phasesList.get(6).getDuration());
        phase8.join(phasesList.get(7).getDuration());

        //Step 2: Calculate statistics
        StatisticsCalculator statisticsCalculator = new StatisticsCalculator(vehicleList);
        int[][] statisticsData = statisticsCalculator.calculateSegmentData();
        int emission = statisticsCalculator.calculateEmission();

        //Step 3: Create GUI and display data
        GUIManager guiManager = null;
        try {
            guiManager = new GUIManager(
                    vehicleRows(vehicleList),
                    phasesRows(phasesList),
                    segmentStatsRows(statisticsData),
                    emissionStatsRow(emission)
            );
        } catch (GUI_Manager_Exception e) {
            throw new RuntimeException(e);
        }
        guiManager.initialize_GUI();

        //Step 4: Simulate the crossroad with the threads
        phase1.run(phasesList, vehicleList, Phase.p1);
        phase2.run(phasesList, vehicleList, Phase.p2);
        phase3.run(phasesList, vehicleList, Phase.p3);
        phase4.run(phasesList, vehicleList, Phase.p4);
        phase5.run(phasesList, vehicleList, Phase.p5);
        phase6.run(phasesList, vehicleList, Phase.p6);
        phase7.run(phasesList, vehicleList, Phase.p7);
        phase8.run(phasesList, vehicleList, Phase.p8);
    }
}
