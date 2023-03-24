package Controller.phase_simulation;

import models.Phase;
import models.Vehicle;
import models.VehicleWithCrossTime;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PhaseManager {

    PhaseThread pt1, pt2, pt3, pt4, pt5, pt6, pt7, pt8;

    TrafficLight trafficLight;

    PhaseHandler phaseHandler;

    Queue<VehicleWithCrossTime> waitingQueue1, waitingQueue2, waitingQueue3, waitingQueue4, waitingQueue5, waitingQueue6, waitingQueue7, waitingQueue8;

    List<VehicleWithCrossTime> crossedList1, crossedList2, crossedList3, crossedList4, crossedList5, crossedList6, crossedList7, crossedList8;

    VehicleListUpdateHandler vehicleListUpdateHandler;

    public interface VehicleListUpdateHandler {
        void onUpdate(List<Vehicle> vehicleList);
    }

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

    public PhaseManager(VehicleListUpdateHandler vehicleListUpdateHandler){
        this.vehicleListUpdateHandler = vehicleListUpdateHandler;

        trafficLight = new TrafficLight(Phase.p1);
        phaseHandler = new PhaseHandler();

        waitingQueue1 = new LinkedList<>();
        waitingQueue2 = new LinkedList<>();
        waitingQueue3 = new LinkedList<>();
        waitingQueue4 = new LinkedList<>();
        waitingQueue5 = new LinkedList<>();
        waitingQueue6 = new LinkedList<>();
        waitingQueue7 = new LinkedList<>();
        waitingQueue8 = new LinkedList<>();

        crossedList1 = new ArrayList<>();
        crossedList2 = new ArrayList<>();
        crossedList3 = new ArrayList<>();
        crossedList4 = new ArrayList<>();
        crossedList5 = new ArrayList<>();
        crossedList6 = new ArrayList<>();
        crossedList7 = new ArrayList<>();
        crossedList8 = new ArrayList<>();

        pt1 = new PhaseThread(Phase.p1, 15, phaseHandler, phaseHandler, trafficLight, waitingQueue1, crossedList1);
        pt2 = new PhaseThread(Phase.p2, 13, phaseHandler, phaseHandler, trafficLight, waitingQueue2, crossedList2);
        pt3 = new PhaseThread(Phase.p3, 14, phaseHandler, phaseHandler, trafficLight, waitingQueue3, crossedList3);
        pt4 = new PhaseThread(Phase.p4, 15, phaseHandler, phaseHandler, trafficLight, waitingQueue4, crossedList4);
        pt5 = new PhaseThread(Phase.p5, 13, phaseHandler, phaseHandler, trafficLight, waitingQueue5, crossedList5);
        pt6 = new PhaseThread(Phase.p6, 14, phaseHandler, phaseHandler, trafficLight, waitingQueue6, crossedList6);
        pt7 = new PhaseThread(Phase.p7, 15, phaseHandler, phaseHandler, trafficLight, waitingQueue7, crossedList7);
        pt8 = new PhaseThread(Phase.p8, 13, phaseHandler, phaseHandler, trafficLight, waitingQueue8, crossedList8);
    }

    public void setVehicleList(List<Vehicle> vehicleList){
        for(Vehicle v: vehicleList){
            addVehicle(v);
        }
    }

    private VehicleWithCrossTime convertToVehicleWithTime(Vehicle v){
        return new VehicleWithCrossTime(v, 0);
    }

    public void start(){
        pt1.startThisPhaseThread();
        pt2.startThisPhaseThread();
        pt3.startThisPhaseThread();
        pt4.startThisPhaseThread();
        pt5.startThisPhaseThread();
        pt6.startThisPhaseThread();
        pt7.startThisPhaseThread();
        pt8.startThisPhaseThread();
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
                    trafficLight.setGreenLightOn(Phase.p4);
                }
                case p4 -> {
                    trafficLight.setGreenLightOn(Phase.p5);
                }
                case p5 -> {
                    trafficLight.setGreenLightOn(Phase.p6);
                }
                case p6 -> {
                    trafficLight.setGreenLightOn(Phase.p7);
                }
                case p7 -> {
                    trafficLight.setGreenLightOn(Phase.p8);
                }
                case p8 -> {
                    trafficLight.setGreenLightOn(Phase.p1);
                }
            }
        }

        @Override
        public void onTimerTick(Phase p) {
            //todo: delete
            System.out.println("CrossingQueue1: "+ waitingQueue1.size()+", "+"CrossingQueue2: "+ waitingQueue2.size()+", "
                    +"CrossingQueue1: "+ waitingQueue2.size()+", ");
            System.out.println("CrossedList1: "+crossedList1.size()+", "+"CrossedList2: "+crossedList2.size()+", "
                    +"CrossedList3: "+crossedList3.size()+", ");
            vehicleListUpdateHandler.onUpdate(combineAllQueuesAndLists());
        }
    }

    //todo: Use this function to add vehicle to a particular phase
    public void addVehicle(Vehicle v){
        switch (v.getVehiclePhase()){
            case p1 -> {
                waitingQueue1.add(convertToVehicleWithTime(v));
            }
            case p2 -> {
                waitingQueue2.add(convertToVehicleWithTime(v));
            }
            case p3 -> {
                waitingQueue3.add(convertToVehicleWithTime(v));
            }
            case p4 -> {
                waitingQueue4.add(convertToVehicleWithTime(v));
            }
            case p5 -> {
                waitingQueue5.add(convertToVehicleWithTime(v));
            }
            case p6 -> {
                waitingQueue6.add(convertToVehicleWithTime(v));
            }
            case p7 -> {
                waitingQueue7.add(convertToVehicleWithTime(v));
            }
            case p8 -> {
                waitingQueue8.add(convertToVehicleWithTime(v));
            }
        }
    }

    //todo: remove
    private Queue<VehicleWithCrossTime> getRandomVehicleQueue(int size){
        Queue<VehicleWithCrossTime> vehicleQueue = new LinkedList<>();
        Random_Vehicle randomVehicle;
        for(int i=0; i<size; i++){
            randomVehicle = new Random_Vehicle();
            vehicleQueue.add(
                    new VehicleWithCrossTime(randomVehicle.getRandomVehicle(), 0)
            );
        }
        return vehicleQueue;
    }

    private List<Vehicle> combineAllQueuesAndLists(){
        List<Vehicle> combinedList = new ArrayList<>();
        combinedList.addAll(crossedList1);
        combinedList.addAll(crossedList2);
        combinedList.addAll(crossedList3);
        combinedList.addAll(crossedList4);
        combinedList.addAll(crossedList5);
        combinedList.addAll(crossedList6);
        combinedList.addAll(crossedList7);
        combinedList.addAll(crossedList8);
        combinedList.addAll(waitingQueue1);
        combinedList.addAll(waitingQueue2);
        combinedList.addAll(waitingQueue3);
        combinedList.addAll(waitingQueue4);
        combinedList.addAll(waitingQueue5);
        combinedList.addAll(waitingQueue6);
        combinedList.addAll(waitingQueue7);
        combinedList.addAll(waitingQueue8);
        return combinedList;
    }

    //todo: remove
    private void printQueuesInCSVFormat(Queue<VehicleWithCrossTime> q){
        for(VehicleWithCrossTime v: q){
            v.printInCSVFormat();
        }
    }

}
