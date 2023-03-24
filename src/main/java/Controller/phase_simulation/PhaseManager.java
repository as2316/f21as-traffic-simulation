package Controller.phase_simulation;

import models.Phase;
import models.Vehicle;
import models.VehicleWithCrossTime;

import java.util.LinkedList;
import java.util.Queue;

public class PhaseManager {

    PhaseThread pt1, pt2, pt3;

    TrafficLight trafficLight;

    PhaseHandler phaseHandler;

    class TrafficLight {

        //This class contains the phase which currently has green light ON
        private Phase currentGreenLightPhase;

        public TrafficLight(Phase currentGreenLightPhase){
            this.currentGreenLightPhase = currentGreenLightPhase;
        }

        public void setGreenLightOn(Phase currentGreenLightPhase) {
            this.currentGreenLightPhase = currentGreenLightPhase;
        }

        public Phase currentGreenLightPhase(){
            return currentGreenLightPhase;
        }

    }

    public PhaseManager(){
        trafficLight = new TrafficLight(Phase.p1);
        phaseHandler = new PhaseHandler();

        Queue<Vehicle> queue1 = getRandomVehicleQueue(5);
        Queue<Vehicle> queue2 = getRandomVehicleQueue(3);
        Queue<Vehicle> queue3 = getRandomVehicleQueue(4);

        pt1 = new PhaseThread(Phase.p1, 15, phaseHandler, phaseHandler, trafficLight, queue1);
        pt2 = new PhaseThread(Phase.p2, 13, phaseHandler, phaseHandler, trafficLight, queue2);
        pt3 = new PhaseThread(Phase.p3, 14, phaseHandler, phaseHandler, trafficLight, queue3);
    }

    public void start(){
        pt1.start();
        pt2.start();
        pt3.start();
    }

    class PhaseHandler implements PhaseThread.PhaseTimeCompleteHandler, PhaseThread.TimerTickHandler {
        @Override
        public void onPhaseTimeComplete(Phase p) {
            switch (p){
                case p1 -> {
                    trafficLight.setGreenLightOn(Phase.p2);
                }
                case p2 -> {
                    trafficLight.setGreenLightOn(Phase.p3);
                }
                case p3 -> {
                    trafficLight.setGreenLightOn(Phase.p1);
                }
                case p4 -> {
                }
                case p5 -> {
                }
                case p6 -> {
                }
                case p7 -> {
                }
                case p8 -> {
                }
            }
        }

        @Override
        public void onTimerTick(Phase p) {
            //todo: update all queues and lists
        }
    }


    private Queue<Vehicle> getRandomVehicleQueue(int size){
        Queue<Vehicle> vehicleQueue = new LinkedList<>();
        Random_Vehicle randomVehicle;
        for(int i=0; i<size; i++){
            randomVehicle = new Random_Vehicle();
            vehicleQueue.add(randomVehicle.getRandomVehicle());
        }
        return vehicleQueue;
    }

}
