package models;

public enum Segment {
    S1,
    S2,
    S3,
    S4;

    public static Segment fromRowValue(int row){
        switch (row){
            case 0 -> { return Segment.S1; }
            case 1 -> { return Segment.S2; }
            case 2 -> { return Segment.S3; }
            case 3 -> { return Segment.S4; }
            default -> { return Segment.S1; }
        }
    }
}


