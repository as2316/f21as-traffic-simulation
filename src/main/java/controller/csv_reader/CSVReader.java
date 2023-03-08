package controller.csv_reader;

import models.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static final String INTERSECTION_FILE_PATH = "/media/abheisenberg/New Volume/Abhishek/HW Uni/Jan Sem/Adv Softw Engg/f21as/f21as/src/main/java/csv_reader/files/intersection.csv";
    public static final String VEHICLES_FILE_PATH = "/media/abheisenberg/New Volume/Abhishek/HW Uni/Jan Sem/Adv Softw Engg/f21as/f21as/src/main/java/csv_reader/files/vehicles.csv";
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
        } catch (Exception e){
            e.printStackTrace();
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
        } catch (Exception e){
            e.printStackTrace();
        }
        return vehicleList;
    }

}
