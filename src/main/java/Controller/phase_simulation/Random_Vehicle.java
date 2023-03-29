package Controller.phase_simulation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class Random_Vehicle {

    /* Data Members */
    private String vehicle_id;
    private VehicleType vehicle_type;
    private Integer crossing_time;
    private Direction direction;
    private Integer length;
    private Integer emission;
    private Status status;
    private Segment segment;


    // private JTable vehicles_table;                  // table reference to add vehicles to
    private int number_of_random_vehicles;         // number of random vehicles to generate
    public Random_Vehicle() {
        vehicle_id = generate_rand_vehicle_id();
        vehicle_type = generate_rand_vehicle_type();
        crossing_time = generate_rand_vehicle_crossing_time();
        direction = generate_rand_vehicle_direction();
        length = generate_rand_vehicle_length();
        emission = generate_rand_vehicle_emission();
        status = generate_rand_vehicle_status();
        segment = generate_rand_vehicle_segment();

//        System.out.println(vehicle_id + " " + vehicle_type + " " + crossing_time + " " + direction + " " + length + " " +
//                emission + " " + status + " " + segment);       // debugging
    }

    public Vehicle getRandomVehicle(){
        return new Vehicle(vehicle_id, vehicle_type, crossing_time, direction, length, emission, status, segment);
    }

    // The function responsible for adding the random vehicles to the table
    private void add_vehicle_to_table(JTable vehicles_table){
        Vehicle vehicle = new Vehicle(vehicle_id, vehicle_type, crossing_time, direction, length, emission, status, segment);
        DefaultTableModel model = (DefaultTableModel) vehicles_table.getModel();
        model.addRow(new Object[]{vehicle.getId(), vehicle.getType(), vehicle.getCrossingTime(), vehicle.getDirection(), vehicle.getLength(), vehicle.getEmission(), vehicle.getStatus(), vehicle.getSegment()});
    }
    private Segment generate_rand_vehicle_segment() {
        return Segment.values()[new Random().nextInt(Segment.values().length)];
    }

    private Status generate_rand_vehicle_status() {
        //todo: do not randomize this, we need only "waiting" vehicles
//        return Status.values()[new Random().nextInt(Status.values().length)];
        return Status.WAITING;
    }

    private Integer generate_rand_vehicle_emission() {
        Random rand = new Random();
        int upper_range = 50;
        return rand.nextInt(upper_range) + 1;
    }

    private Integer generate_rand_vehicle_length() {
        Random rand = new Random();
        int upper_range = 40;
        return rand.nextInt(upper_range) + 1;
    }

    private Direction generate_rand_vehicle_direction() {
        return Direction.values()[new Random().nextInt(Direction.values().length)];
    }

    private Integer generate_rand_vehicle_crossing_time() {
        Random rand = new Random();
        int upper_range = 5;   //todo: making it small to simulate the stuff faster
        return rand.nextInt(upper_range) + 1;
    }

    private VehicleType generate_rand_vehicle_type() {
        return VehicleType.values()[new Random().nextInt(VehicleType.values().length)];
    }

    private String generate_rand_vehicle_id() {
        // Generate a random uppercase character
        Random rand = new Random();
        int rand_int = rand.nextInt(16);
        char rand_char = (char)('A' + rand_int);

        // Create an array of size 10
        ArrayList<Integer> possible_digits = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            possible_digits.add(i);
        }

        // Shuffle the array
        Collections.shuffle(possible_digits);

        // Get 5 digits from the shuffled [1, 10] range
        int first_digit = possible_digits.get(0);
        int second_digit = possible_digits.get(1);
        int third_digit = possible_digits.get(2);
        int fourth_digit = possible_digits.get(3);
        int fifth_digit = possible_digits.get(4);

        String rand_id = rand_char + Integer.toString(first_digit) + Integer.toString(second_digit)
                + Integer.toString(third_digit) + Integer.toString(fourth_digit) + Integer.toString(fifth_digit);

        return rand_id;
    }




}
