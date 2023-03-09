package Controller;

import models.Segment;
import models.Vehicle;

import java.util.List;

public class StatisticsCalculator {

    List<Vehicle> vehicleList;

    int[][] statisticsData;

    public StatisticsCalculator(List<Vehicle> vehicleList){
        this.vehicleList = vehicleList;
        statisticsData = new int[4][3];
    }

    public int[][] calculateSegmentData(){
        for(Vehicle v: vehicleList){
            int index = getSegmentIndex(v.getSegment());
            statisticsData[index][1] += v.getLength();
            statisticsData[index][2] += v.getCrossingTime();
        }
        return statisticsData;
    }

    public int calculateEmission(){
        int emission = 0;
        for(Vehicle v: vehicleList){
            emission += v.getEmission();
        }
        return emission;
    }

    public int getSegmentIndex(Segment s){
        switch (s){
            case S1 -> { return 0; }
            case S2 -> { return 1; }
            case S3 -> { return 2; }
            case S4 -> { return 3; }
            default -> { return 0; }
        }
    }

}
