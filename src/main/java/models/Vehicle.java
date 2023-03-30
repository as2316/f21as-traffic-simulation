package models;
public class Vehicle {

    /* DATA MEMBERS */
    private String id;
    private VehicleType type;
    private Integer crossingTime;
    private Direction direction;
    private Integer length;
    private Integer emission;
    private Status status;
    private Segment segment;


    /* CONSTRUCTOR */
    public Vehicle(String id, VehicleType type, Integer crossingTime, Direction direction, Integer length, Integer emission, Status status, Segment segment) {
        if (crossingTime < 0) throw new IllegalArgumentException("Vehicle's crossing time cannot be negative.");
        if (length < 0) throw new IllegalArgumentException("Vehicle's length should not be negative.");
        if (emission < 0) throw new IllegalArgumentException("Vehicle's emission cannot be negative");

        this.id = id;
        this.type = type;
        this.crossingTime = crossingTime;
        this.direction = direction;
        this.length = length;
        this.emission = emission;
        this.status = status;
        this.segment = segment;
    }

    public Phase getVehiclePhase(){
        if (this.segment == Segment.S1) {
            if (this.direction == Direction.LEFT) {
                return Phase.p1;
            }
            else {
                return Phase.p6;
            }
        }
        if (this.segment == Segment.S2) {
            if (this.direction == Direction.LEFT) {
                return Phase.p2;
            }
            else {
                return Phase.p5;
            }
        }
        if (this.segment == Segment.S3) {
            if (this.direction == Direction.LEFT) {
                return Phase.p3;
            }
            else {
                return Phase.p8;
            }
        }
        if (this.segment == Segment.S4) {
            if (this.direction == Direction.LEFT) {
                return Phase.p4;
            }
            else {
                return Phase.p7;
            }
        }
        return null;
    }

    /* GETTERS */
    public String getId() {
        return id;
    }

    public VehicleType getType() {
        return type;
    }

    public Integer getCrossingTime() {
        return crossingTime;
    }

    public Direction getDirection() {
        return direction;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getEmission() {
        return emission;
    }

    public Status getStatus() {
        return status;
    }

    public Segment getSegment() {
        return segment;
    }


    /* SETTERS */
    public void setId(String id) {
        this.id = id;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public void setCrossingTime(Integer crossingTime) {
        this.crossingTime = crossingTime;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public void setEmission(Integer emission) {
        this.emission = emission;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: "+this.id+", ");
        sb.append("Type: "+this.type+", ");
        sb.append("Crossing time: "+this.crossingTime+"s, ");
        sb.append("Direction: "+this.direction+", ");
        sb.append("Length: "+this.length+"m, ");
        sb.append("Emission: "+this.emission+", ");
        sb.append("Status: "+this.status+", ");
        sb.append("Segment: "+this.segment+", ");
        return sb.toString();
    }

    public String[] getRowToPrint(){
        String[] row = new String[8];
        row[0] = this.id;
        row[1] = this.type.toString();
        row[2] = this.crossingTime.toString();
        row[3] = this.direction.toString();
        row[4] = this.length.toString();
        row[5] = this.emission.toString();
        row[6] = this.status.toString();
        row[7] = this.segment.toString();
        return row;
    }

    public void printInCSVFormat(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.id+",");
        sb.append(this.type+",");
        sb.append(this.crossingTime+",");
        sb.append(this.direction+",");
        sb.append(this.length+",");
        sb.append(this.emission+",");
        sb.append(this.status+",");
        sb.append(this.segment+",");
        System.out.println(sb);
    }
}
