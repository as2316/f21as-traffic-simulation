package models;

public class VehicleWithCrossTime {
    private Vehicle vehicle;
    private int willCrossAtTime;

    public VehicleWithCrossTime(Vehicle vehicle, int willCrossAtTime) {
        this.vehicle = vehicle;
        this.willCrossAtTime = willCrossAtTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int getWillCrossAtTime() {
        return willCrossAtTime;
    }

    public void setWillCrossAtTime(int willCrossAtTime) {
        this.willCrossAtTime = willCrossAtTime;
    }
}
