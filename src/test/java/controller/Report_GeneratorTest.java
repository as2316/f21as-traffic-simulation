package controller;

import Controller.Report_Generator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Report_GeneratorTest {

    @Test
    void write_to_file() {
        List<String> list = Arrays.asList("Car", "Truck", "Bus");

        Report_Generator reportGenerator = new Report_Generator(list);
        reportGenerator.write_to_file();

        File report = new File("src/main/java/Controller/csv_reader/files/report.txt");
        assertTrue(report.exists());
    }
}