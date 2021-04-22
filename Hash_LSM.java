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

  public String search (String key) {
    int hash = Math.abs(key.hashCode())%TABLE_SIZE;
    String retVal = c0[hash].search(key);
    if (retVal != null) {
      return retVal;
    }

    //TODO implement disk search
    return "Not found :(";
  }

  //adds ned BucketNode to end of linked list in c0 at the specified index
  public void addToList (int index, String key, String value) {
    BucketNode cur = c0[index];
    while (cur.next != null) {
      cur = cur.next;
    }
    cur.add(key, value);
  }

  //merges data from c0 into the sorted run in c1 corresponding to its hash index
  public void writeToDisk () {
    //variables to be used in method
    int[] root = new int[TABLE_SIZE];
    int[] updatedRoot = new int[TABLE_SIZE];
    BucketNodeData[] data;
    BucketNodeData[] mergedData;
    String line;
    int count;

    try {
      //get partitian pointers from root file
      reader = new FileReader("root.txt");
      bufferedReader = new BufferedReader(reader);
      count = 0;
      while ((line = bufferedReader.readLine()) != null) {
        int index = Integer.parseInt(line);
        root[count] = index;
        updatedRoot[count] = index;
        count++;
      }
      reader.close();

      //load data from c1
      reader = new FileReader("data.txt");
      bufferedReader = new BufferedReader(reader);
      int numRecords = Integer.parseInt(bufferedReader.readLine());
      if (numRecords > 0) {
        //copy data from c1
        data = new BucketNodeData[numRecords];
        for (int i = 0; i < data.length; i++) {
          line = bufferedReader.readLine();
          String[] values = line.split("\t", 2);
          data[i] = new BucketNodeData(values[0], values[1]);
        }
        mergedData = new BucketNodeData[TABLE_SIZE + numRecords];
        count = 0;

        int start = 0, end = 1; //indexes for root pointers, root[start] is the index of where a data run starts and root[end-1] is the last record in the run
        //merge data from each index in c0 to each partitioned run in c1
        while (start < root.length) {
          BucketNodeData[] newData = c0[start] != null ? c0[start].getSortedArray() : new BucketNodeData[0];
          int i = root[start];//reference for c1 data
          int j = 0; //reference for c0 data (new data)

          int endIndex;
          if (start == root.length-1) {
            endIndex = data.length;
          } else {
            endIndex = root[end];
          }
          
          //merge data
          while (i < endIndex && j < newData.length) {
            if (data[i].compareTo(newData[j]) < 0) {
              mergedData[count] = data[i];
              i++;
            } else {
              mergedData[count] = newData[j];
              j++;
            }
            count++;
          }
          while (i < endIndex) {
            mergedData[count] = data[i];
            i++;
            count++;
          }
          while (j < newData.length) {
            mergedData[count] = newData[j];
            j++;
            count++;
          }

          //update root pointers
          for (int k = end; k < updatedRoot.length; k++) {
            updatedRoot[k] += newData.length;
          }
          start++;
          end++;
        }

        reader.close();
      } else {
        reader.close();
        //print ordered data from c0
        mergedData = new BucketNodeData[TABLE_SIZE];
        count = 0;
        for (int i = 0; i < TABLE_SIZE; i++) {
          updatedRoot[i] = count;
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
        if (mergedData[i] != null)
          writer.write("" + mergedData[i].key + "\t" + mergedData[i].value + "\n");
      }
      writer.close();

      //write data to root
      writer = new FileWriter("root.txt");
      for (int i = 0; i < TABLE_SIZE; i++) {
        writer.write("" + updatedRoot[i] + "\n");
      }
      writer.close();
    } catch (IOException e) {
      System.out.println("File error");
    }

    //empty table and update c0_count
    c0_count = 0;
    for (int i = 0; i < TABLE_SIZE; i++) {
      c0[i] = null;
    }
  }
}