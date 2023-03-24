package Controller.phase_simulation;

import models.*;

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
        void onUpdate(VehicleDataForGUI data);
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

    public PhaseManager(
            VehicleListUpdateHandler vehicleListUpdateHandler,
            List<PhaseWithDuration> phaseWithDurations
    ){
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

        pt1 = new PhaseThread(Phase.p1, phaseWithDurations.get(0).getDuration(), phaseHandler, phaseHandler, trafficLight, waitingQueue1, crossedList1);
        pt2 = new PhaseThread(Phase.p2, phaseWithDurations.get(1).getDuration(), phaseHandler, phaseHandler, trafficLight, waitingQueue2, crossedList2);
        pt3 = new PhaseThread(Phase.p3, phaseWithDurations.get(2).getDuration(), phaseHandler, phaseHandler, trafficLight, waitingQueue3, crossedList3);
        pt4 = new PhaseThread(Phase.p4, phaseWithDurations.get(3).getDuration(), phaseHandler, phaseHandler, trafficLight, waitingQueue4, crossedList4);
        pt5 = new PhaseThread(Phase.p5, phaseWithDurations.get(4).getDuration(), phaseHandler, phaseHandler, trafficLight, waitingQueue5, crossedList5);
        pt6 = new PhaseThread(Phase.p6, phaseWithDurations.get(5).getDuration(), phaseHandler, phaseHandler, trafficLight, waitingQueue6, crossedList6);
        pt7 = new PhaseThread(Phase.p7, phaseWithDurations.get(6).getDuration(), phaseHandler, phaseHandler, trafficLight, waitingQueue7, crossedList7);
        pt8 = new PhaseThread(Phase.p8, phaseWithDurations.get(7).getDuration(), phaseHandler, phaseHandler, trafficLight, waitingQueue8, crossedList8);
    }

    public void setVehicleList(List<Vehicle> vehicleList){
        for(Vehicle v: vehicleList){
            addVehicle(v);
        }
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
            vehicleListUpdateHandler.onUpdate(createVehicleDataForGUI());
        }

        private VehicleDataForGUI createVehicleDataForGUI() {
            return new VehicleDataForGUI(
                    combineAllQueuesAndLists(),
                    createWaitingTimeArray()
            );
        }

        private int[] createWaitingTimeArray() {
            return new int[]{
                    pt1.getWaitingTime()+ pt6.getWaitingTime(),     //Waiting time for segment 1
                    pt2.getWaitingTime()+ pt5.getWaitingTime(),     //Waiting time for segment 2
                    pt3.getWaitingTime()+ pt8.getWaitingTime(),     //Waiting time for segment 3
                    pt4.getWaitingTime()+ pt7.getWaitingTime(),     //Waiting time for segment 4
            };
        }
    }

    //todo: Use this function to add vehicle to a particular phase
    public void addVehicle(Vehicle v){
        switch (v.getVehiclePhase()){
            case p1 -> {
                pt1.addVehicleToQueue(v);
            }
            case p2 -> {
                pt2.addVehicleToQueue(v);
            }
            case p3 -> {
                pt3.addVehicleToQueue(v);
            }
            case p4 -> {
                pt4.addVehicleToQueue(v);
            }
            case p5 -> {
                pt5.addVehicleToQueue(v);
            }
            case p6 -> {
                pt6.addVehicleToQueue(v);
            }
            case p7 -> {
                pt7.addVehicleToQueue(v);
            }
            case p8 -> {
                pt8.addVehicleToQueue(v);
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

}
