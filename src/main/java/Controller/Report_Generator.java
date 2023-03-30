package Controller;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Report_Generator {
    private String output_file_path;
    private List<String> file_text;

    public Report_Generator(List<String> file_text){
        this.output_file_path = "/Users/shubhamgupta_01/Documents/ASE_CW/f21as-traffic-simulation/src/main/java/Controller/csv_reader/files/report.txt";
        this.file_text = file_text;
    }

    public void write_to_file() {
        System.out.println("Entered to file writing");
        try {
            FileWriter writer = new FileWriter(output_file_path, false);
            for (String str : file_text)
                writer.write(str + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
