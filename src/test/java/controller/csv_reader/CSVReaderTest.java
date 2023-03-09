package controller.csv_reader;

import controller.csv_reader.CSVReader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CSVReaderTest {

    public static final String INTERSECTION_FILE_PATH = "/media/abheisenberg/New Volume/Abhishek/HW Uni/Jan Sem/Adv Softw Engg/f21as/f21as/src/main/java/controller/csv_reader/files/intersection.csv";
    public static final String VEHICLES_FILE_PATH = "/media/abheisenberg/New Volume/Abhishek/HW Uni/Jan Sem/Adv Softw Engg/f21as/f21as/src/main/java/controller/csv_reader/files/vehicles.csv";

    @Test
    // Testing that the values in csv files are stored in the lists
    public void readDataTest() {
        var csvReader = new CSVReader();
        assertFalse(csvReader.readIntersectionData(INTERSECTION_FILE_PATH).isEmpty() || csvReader.readVehicleData(VEHICLES_FILE_PATH).isEmpty(), "Data are not set in the list");
    }
}