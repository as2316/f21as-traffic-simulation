package models;

public class PhaseWithDuration {
    /* DATA MEMBERS */
    private Phase phase;
    private int duration;

    /* CONSTRUCTOR */
    public PhaseWithDuration(Phase phase, int duration) {
        this.phase = phase;
        this.duration = duration;
    }

    /* GETTERS */
    public Phase getPhase() {
        return phase;
    }

    public int getDuration() {
        return duration;
    }

    /* SETTERS */
    public void setPhases(Phase phase) {
        this.phase = phase;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Phase name: "+phase+", duration: "+duration;
    }

    public String[] getRowToPrint(){
        String[] row = new String[2];
        row[0] = this.phase.toString();
        row[1] = String.valueOf(this.duration);
        return row;
    }
}
