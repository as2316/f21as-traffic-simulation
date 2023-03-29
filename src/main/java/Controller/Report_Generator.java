package Controller;
import java.io.FileWriter;
import java.io.IOException;

public class Report_Generator {
    private String output_file_path;
    private String file_text;

    public Report_Generator(String output_file_path){
        file_text = "";
        this.output_file_path = output_file_path;
    }

    /*
        Our goal is to format the report file like this:

        Vehicle L23467 crossed
        Vehicle A78731 was added
        Signal A was changed to STOP
        Vehicle Z99822 was added
        Signal B was changed to MOVE
     */

    public void add_vehicle_text(String vehicle_ID){
        String txt = "Vehicle [" + vehicle_ID + "] was added.\n";
        file_text += txt;
    }

    public void crossed_vehicle_text(String vehicle_ID){
        String txt = "Vehicle [" + vehicle_ID + "] crossed.\n";
        file_text += txt;
    }

    public void signal_status_changed_text(String signal){
        String text = "Signal [" + signal + "] status has changed.\n";
        file_text += text;
    }
    public void write_to_file() {
        System.out.println("Entered to file writing");
        try {
            FileWriter writer = new FileWriter(output_file_path, true);
            writer.write(file_text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
