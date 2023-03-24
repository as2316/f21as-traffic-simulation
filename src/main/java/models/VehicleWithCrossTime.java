package models;

public class VehicleWithCrossTime extends Vehicle {
    private int willCrossAtTime;

    public VehicleWithCrossTime(Vehicle vehicle, int willCrossAtTime) {
        super(vehicle.getId(), vehicle.getType(), vehicle.getCrossingTime(), vehicle.getDirection(), vehicle.getLength(), vehicle.getEmission(), vehicle.getStatus(), vehicle.getSegment());
        this.willCrossAtTime = willCrossAtTime;
    }


    public int getWillCrossAtTime() {
        return willCrossAtTime;
    }

    public void setWillCrossAtTime(int willCrossAtTime) {
        this.willCrossAtTime = willCrossAtTime;
    }
}
