import java.io.*;
import java.lang.Math;

public class Hash_LSM {
  public final int TABLE_SIZE = 10; //number of values to take before flushing to disk
  public BucketNode[] c0; //c0 is a hash table that writes to disk when it reaches capacity
  public int c0_count; //count stores the current number of values in c0

  //variables for file io
  FileWriter writer = null;
  FileReader reader = null;
  BufferedReader bufferedReader = null;

  public Hash_LSM () {
    c0 = new BucketNode[TABLE_SIZE]; //initialize c0
    c0_count = 0; //initialize count

    try {
      //initialize "tree" root
      writer = new FileWriter("root.txt");
      for (int i = 0; i < TABLE_SIZE; i++) {
        writer.write("" + 0 + "\n");
      }
      writer.close();

      //initialize data fie
      writer = new FileWriter("data.txt");
      writer.write("" + 0 + "\n");
      writer.close();
    } catch (IOException e) {
      System.out.println("File error");
    }
  }

  //for testing
  public void printC0 () {
    for (int i = 0; i < TABLE_SIZE; i++) {
      System.out.println("[" + i + "]: " + c0[i]);
    }
    System.out.println("");
  }

  //writes data to c0 if there is room, calls writeToDisk otherwise
  public void insert (String key, String value) {
    int hash = Math.abs(key.hashCode())%TABLE_SIZE;
    
    //check if c0 has space
    if (c0_count == TABLE_SIZE) {
      printC0();
      writeToDisk();
    }

    //if no value at hash index then simply add at start of linked list
    if (c0[hash] == null) {
      c0[hash] = new BucketNode(key, value);
    } else {
      addToList(hash, key, value); //iteratively add to end of list otherwise
    }
    c0_count++;
  }

  //adds ned BucketNode to end of linked list in c0 at the specified index
  public void addToList (int index, String key, String value) {
    BucketNode cur = c0[index];
    while (cur.next != null) {
      cur = cur.next;
    }
    cur.add(key, value);
  }

  public void writeToDisk () {
    //variables to be used in method
    int[] root = new int[TABLE_SIZE];
    BucketNodeData[] data;
    BucketNodeData[] mergedData;
    String line;
    int count;

    try {
      //get pointers from root file
      reader = new FileReader("root.txt");
      bufferedReader = new BufferedReader(reader);
      count = 0;
      while ((line = bufferedReader.readLine()) != null) {
        int index = Integer.parseInt(line);
        root[count] = index;
        count++;
      }
      reader.close();

      //load data from c1
      reader = new FileReader("data.txt");
      bufferedReader = new BufferedReader(reader);
      int numRecords = Integer.parseInt(bufferedReader.readLine());
      if (numRecords > 0) {
        data = new BucketNodeData[numRecords];
        mergedData = new BucketNodeData[TABLE_SIZE + numRecords];

        reader.close();
      } else {
        reader.close();
        //print ordered data from c0
        mergedData = new BucketNodeData[TABLE_SIZE];
        count = 0;
        for (int i = 0; i < TABLE_SIZE; i++) {
          root[i] = count;
          if (c0[i] != null) {
            BucketNodeData[] temp = c0[i].getSortedArray();
            for (int j = 0; j < temp.length; j++) {
              mergedData[count] = temp[j];
              count++;
            }
          }
        }
      }
      //write data to c1
      writer = new FileWriter("data.txt");
      writer.write("" + mergedData.length + "\n");
      for (int i = 0; i < mergedData.length; i++) {
        writer.write("" + mergedData[i].key + "\t" + mergedData[i].value + "\n");
      }
      writer.close();

      //write data to root
      writer = new FileWriter("root.txt");
      for (int i = 0; i < TABLE_SIZE; i++) {
        writer.write("" + root[i] + "\n");
      }
      writer.close();
    } catch (IOException e) {
      System.out.println("File error");
    }
    // for (int i = 0; i < TABLE_SIZE; i++) {

    // }
  }
}