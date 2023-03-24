package Controller.phase_simulation;

import models.Phase;
import models.Status;
import models.Vehicle;
import models.VehicleWithCrossTime;

import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class PhaseThread extends Thread {

    private Phase phase;
    private PhaseTimeCompleteHandler phaseCompleteHandler;
    private TimerTickHandler timerTickHandler;
    private final PhaseManager.TrafficLight trafficLight;
    private int phaseTime;
    private int timeElapsed = 0;
    private TimerTask task;
    private Timer timer;
    private boolean isPhaseRunning;
    private Queue<VehicleWithCrossTime> vehicleWithCrossTimeQueue;
    private List<VehicleWithCrossTime> crossedVehicleList;
    private int lastVehicleWillCrossAt = 0;

    interface PhaseTimeCompleteHandler {
        public void onPhaseTimeComplete(Phase p);
    }

    interface TimerTickHandler {
        public void onTimerTick(Phase p);
    }

    public PhaseThread(
            Phase phase,
            int phaseTime,
            PhaseTimeCompleteHandler phaseCompleteHandler,
            TimerTickHandler timerTickHandler,
            PhaseManager.TrafficLight ap,
            Queue<VehicleWithCrossTime> vehicleQueue,
            List<VehicleWithCrossTime> crossedVehicleList
    ){
        this.vehicleWithCrossTimeQueue = vehicleQueue;
        this.phase = phase;
        this.phaseTime = phaseTime;
        this.phaseCompleteHandler = phaseCompleteHandler;
        this.timerTickHandler = timerTickHandler;
        this.trafficLight = ap;
        this.isPhaseRunning = false;
        this.crossedVehicleList = crossedVehicleList;
        updateCrossingAtTimes();
    }

    private void updateCrossingAtTimes(){
        for(VehicleWithCrossTime v: vehicleWithCrossTimeQueue){
            lastVehicleWillCrossAt += v.getCrossingTime();
            v.setWillCrossAtTime(lastVehicleWillCrossAt);
        }
    }

    @Override
    public void run() {

        synchronized (trafficLight){
            while (true) {

                while(trafficLight.currentGreenLightPhase() != phase){
                    try {
                        trafficLight.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                simulateThisPhase();
                trafficLight.notifyAll();
            }
        }
    }

    private void simulateThisPhase() {

        if(!isPhaseRunning){
            isPhaseRunning = true;

            System.out.println("\n\n\n\n\n~~~~~~~~~ GREEN light in phase "+trafficLight.currentGreenLightPhase()+" for "+phaseTime+" seconds");
            System.out.println("-------------------------------------------------------------------");

            System.out.println("Total vehicles in line: "+vehicleWithCrossTimeQueue.size()+", Total Crossing time: "+lastVehicleWillCrossAt+"s");

            System.out.println(vehicleWithCrossTimeQueue);

            //Run the timer for this phase
            timeElapsed = 0;    //Start the timer afresh
            task = new TimerTask() {
                @Override
                public void run() {
                    timeElapsed++;

                    if  (timeElapsed > phaseTime){
                        cancel();
                        phaseTimeOver();
                    }
                    else {
                        System.out.println(timeElapsed+"s");

                        if(vehicleWithCrossTimeQueue.isEmpty()){
                            System.out.println("No more vehicles present in line in phase "+phase);
                        } else if(timeElapsed == vehicleWithCrossTimeQueue.peek().getWillCrossAtTime()){
                            VehicleWithCrossTime vehicleRemoved = vehicleWithCrossTimeQueue.poll();
                            vehicleRemoved.setStatus(Status.CROSSED);
                            crossedVehicleList.add(0, vehicleRemoved);
                            System.out.println("Vehicle "+vehicleRemoved.getId()+" crossed at time "+timeElapsed+"s");
                        }

                    }

                    timerTickHandler.onTimerTick(phase);
                }
            };

            timer = new Timer();
            timer.schedule(task, 0,1000);
        }
    }

    private void phaseTimeOver(){
        //Stop the timer and notify phase finish event
        System.out.println("~~~~~~~~~~~ RED Light in phase "+ trafficLight.currentGreenLightPhase());

        phaseCompleteHandler.onPhaseTimeComplete(phase);
        isPhaseRunning = false;

        prepareVehicleQueueForNextIteration();
    }

    /**
     * This function prepares this phase's queue for the next iteration, by updating the values of 'willCrossAtTime'.
     * This needs to be done since one or more vehicles from the beginning of this queue might have passed, so we have
     * to update the time (the time at which they are going pass) of the remaining vehicles in the queue.
     */
    private void prepareVehicleQueueForNextIteration() {
        int crossTime = 0;

        for(VehicleWithCrossTime vehicleWithCrossTime: vehicleWithCrossTimeQueue){
            crossTime += vehicleWithCrossTime.getCrossingTime();
            vehicleWithCrossTime.setWillCrossAtTime(crossTime);
        }

        lastVehicleWillCrossAt = crossTime;
    }

    public void startThisPhaseThread(){
        updateCrossingAtTimes();
        this.start();
    }
}