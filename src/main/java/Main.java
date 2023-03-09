import controller.csv_reader.CSVReader;
import models.PhaseWithDuration;
import models.Vehicle;
import controller.StatisticsCalculator;
import view.GUIManager;

import java.util.List;

import static controller.GUIRowUtils.*;

public class Main {

    //Please change this path according to your system
    public static final String INTERSECTION_FILE_PATH = "/media/abheisenberg/New Volume/Abhishek/HW Uni/Jan Sem/Adv Softw Engg/f21as/f21as/src/main/java/controller/csv_reader/files/intersection.csv";
    public static final String VEHICLES_FILE_PATH = "/media/abheisenberg/New Volume/Abhishek/HW Uni/Jan Sem/Adv Softw Engg/f21as/f21as/src/main/java/controller/csv_reader/files/vehicles.csv";

    //Main entry point of the system
    public static void main(String[] args) {

        //Step 1: Read data from CSV
        CSVReader csvReader = new CSVReader();
        List<PhaseWithDuration> phasesList = csvReader.readIntersectionData(INTERSECTION_FILE_PATH);
        List<Vehicle> vehicleList = csvReader.readVehicleData(VEHICLES_FILE_PATH);

        //Step 2: Calculate statistics
        StatisticsCalculator statisticsCalculator = new StatisticsCalculator(vehicleList);
        int[][] statisticsData = statisticsCalculator.calculateSegmentData();
        int emission = statisticsCalculator.calculateEmission();

        //Step 3: Create GUI and display data
        GUIManager guiManager = new GUIManager(
                vehicleRows(vehicleList),
                phasesRows(phasesList),
                segmentStatsRows(statisticsData),
                emissionStatsRow(emission)
        );
        guiManager.initialize_GUI();
    }
}
