import java.io.*;

public class Control {
    //variables for file io
    FileWriter writer = null;
    FileReader reader = null;
    BufferedReader bufferedReader = null;

    public Control () {
      try {
        //initialize data fie
        writer = new FileWriter("control_data.txt");
        //writer.write("" + 0 + "\n");
        writer.close();
      } catch (IOException e) {

      }
    }

    public void insert (String key, String value) {
      try {
        //write data to c1
        writer = new FileWriter("control_data.txt", true);
        writer.write(key + "\t" + value + "\n");
        writer.close();
      } catch (IOException e) {
        
      }
    }

    public String search(String key) {
      try {
        //load data from run in c1 corresponding to hash index
        reader = new FileReader("control_data.txt");
        bufferedReader = new BufferedReader(reader);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
          String[] values = line.split("\t", 2);
          if (values[0].equals(key)) {
            return values[1];
          }
        }
      } catch (IOException e) {
        
      }
      return "";
    }
  }