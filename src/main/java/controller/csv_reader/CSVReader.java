package controller.csv_reader;

import models.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static final String INTERSECTION_FILE_PATH = "src/main/java/controller/csv_reader/files/intersection.csv";
    public static final String VEHICLES_FILE_PATH = "src/main/java/controller/csv_reader/files/vehicles.csv";
    public List<PhaseWithDuration> readIntersectionData(){
        List<PhaseWithDuration> phaseList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(INTERSECTION_FILE_PATH));
            String line;
            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                Phase p = Phase.valueOf(values[0]);
                PhaseWithDuration pWithDuration = new PhaseWithDuration(p, Integer.parseInt(values[1]));
                phaseList.add(pWithDuration);
            }
        } catch (IOException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Vehicle file can't be found", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        return phaseList;
    }

    public List<Vehicle> readVehicleData(){
        List<Vehicle> vehicleList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(VEHICLES_FILE_PATH));
            String line;
            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                Vehicle v = new Vehicle(
                        values[0],
                        VehicleType.valueOf(values[1]),
                        Integer.parseInt(values[2]),
                        Direction.valueOf(values[3]),
                        Integer.parseInt(values[4]),
                        Integer.parseInt(values[5]),
                        Status.valueOf(values[6]),
                        Segment.valueOf(values[7])
                );
                vehicleList.add(v);
            }
        } catch (IOException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Intersection file can't be found", "Warning", JOptionPane.WARNING_MESSAGE);
        }
        return vehicleList;
    }

}
