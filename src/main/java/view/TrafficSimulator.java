package view;

import Controller.StatisticsCalculator;
import Controller.csv_reader.CSVReader;
import Controller.phase_simulation.PhaseManager;
import exceptions.GUI_Manager_Exception;
import models.PhaseWithDuration;
import models.Vehicle;
import models.VehicleDataForGUI;

import java.util.List;

import static Controller.GUIRowUtils.*;
import static Controller.GUIRowUtils.emissionStatsRow;

public class TrafficSimulator implements PhaseManager.VehicleListUpdateHandler {

    //Please change this path according to your system
    public static final String INTERSECTION_FILE_PATH = "/media/abheisenberg/New Volume/Abhishek/HW Uni/Jan Sem/Adv Softw Engg/f21as/f21as/src/main/java/Controller/csv_reader/files/intersection.csv";
    public static final String VEHICLES_FILE_PATH = "/media/abheisenberg/New Volume/Abhishek/HW Uni/Jan Sem/Adv Softw Engg/f21as/f21as/src/main/java/Controller/csv_reader/files/vehicles.csv";


    CSVReader csvReader;
    List<PhaseWithDuration> phasesList;
    List<Vehicle> vehicleList;

    StatisticsCalculator statisticsCalculator;
    int[][] statisticsData;
    int emission;

    GUIManager guiManager;

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

        PhaseManager phaseManager = new PhaseManager(this, phasesList);
        phaseManager.setVehicleList(vehicleList);
        phaseManager.start();

        //Step 3: Create GUI and display data
        try {
            guiManager = new GUIManager(
                    vehicleRows(vehicleList),
                    phasesRows(phasesList),
                    segmentStatsRows(statisticsData, new int[]{0,0,0,0}),
                    emissionStatsRow(emission)
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
}
