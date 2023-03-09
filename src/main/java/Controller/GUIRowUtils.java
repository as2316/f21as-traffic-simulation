package Controller;

import models.PhaseWithDuration;
import models.Segment;
import models.Vehicle;

import java.util.List;

public class GUIRowUtils {

    public static String[][] vehicleRows(List<Vehicle> vehiclesList){
        String[][] rows = new String[vehiclesList.size()][];
        for(int i=0; i<rows.length; i++){
            rows[i] = vehiclesList.get(i).getRowToPrint();
        }
        return rows;
    }

    public static String[][] phasesRows(List<PhaseWithDuration> phasesList){
        String[][] rows = new String[phasesList.size()][];
        for(int i=0; i<rows.length; i++){
            rows[i] = phasesList.get(i).getRowToPrint();
        }
        return rows;
    }

    public static String[][] segmentStatsRows(int[][] statisticsData){
        String[][] rows = new String[4][4];
        for(int i=0; i< rows.length; i++){
            rows[i][0] = Segment.fromRowValue(i).toString();
            rows[i][1] = "NA";  //TODO: Stage 2
            rows[i][2] = statisticsData[i][1] + " m";
            rows[i][3] = statisticsData[i][2] + " s";
        }
        return rows;
    }

    public static String[][] emissionStatsRow(int emission){
        return new String[][] {{String.valueOf(emission)}};
    }

}
