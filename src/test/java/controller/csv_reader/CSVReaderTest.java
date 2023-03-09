package controller.csv_reader;

import controller.csv_reader.CSVReader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CSVReaderTest {

    @Test
    // Testing that the values in csv files are stored in the lists
    public void readDataTest() {
        var csvReader = new CSVReader();
        assertFalse(csvReader.readIntersectionData().isEmpty() || csvReader.readVehicleData().isEmpty(), "Data are not set in the list");
    }
}