import controller.csv_reader.CSVReader;
import models.PhaseWithDuration;
import models.Vehicle;
import controller.StatisticsCalculator;
import view.GUIManager;

import java.util.List;

import static controller.GUIRowUtils.*;

public class Main {
    public static void main(String[] args) {

        //Step 1: Read data from CSV
        CSVReader csvReader = new CSVReader();
        List<PhaseWithDuration> phasesList =csvReader.readIntersectionData();
        List<Vehicle> vehicleList = csvReader.readVehicleData();

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
