package models;

import java.util.List;

public class VehicleDataForGUI {

    List<Vehicle> vehicleList;
    int[] waitingTimes;

    public VehicleDataForGUI(List<Vehicle> vehicleList, int[] waitingTimes) {
        this.vehicleList = vehicleList;
        this.waitingTimes = waitingTimes;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public int[] getWaitingTimes() {
        return waitingTimes;
    }

    public void setWaitingTimes(int[] waitingTimes) {
        this.waitingTimes = waitingTimes;
    }
}
