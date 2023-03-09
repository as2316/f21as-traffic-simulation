package controller;

import controller.StatisticsCalculator;
import models.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsCalculatorTest {

    @Test
    // Testing calculation of the statistics
    public void checkSegmentTest() {
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(new Vehicle("1",VehicleType.CAR,10,Direction.LEFT,25,10, Status.WAITING, Segment.S2));
        vehicleList.add(new Vehicle("2",VehicleType.TRUCK,20,Direction.STRAIGHT,30,10,Status.WAITING,Segment.S1));
        vehicleList.add(new Vehicle("3",VehicleType.BUS,15,Direction.RIGHT,100,40,Status.WAITING,Segment.S1));
        var statistics = new StatisticsCalculator(vehicleList);
        var calculation = statistics.calculateSegmentData();
        assertEquals(130, calculation[0][1], "Length calculation error S1");
        assertEquals(25, calculation[1][1], "Length calculation error S2");
        assertEquals(35, calculation[0][2], "Crossing time calculation error S1");
        assertEquals(10, calculation[1][2], "Crossing time calculation error S2");
    }

    @Test
    // Testing calculation of the emission
    public void checkEmissionTest() {
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(new Vehicle("1",VehicleType.CAR,10,Direction.LEFT,25,10, Status.WAITING, Segment.S2));
        vehicleList.add(new Vehicle("2",VehicleType.TRUCK,20,Direction.STRAIGHT,30,10,Status.WAITING,Segment.S1));
        vehicleList.add(new Vehicle("3",VehicleType.BUS,15,Direction.RIGHT,100,40,Status.WAITING,Segment.S1));
        var statistics = new StatisticsCalculator(vehicleList);
        var calculation = statistics.calculateEmission();
        assertEquals(60, calculation, "Emission calculation error");
    }
}