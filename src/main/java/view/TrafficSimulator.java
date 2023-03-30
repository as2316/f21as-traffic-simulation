package view;

import Controller.StatisticsCalculator;
import Controller.csv_reader.CSVReader;
import Controller.phase_simulation.PhaseManager;
import Controller.phase_simulation.Random_Vehicles_Caller;
import exceptions.GUI_Manager_Exception;
import models.PhaseWithDuration;
import models.Vehicle;
import models.VehicleDataForGUI;

import java.util.List;

import static Controller.GUIRowUtils.*;
import static Controller.GUIRowUtils.emissionStatsRow;

public class TrafficSimulator implements PhaseManager.VehicleListUpdateHandler, GUIManager.ExitEventHandler {

    //Please change this path according to your system
    public static final String INTERSECTION_FILE_PATH = "/Users/shubhamgupta_01/Documents/ASE_CW/f21as-traffic-simulation/src/main/java/Controller/csv_reader/files/intersection.csv";
    public static final String VEHICLES_FILE_PATH = "/Users/shubhamgupta_01/Documents/ASE_CW/f21as-traffic-simulation/src/main/java/Controller/csv_reader/files/vehicles.csv";


    CSVReader csvReader;
    List<PhaseWithDuration> phasesList;
    List<Vehicle> vehicleList;

    StatisticsCalculator statisticsCalculator;
    int[][] statisticsData;
    int emission;
    GUIManager guiManager;
    PhaseManager phaseManager;

    public TrafficSimulator(){

        //Step 1: Read data from CSV
        csvReader = new CSVReader();
        phasesList = csvReader.readIntersectionData(INTERSECTION_FILE_PATH);
        vehicleList = csvReader.readVehicleData(VEHICLES_FILE_PATH);
    }

    public void simulate(){

        //Step 2: Calculate statistics
        statisticsCalculator = new StatisticsCalculator(vehicleList);
        statisticsData = statisticsCalculator.calculateStatisticsData();
        emission = statisticsCalculator.calculateEmission();

        phaseManager = new PhaseManager(this, phasesList);
        phaseManager.setVehicleList(vehicleList);
        phaseManager.start();
        Random_Vehicles_Caller random_vehicle_caller = new Random_Vehicles_Caller(phaseManager);
        Thread t = new Thread(random_vehicle_caller);
        t.start();

        //Step 3: Create GUI and display data
        try {
            guiManager = new GUIManager(
                    vehicleRows(vehicleList),
                    phasesRows(phasesList),
                    segmentStatsRows(statisticsData, new int[]{0,0,0,0}),
                    emissionStatsRow(emission),
                    this
            );

        } catch (
                GUI_Manager_Exception e) {
            throw new RuntimeException(e);
        }
        guiManager.initialize_GUI();
    }


    @Override
    public void onUpdate(VehicleDataForGUI data) {
        vehicleList = data.getVehicleList();

        statisticsCalculator.updateVehicleList(vehicleList);
        statisticsData = statisticsCalculator.calculateStatisticsData();

        guiManager.updateVehiclesTable(vehicleRows(vehicleList));
        guiManager.updateStatistics(segmentStatsRows(statisticsData, data.getWaitingTimes()));
    }

    @Override
    public void onExitButtonPressed() {
        System.out.println("Exit button pressed");
        guiManager.saveEventLog(phaseManager.getEventLogs());
    }
}
